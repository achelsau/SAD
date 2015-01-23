package ro.blogspot.smartadminwade.restcontrollers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ro.blogspot.smartadminwade.model.Software;

@Controller
@RequestMapping("/query")
public class QueryController {
	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public Software query(ModelMap model) {

		return null;
	}

}
