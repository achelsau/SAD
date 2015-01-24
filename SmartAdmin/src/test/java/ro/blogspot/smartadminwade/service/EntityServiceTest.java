package ro.blogspot.smartadminwade.service;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import ro.blogspot.smartadminwade.model.Software;
import ro.blogspot.smartadminwade.model.SoftwareType;
import ro.blogspot.smartadminwade.restcontrollers.SoftwareDescription;

@Configuration
@ComponentScan(value = "ro.blogspot.smartadminwade.service")
public class EntityServiceTest extends GenericTest {

	@Test
	public void testGetSoftwareApplicationEntities() {
		EntityService entityService = context.getBean(EntityService.class);
		List<Software> softwares = entityService.getSoftwareEntities(SoftwareType.SoftwareApplication);

		Assert.assertEquals(10, softwares.size());
	}

	@Test
	public void testGetSoftwareEnvironmentsEntities() {
		EntityService entityService = context.getBean(EntityService.class);
		List<Software> softwares = entityService.getSoftwareEntities(SoftwareType.SoftwareEnvironment);

		Assert.assertEquals(2, softwares.size());
	}

	@Test
	public void testGetSoftDescriptions() {
		EntityService entityService = context.getBean(EntityService.class);
		Map<String, SoftwareDescription> softwares = entityService.getSoftwareFriendlyDescriptions();

		Assert.assertNotNull(softwares);
	}

	@Test
	public void testGetAllSoftwareEntities() {
		EntityService entityService = context.getBean(EntityService.class);
		List<Software> softwares = entityService.getAllSoftwareEntities();

		Assert.assertEquals(12, softwares.size());
	}

}
