package ro.blogspot.smartadminwade.service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import ro.blogspot.smartadminwade.model.Software;

@Configuration
@ComponentScan(value = "ro.blogspot.smartadminwade.service")
public class QueryServiceTest {
	
	private AnnotationConfigApplicationContext context;
	
	@Before
	public void setUp() {
		context = new AnnotationConfigApplicationContext(QueryServiceTest.class);
	}

	@After
	public void tearDown() {
		context.close();
	}

	@Test
	public void should_return_an_environment() {
		QueryService queryService = context.getBean(QueryService.class);
		String webDevBasicEnv = "http://smartadminwade.blogspot.ro/sad#Basic_Web_Development_Environment_Windows_x86";

		Software resource = queryService.getDependencyGraph(webDevBasicEnv);

		Assert.assertNotNull(resource);
		Assert.assertEquals(resource.getName(), webDevBasicEnv);
	}

	@Test
	public void should_return_a_root_node_when_triggering_a_query() {
		QueryService queryService = context.getBean(QueryService.class);

		Software resource = queryService
				.getDependencyGraph("http://smartadminwade.blogspot.ro/sad#M2Eclipse_1.5_Windows_x86");

		Assert.assertNotNull(resource);
	}

}
