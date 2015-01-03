package ro.blogspot.smartadminwade.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ro.blogspot.smartadminwade.model.RDFResource;
import ro.blogspot.smartadminwade.util.SADConstants;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;

@Service
public class QueryService {
	
	@Autowired
	private DataAccessService dataAccessService;

	public ro.blogspot.smartadminwade.model.RDFResource getDependencyGraph(String uri) {
		HashMap<String, Boolean> visited = new HashMap<String, Boolean>();

		return depthFirstSearch(uri, visited);
	}

	private ro.blogspot.smartadminwade.model.RDFResource depthFirstSearch(String uri, HashMap<String, Boolean> visited) {
		Model model = dataAccessService.getModel();
		StmtIterator iterator = model.getResource(uri).listProperties();
		visited.put(uri, true);

		Statement statement = null;
		ro.blogspot.smartadminwade.model.RDFResource res = new RDFResource();
		res.setDependsOn(new HashSet<RDFResource>());
		while (iterator.hasNext()) {
			Statement s = iterator.nextStatement();
			res.setName(s.getSubject().getURI());
			Set<RDFResource> dependsOn = res.getDependsOn();

			if (s.getPredicate().hasURI(SADConstants.namespace + "DependsOn")
					&& visited.get(s.getResource().getURI()) == null) {
				dependsOn.add(depthFirstSearch(s.getResource().getURI(), visited));
			}

			statement = s;
		}

		if (statement != null) {
			return res;
		} else {
			return null;
		}
	}

	public void setDataAccessService(DataAccessService dataAccessService) {
		this.dataAccessService = dataAccessService;

	}
}
