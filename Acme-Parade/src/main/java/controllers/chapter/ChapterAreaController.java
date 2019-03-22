
package controllers.chapter;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AreaService;
import services.ChapterService;
import controllers.AbstractController;
import domain.Area;
import domain.Chapter;

@Controller
@RequestMapping("/chapter")
public class ChapterAreaController extends AbstractController {

	@Autowired
	private ChapterService	chapterService;

	@Autowired
	private AreaService		areaService;

	@Autowired
	private ActorService	actorService;


	//	@RequestMapping(value = "/assign", method = RequestMethod.GET)
	//	public ModelAndView assign() {
	//		ModelAndView res;
	//		try {
	//			Assert.isNull(this.chapterService.findByPrincipal().getArea(), "Este chapter ya tiene area asignada");
	//			res = new ModelAndView("area/assign");
	//			final Collection<Area> areasSinAsignar = this.areaService.areasSinAsignar();
	//			res.addObject("requestURI", "area/assign.do");
	//			res.addObject("areasSinAsignar", areasSinAsignar);
	//		} catch (final Throwable oops) {
	//			res = new ModelAndView("redirect:/#");
	//		}
	//		return res;
	//	}
	//
	//	@RequestMapping(value = "/assign", method = RequestMethod.POST, params = "save")
	//	public ModelAndView save(@ModelAttribute("assign") final Chapter chapter, final BindingResult binding) {
	//
	//		ModelAndView res;
	//		final Collection<Area> areasSinAsignar = this.areaService.areasSinAsignar();
	//		if (binding.hasErrors()) {
	//			res = new ModelAndView("area/assign");
	//			res.addObject("areasSinAsignar", areasSinAsignar);
	//
	//		} else
	//			try {
	//				Assert.isTrue(this.actorService.authEdit(this.actorService.findByPrincipal(), "CHAPTER"));
	//				final Chapter chapterFinal = this.chapterService.reconstructAssign(chapter, binding);
	//				this.chapterService.save(chapterFinal);
	//				res = new ModelAndView("redirect:/#");
	//
	//			} catch (final Throwable oops) {
	//				res = new ModelAndView("area/assign");
	//				res.addObject("areasSinAsignar", areasSinAsignar);
	//			}
	//
	//		return res;
	//	}
	//
	//}

	@RequestMapping(value = "/assign", method = RequestMethod.GET)
	public ModelAndView assign() {
		ModelAndView result;
		try {
			Assert.isNull(this.chapterService.findByPrincipal().getArea(), "Este chapter ya tiene area asignada");
			result = new ModelAndView("chapter/assign");
			final Chapter chapter = new Chapter();
			final Collection<Area> areas = this.areaService.areasSinAsignar();
			result.addObject("requestURI", "chapter/assign.do");
			result.addObject("areas", areas);
			result.addObject("chapter", chapter);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}
	@RequestMapping(value = "/assign", method = RequestMethod.POST, params = "save")
	public ModelAndView assign(final Chapter chapter, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = new ModelAndView("chapter/assign");
			final Collection<Area> areas = this.areaService.areasSinAsignar();
			result.addObject("areas", areas);
			result.addObject("chapter", chapter);

		} else
			try {
				Assert.isTrue(this.actorService.authEdit(this.actorService.findByPrincipal(), "CHAPTER"));
				final Chapter chapterFinal = this.chapterService.reconstructAssign(chapter, binding);
				this.chapterService.save(chapterFinal);
				result = new ModelAndView("redirect:/#");

			} catch (final Throwable oops) {
				result = new ModelAndView("chapter/assign");
				final Collection<Area> areas = this.areaService.areasSinAsignar();
				result.addObject("areas", areas);
				result.addObject("chapter", chapter);

			}
		return result;
	}
}
