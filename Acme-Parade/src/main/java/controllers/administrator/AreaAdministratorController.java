
package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.AreaService;
import controllers.AbstractController;
import domain.Area;

@Controller
@RequestMapping("area/administrator")
public class AreaAdministratorController extends AbstractController {

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private AreaService				areaService;

	@Autowired
	private Validator				validator;


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
		this.validator.validate(area, binding);

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

}
