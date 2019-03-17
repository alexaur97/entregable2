<<<<<<< HEAD
=======

package controllers.administrator;
>>>>>>> 17dcc14c894328afb66f087b2898449f288e474c

package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import services.AdministratorService;
import services.AreaService;
import controllers.AbstractController;

@Controller
@RequestMapping("area/administrator")
public class AreaAdministratorController extends AbstractController {
<<<<<<< HEAD

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private AreaService				areaService;

	//		
	//		// List -----------------------------------------------------------	
	//		@RequestMapping(value = "/list")
	//		public ModelAndView list() {
	//
	//			ModelAndView result;
	//
	//			try {
	//				this.administratorService.findByPrincipal();
	//				Collection<Area> areas;
	//
	//				areas = this.areaService.findAll();
	//
	//				result = new ModelAndView("area/list");
	//				result.addObject("areas", areas);
	//				result.addObject("resquestURI", "/area/administrator/list.do");
	//
	//			} catch (final Exception e) {
	//				result = new ModelAndView("redirect:/#");
	//			}
	//			return result;
	//		}
	//		
	//		//Create-----------------------------------------------------------------------
	//		@RequestMapping(value = "/create", method = RequestMethod.GET)
	//		public ModelAndView create() {
	//
	//			ModelAndView result;
	//			Area area;
	//			area = new Area();
	//
	//			try {
	//				this.administratorService.findByPrincipal();
	//
	//				area.setId(0);
	//				
	//
	//				result = new ModelAndView("area/edit");
	//				result.addObject("area", area);
	//			} catch (final Throwable oops) {
	//
	//				result = this.createEditModelAndView(area, "area.commit.error");
	//
	//			}
	//
	//			return result;
	//		}
	//		// Edition ----------------------------------------------------------------
	//
	//		@RequestMapping(value = "/edit", method = RequestMethod.GET)
	//		public ModelAndView edit(@RequestParam final int areaId) {
	//			ModelAndView res = new ModelAndView("area/edit");
	//
	//			try {
	//				Assert.notNull(areaId);
	//				final Area area = this.areaService.findOne(areaId);
	//				this.administratorService.findByPrincipal();
	//				
	//				res.addObject("area", area);
	//				
	//			} catch (final Exception e) {
	//				res = new ModelAndView("redirect:/#");
	//			}
	//			return res;
	//		}
	//		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	//		public ModelAndView save(@ModelAttribute("area") Area area, final BindingResult binding) {
	//			ModelAndView res;
	//
	//			if (binding.hasErrors())
	//				res = this.createEditModelAndView(area);
	//			else
	//				try {
	//					this.areaService.save(area);
	//					res = new ModelAndView("redirect:/area/administrator/list.do");
	//				} catch (final Throwable oops) {
	//					res = this.createEditModelAndView(area, "area.commit.error");
	//
	//				}
	//
	//			return res;
	//		}
	//		@RequestMapping(value = "edit", method = RequestMethod.POST, params = "delete")
	//		public ModelAndView delete(final Area area, final BindingResult binding) {
	//			ModelAndView result;
	//
	//			try {
	//
	//				this.areaService.delete(area);
	//				result = new ModelAndView("redirect:/area/administrator/list.do");
	//			} catch (final Throwable oops) {
	//				result = this.createEditModelAndView(area, "area.commit.error");
	//
	//			}
	//
	//			return result;
	//		}
	//		
	//		
	//		protected ModelAndView createEditModelAndView(final Area area) {
	//			return this.createEditModelAndView(area, null);
	//		}
	//		protected ModelAndView createEditModelAndView(final Area area, final String messageCode) {
	//			final ModelAndView res;
	//			res = new ModelAndView("area/edit");
	//			this.administratorService.findByPrincipal();
	//			
	//			res.addObject("area", area);
	//
	//			res.addObject("message", messageCode);
	//
	//			return res;
	//		}
=======

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private AreaService				areaService;


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

	//Create-----------------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result;
		Area area;
		area = new Area();

		try {
			this.administratorService.findByPrincipal();

			area.setId(0);

			result = new ModelAndView("area/edit");
			result.addObject("area", area);
		} catch (final Throwable oops) {

			result = this.createEditModelAndView(area, "area.commit.error");

		}

		return result;
	}
	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int areaId) {
		ModelAndView res = new ModelAndView("area/edit");

		try {
			Assert.notNull(areaId);
			final Area area = this.areaService.findOne(areaId);
			this.administratorService.findByPrincipal();

			res.addObject("area", area);

		} catch (final Exception e) {
			res = new ModelAndView("redirect:/#");
		}
		return res;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("area") final Area area, final BindingResult binding) {
		ModelAndView res;

		if (binding.hasErrors())
			res = this.createEditModelAndView(area);
		else
			try {
				this.areaService.save(area);
				res = new ModelAndView("redirect:/area/administrator/list.do");
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(area, "area.commit.error");

			}

		return res;
	}
	@RequestMapping(value = "edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Area area, final BindingResult binding) {
		ModelAndView result;

		try {

			this.areaService.delete(area);
			result = new ModelAndView("redirect:/area/administrator/list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(area, "area.commit.error");

		}

		return result;
	}

	protected ModelAndView createEditModelAndView(final Area area) {
		return this.createEditModelAndView(area, null);
	}
	protected ModelAndView createEditModelAndView(final Area area, final String messageCode) {
		final ModelAndView res;
		res = new ModelAndView("area/edit");
		this.administratorService.findByPrincipal();

		res.addObject("area", area);

		res.addObject("message", messageCode);

		return res;
	}
>>>>>>> 17dcc14c894328afb66f087b2898449f288e474c

}
