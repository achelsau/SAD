package ro.blogspot.smartadminwade.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ro.blogspot.smartadminwade.model.Software;
import ro.blogspot.smartadminwade.model.SoftwareType;
import ro.blogspot.smartadminwade.restcontrollers.SoftwareDescription;
import ro.blogspot.smartadminwade.util.SADConstants;

import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;

@Service
public class EntityService {
	@Autowired
	private DataReaderService rdfReaderService;

	public List<Software> getAllSoftwareEntities() {
		List<Software> applications = getSoftwareEntities(SoftwareType.SoftwareApplication);
		List<Software> environments = getSoftwareEntities(SoftwareType.SoftwareEnvironment);
		environments.addAll(applications);

		return applications;
	}

	public List<Software> getSoftwareEntities(SoftwareType softwareType) {
		Property rdfType = rdfReaderService.getModel().createProperty(SADConstants.RDF_NAMESPACE + "type");
		Resource application = rdfReaderService.getModel().createResource(SADConstants.SAD_NAMESPACE + softwareType);
		ResIterator subjectsIterator = rdfReaderService.getModel().listSubjectsWithProperty(rdfType, application);

		List<Software> softwares = new ArrayList<Software>();
		while (subjectsIterator.hasNext()) {
			Resource resource = subjectsIterator.next();

			StmtIterator stmtIterator = resource.listProperties();
			Software software = new Software();
			while (stmtIterator.hasNext()) {
				Statement s = stmtIterator.next();
				software = ObjectMapper.mapResourceToObject(software, s);
			}

			softwares.add(software);
		}

		return softwares;
	}

	public Map<String, SoftwareDescription> getSoftwareFriendlyDescriptions() {
		Map<String, SoftwareDescription> descriptions = new HashMap<String, SoftwareDescription>();

		List<Software> allSoftware = getAllSoftwareEntities();
		for (Software soft : allSoftware) {
			SoftwareDescription description = descriptions.get(soft.getUserFriendlyName());
			if (description == null) {
				description = new SoftwareDescription();
			}

			Set<String> versions = description.getVersions();
			if (versions == null) {
				versions = new HashSet<String>();
				description.setVersions(versions);
			}
			versions.add(soft.getVersion());

			Set<String> operatingSystems = description.getOperatingSystems();
			if (operatingSystems == null) {
				operatingSystems = new HashSet<String>();
				description.setOperatingSystems(operatingSystems);
			}
			operatingSystems.add(soft.getOs());

			Set<String> architectures = description.getArchitectures();
			if (architectures == null) {
				architectures = new HashSet<String>();
				description.setArchitectures(architectures);
			}
			architectures.add(soft.getOsArchitecture());

			descriptions.put(soft.getUserFriendlyName(), description);
		}

		return descriptions;
	}
}
