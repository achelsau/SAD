package ro.blogspot.smartadminwade.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ro.blogspot.smartadminwade.model.ResourceType;
import ro.blogspot.smartadminwade.model.Software;
import ro.blogspot.smartadminwade.util.SADConstants;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;

@Service
public class QueryService {
	
	@Autowired
	private DataAccessService dataAccessService;

	public ro.blogspot.smartadminwade.model.Software getDependencyGraph(String uri) {
		HashMap<String, Boolean> visited = new HashMap<String, Boolean>();

		return depthFirstSearch(uri, visited);
	}

	private Software depthFirstSearch(String uri, HashMap<String, Boolean> visited) {
		Model model = dataAccessService.getModel();
		StmtIterator iterator = model.getResource(uri).listProperties();
		visited.put(uri, true);

		Statement statement = null;
		Software software = new Software();
		software.setDependsOn(new HashSet<Software>());
		while (iterator.hasNext()) {
			Statement s = iterator.nextStatement();
			software.setName(s.getSubject().getURI());

			if (s.getPredicate().hasURI(SADConstants.DOAP_NAMESPACE + "license")) {
				software.setLicense(s.getObject().toString());
			} else if (s.getPredicate().hasURI(SADConstants.SAD_NAMESPACE + "Architecture")) {
				software.setOsArchitecture(s.getObject().toString());
			} else if (s.getPredicate().hasURI(SADConstants.DOAP_NAMESPACE + "Version")) {
				software.setVersion(s.getObject().toString());
			} else if (s.getPredicate().hasURI(SADConstants.RDF_NAMESPACE + "type")) {
				String type = s.getObject().toString();
				if (type.substring(type.indexOf("#") + 1).equals("NamedIndividual")) {
					continue;
				}
				software.setType(ResourceType.valueOf(type.substring(type.indexOf("#") + 1)));
			} else if (s.getPredicate().hasURI(SADConstants.SAD_NAMESPACE + "UserFriendlyName")) {
				software.setUserFriendlyName(s.getObject().toString());
			} else if (s.getPredicate().hasURI(SADConstants.SAD_NAMESPACE + "dependsOn")
					&& visited.get(s.getResource().getURI()) == null) {
				Set<Software> dependsOn = software.getDependsOn();
				dependsOn.add(depthFirstSearch(s.getResource().getURI(), visited));
			}

			statement = s;
		}

		if (statement != null) {
			return software;
		} else {
			return null;
		}
	}

	public void setDataAccessService(DataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;
	}
}
