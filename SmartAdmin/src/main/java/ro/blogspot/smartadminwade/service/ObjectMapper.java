package ro.blogspot.smartadminwade.service;

import ro.blogspot.smartadminwade.model.Software;
import ro.blogspot.smartadminwade.model.SoftwareType;
import ro.blogspot.smartadminwade.util.SADConstants;

import com.hp.hpl.jena.rdf.model.Statement;

public class ObjectMapper {
	public static Software mapResourceToObject(Software software, Statement s) {
		software.setName(s.getSubject().getURI());

		if (s.getPredicate().hasURI(SADConstants.DOAP_NAMESPACE + "license")) {
			software.setLicense(s.getObject().toString());
		} else if (s.getPredicate().hasURI(SADConstants.SAD_NAMESPACE + "Architecture")) {
			software.setOsArchitecture(s.getObject().toString());
		} else if (s.getPredicate().hasURI(SADConstants.DOAP_NAMESPACE + "Version")) {
			software.setVersion(s.getObject().toString());
		} else if (s.getPredicate().hasURI(SADConstants.DOAP_NAMESPACE + "download-page")) {
			software.setLink(s.getObject().toString());
		} else if (s.getPredicate().hasURI(SADConstants.DBPEDIA_NAMESPACE + "Operating_system")) {
			software.setOs(s.getObject().toString());
		} else if (s.getPredicate().hasURI(SADConstants.RDF_NAMESPACE + "type")) {
			String type = s.getObject().toString();
			if (!type.substring(type.indexOf("#") + 1).equals(SoftwareType.SoftwareApplication.toString())
					&& !type.substring(type.indexOf("#") + 1).equals(SoftwareType.SoftwareEnvironment.toString())) {
				return software;
			}
			software.setType(SoftwareType.valueOf(type.substring(type.indexOf("#") + 1)));
		} else if (s.getPredicate().hasURI(SADConstants.SAD_NAMESPACE + "UserFriendlyName")) {
			software.setUserFriendlyName(s.getObject().toString());
		}

		return software;
	}
}
