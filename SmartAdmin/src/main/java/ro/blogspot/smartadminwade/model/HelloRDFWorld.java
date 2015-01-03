package ro.blogspot.smartadminwade.model;

import java.io.InputStream;
import java.util.HashMap;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.vocabulary.VCARD;

/**
 * @author Ariel
 *
 */
public class HelloRDFWorld {
	private static String namespace = "http://smartadminwade.blogspot.ro/";
	private static String dbpediaNamespace = "http://dbpedia.org/resource/";
	private static String rdfNamespace = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
	private static HashMap<String, Boolean> visited = new HashMap<String, Boolean>();

	public static void main(String[] args) {
		Model model = ModelFactory.createDefaultModel();

		// some definitions
		String personURI = "http://somewhere/JohnSmith";
		String givenName = "John";
		String familyName = "Smith";
		String fullName = givenName + " " + familyName;

		createNestedWebDevEnvironment(model, namespace);

		// createNestedResource(model, namespace, personURI, givenName,
		// familyName, fullName);
		
		// readRDF();
		
	}

	private static void createNestedWebDevEnvironment(Model model, String namespace) {
		Resource webDevBasic = model.createResource(namespace + "WebDevBasic");
		Resource webDevAdvanced = model.createResource(namespace + "WebDevAdvanced");
		Property dependsOn = model.createProperty(namespace + "DependsOn");
		
		Resource jdk7Win64 = model.createResource(namespace + "JDK7_Windows_x64");
		Resource windows = model.createResource(dbpediaNamespace + "Microsoft_Windows");
		Resource x64 = model.createResource(dbpediaNamespace + "64-bit_computing");
		Resource resourceType = model.createResource(namespace + "Resource");
		Resource environmentType = model.createResource(namespace + "Environment");

		Property version = model.createProperty(namespace + "Version");
		Property operatingSystem = model.createProperty(dbpediaNamespace + "Operating_system");
		Property architecture = model.createProperty(namespace + "Architecture");
		Property rdfType = model.createProperty(rdfNamespace + "type");
		
		jdk7Win64.addProperty(version, "7", XSDDatatype.XSDdecimal);
		jdk7Win64.addProperty(operatingSystem, windows);
		jdk7Win64.addProperty(architecture, x64);
		jdk7Win64.addProperty(rdfType, resourceType);

		Resource apacheTomcat = model.createResource(dbpediaNamespace + "Apache_Tomcat");
		apacheTomcat.addProperty(version, "7");
		apacheTomcat.addProperty(dependsOn, jdk7Win64);
		apacheTomcat.addProperty(rdfType, resourceType);

		Resource apacheMaven = model.createResource(dbpediaNamespace + "Apache_Maven");
		apacheMaven.addProperty(version, "3.2");
		apacheMaven.addProperty(dependsOn, jdk7Win64);
		apacheMaven.addProperty(rdfType, resourceType);

		Resource eclipse = model.createResource(dbpediaNamespace + "Eclipse_(software)");
		eclipse.addProperty(operatingSystem, windows);
		eclipse.addProperty(version, "4.5.0");
		eclipse.addProperty(architecture, x64);
		eclipse.addProperty(dependsOn, jdk7Win64);
		eclipse.addProperty(rdfType, resourceType);

		Resource m2eclipse = model.createResource(dbpediaNamespace + "M2Eclipse");
		m2eclipse.addProperty(version, "1.5");
		m2eclipse.addProperty(dependsOn, apacheMaven);
		m2eclipse.addProperty(dependsOn, eclipse);
		m2eclipse.addProperty(rdfType, resourceType);

		webDevBasic.addProperty(dependsOn, jdk7Win64);
		webDevBasic.addProperty(dependsOn, apacheTomcat);
		webDevBasic.addProperty(dependsOn, apacheMaven);
		webDevBasic.addProperty(dependsOn, m2eclipse);
		webDevBasic.addProperty(rdfType, environmentType);

		model.setNsPrefix("sad", namespace);
		model.setNsPrefix("dbpedia", "http://dbpedia.org/resource/");
		model.write(System.out, "N3");

		ResIterator iterator = model.listSubjectsWithProperty(rdfType, resourceType);
		while (iterator.hasNext()) {
			Resource resource = iterator.next();
			System.out.println(resource.toString());
		}
		System.out.println("---------------");
		dfs(model, dbpediaNamespace + "M2Eclipse");
	}

	private static void dfs(Model model, String uri) {
		StmtIterator iterator = model.getResource(uri).listProperties();
		visited.put(uri, true);
		while (iterator.hasNext()) {
			Statement s = iterator.nextStatement();

			if (s.getPredicate().hasURI(namespace + "DependsOn") && visited.get(s.getResource().getURI()) == null) {
				dfs(model, s.getResource().getURI());
			}

			System.out.println(s.toString());
		}
	}

	private static void createNestedResource(Model model, String namespace, String personURI, String givenName,
			String familyName, String fullName) {
		model
				.createResource(personURI)
				.addProperty(VCARD.FN, fullName)
				.addProperty(
						model.createProperty(namespace + "Resource"),
						model.createResource(namespace + "JDK").addProperty(VCARD.Given, givenName)
								.addProperty(VCARD.Family, familyName));
		model
		.createResource(personURI + "1")
		.addProperty(VCARD.FN, fullName)
		.addProperty(
				model.createProperty(namespace + "Resource"),
						model.createResource(namespace + "JDK").addProperty(VCARD.Given, givenName + "1")
						.addProperty(VCARD.Family, familyName));

		model.setNsPrefix("sad", namespace);
		model.write(System.out, "RDF/XML");
	}

	private static void readRDF() {
		String inputFileName = "price.rdf";

		// create an empty model
		Model model = ModelFactory.createDefaultModel();

		// use the FileManager to find the input file
		InputStream in = FileManager.get().open(inputFileName);
		if (in == null) {
			throw new IllegalArgumentException("File: " + inputFileName + " not found");
		}

		// read the RDF/XML file
		model.read(in, null);

		// write it to standard out
		model.write(System.out);
	}
}
