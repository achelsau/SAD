package ro.blogspot.smartadminwade.restcontrollers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ro.blogspot.smartadminwade.model.OS;

/**
 * @author Ariel
 *
 */
@Controller
@RequestMapping("/oses")
public class OSController {
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public OS getListOfAvailableOSes(ModelMap model) {
		
		return new OS("Windows", "7", "x64");
	}
}
