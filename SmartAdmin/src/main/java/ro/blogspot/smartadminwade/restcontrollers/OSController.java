package ro.blogspot.smartadminwade.restcontrollers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ro.blogspot.smartadminwade.model.SoftwareType;
import ro.blogspot.smartadminwade.model.Software;

/**
 * @author Ariel
 *
 */
@Controller
@RequestMapping("/oses")
public class OSController {
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public Software getListOfAvailableOSes(ModelMap model) {
		
		return new Software(SoftwareType.SoftwareApplication, "test", "3", "sdsd", "213213", "sdgdf", null);
	}
}
