package ro.blogspot.smartadminwade.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		ro.blogspot.smartadminwade.model.Software res = new Software();
		res.setDependsOn(new HashSet<Software>());
		while (iterator.hasNext()) {
			Statement s = iterator.nextStatement();
			res.setName(s.getSubject().getURI());
			Set<Software> dependsOn = res.getDependsOn();

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
