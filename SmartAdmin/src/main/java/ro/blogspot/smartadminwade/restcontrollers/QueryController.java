package ro.blogspot.smartadminwade.restcontrollers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ro.blogspot.smartadminwade.model.Software;
import ro.blogspot.smartadminwade.service.QueryService;

@Controller
@RequestMapping("/query")
public class QueryController {

	@Autowired
	private QueryService queryService;

	@RequestMapping(value = "/", method = RequestMethod.POST, headers = "content-type=text/plain")
	@ResponseBody
	public Software query(@RequestBody String elementURI, HttpServletRequest request,
			HttpServletResponse response) {

		return queryService.getDependencyGraph("http://smartadminwade.blogspot.ro/sad#" + elementURI);
	}

}
