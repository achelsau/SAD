package ro.blogspot.smartadminwade.restcontrollers;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ro.blogspot.smartadminwade.service.EntityService;

/**
 * @author Ariel
 *
 */
@Controller
@RequestMapping("/entities")
public class EntityController {
	
	@Autowired
	private EntityService entityService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, SoftwareDescription> getEntityList(HttpServletRequest request,
			HttpServletResponse response) {
		
		return entityService.getSoftwareFriendlyDescriptions();
	}
}
