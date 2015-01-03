package ro.blogspot.smartadminwade.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.hp.hpl.jena.rdf.model.Model;

public class DataAccessServiceTest {
	private DataAccessService dataAccessService;

	@Before
	public void setUp() {
		// setup
		dataAccessService = new DataAccessService();
	}

	@Test
	public void should_access_rdf_data() {
		// execute
		dataAccessService.readRDFData("database.rdf");

		// verify
		Model model = dataAccessService.getModel();
		Assert.assertNotNull(model);
		Assert.assertTrue(model.listSubjects().toList().size() > 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void should_throw_exception_when_file_not_found() {
		// execute
		try {
			dataAccessService.readRDFData("database1.rdf");
		} catch (IllegalArgumentException ex) {
			// verify
			Assert.assertEquals("File: database1.rdf not found", ex.getMessage());

			throw ex;
		}
	}
}
