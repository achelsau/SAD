package ro.blogspot.smartadminwade.service;

import java.io.InputStream;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;

@Service
@Scope("singleton")
public class DataAccessService {

	private Model model;

	@PostConstruct
	public void readRDFData() {
		readRDFData("database.rdf");
	}

	public void readRDFData(String inputFileName) {
		// create an empty model
		model = ModelFactory.createDefaultModel();

		// use the FileManager to find the input file
		InputStream in = FileManager.get().open(inputFileName);
		if (in == null) {
			throw new IllegalArgumentException("File: " + inputFileName + " not found");
		}

		// read the RDF/XML file
		model.read(in, null);
	}

	public Model getModel() {
		return model;
	}
}
