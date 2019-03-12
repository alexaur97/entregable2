/*
 * aboutController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/about")
public class AboutController extends AbstractController {

	@RequestMapping("/cookies")
	public ModelAndView cookies() {
		ModelAndView result;

		result = new ModelAndView("about/cookies");

		return result;
	}

	@RequestMapping("/terms")
	public ModelAndView terms() {
		ModelAndView result;

		result = new ModelAndView("about/terms");

		return result;
	}

	@RequestMapping("/contact")
	public ModelAndView contact() {
		ModelAndView result;

		result = new ModelAndView("about/contact");

		return result;
	}

	@RequestMapping("/privacy")
	public ModelAndView privacy() {
		ModelAndView result;

		result = new ModelAndView("about/privacy");

		return result;
	}

}
