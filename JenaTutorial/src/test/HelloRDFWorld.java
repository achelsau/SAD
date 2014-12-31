package test;

import java.io.InputStream;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
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
	public static void main(String[] args) {
		Model model = ModelFactory.createDefaultModel();
		String namespace = "http://smartadminwade.blogspot.ro/";

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
		Property dependsOn = model.createProperty(namespace + "dependsOn");
		
		Resource jdk7Win64 = model.createResource(namespace + "JDK7_Windows_x64");

		Resource tomcat = model.createResource("http://dbpedia.org/resource/Apache_Tomcat");
		Resource maven = model.createResource("http://dbpedia.org/resource/Apache_Maven");
		Property version = model.createProperty(namespace + "version");
		Property os = model.createProperty("http://dbpedia.org/resource/Operating_system");
		Property osArchitecture = model.createProperty(namespace + "Opearating_system_architecture");
		jdk7Win64.addProperty(version, "7", XSDDatatype.XSDinteger);
		jdk7Win64.addProperty(os, "Windows");
		jdk7Win64.addProperty(osArchitecture, "x64");
		webDevBasic.addProperty(dependsOn, jdk7Win64);
		webDevBasic.addProperty(dependsOn, tomcat);
		webDevBasic.addProperty(dependsOn, maven);

		Resource jdk8Win64 = model.createResource(namespace + "JDK8_Windows_x64");
		jdk8Win64.addProperty(version, "8", XSDDatatype.XSDinteger);
		jdk8Win64.addProperty(os, "Windows");
		jdk8Win64.addProperty(osArchitecture, "x64");
		webDevAdvanced.addProperty(dependsOn, jdk8Win64);

		model.setNsPrefix("sad", namespace);
		model.setNsPrefix("dbpedia", "http://dbpedia.org/resource/");
		model.write(System.out, "RDF/XML");

		StmtIterator iter = model.listStatements();
		while (iter.hasNext()) {
			Statement s = iter.nextStatement();
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
