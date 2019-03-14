package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.AreaService;
import controllers.AbstractController;
import domain.Area;

@Controller
@RequestMapping("area/administrator")
public class AreaAdministratorController extends AbstractController {
		@Autowired
		private AdministratorService administratorService;
		
		@Autowired
		private AreaService areaService;
		
		
		
		// List -----------------------------------------------------------	
		@RequestMapping(value = "/list")
		public ModelAndView list() {

			ModelAndView result;

			try {
				this.administratorService.findByPrincipal();
				Collection<Area> areas;

				areas = this.areaService.findAll();

				result = new ModelAndView("area/list");
				result.addObject("areas", areas);
				result.addObject("resquestURI", "/area/administrator/list.do");

			} catch (final Exception e) {
				result = new ModelAndView("redirect:/#");
			}
			return result;
		}
		

}
