package ro.blogspot.smartadminwade.service;

import org.junit.After;
import org.junit.Before;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(value = "ro.blogspot.smartadminwade.service")
public class GenericTest {

	protected AnnotationConfigApplicationContext context;

	@Before
	public void setUp() {
		context = new AnnotationConfigApplicationContext(QueryServiceTest.class);
	}

	@After
	public void tearDown() {
		context.close();
	}

}
