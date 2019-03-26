
package controllers.chapter;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ChapterService;
import services.ParadeService;
import controllers.AbstractController;
import domain.Area;
import domain.Chapter;
import domain.Parade;

@Controller
@RequestMapping("/chapter/parade/")
public class ChapterParadeController extends AbstractController {

	@Autowired
	private ParadeService	paradeService;

	@Autowired
	private ActorService	actorService;

	@Autowired
	private ChapterService	chapterService;


	// List -----------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;
		try {
			final Chapter chapter = this.chapterService.findByPrincipal();
			Collection<Parade> parades;
			parades = this.paradeService.findFinalParadeByArea(chapter.getArea().getId());
			final String s = "SUBMITTED";
			result = new ModelAndView("parade/list");
			result.addObject("requestURI", "parade/list.do");
			result.addObject("parades", parades);
			result.addObject("s", s);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}

	@RequestMapping(value = "/accept", method = RequestMethod.GET)
	public ModelAndView accept(@RequestParam final int paradeId) {
		ModelAndView result;
		final Collection<Parade> parades;

		try {

			Assert.notNull(paradeId);
			final String s = "SUBMITTED";
			final Parade parade = this.paradeService.findOne(paradeId);
			final Area a = this.chapterService.findByPrincipal().getArea();
			Assert.isTrue(parade.getBrotherhood().getArea().equals(a));
			Assert.isTrue(parade.getStatus().equals("SUBMITTED"));
			parade.setStatus("ACCEPTED");
			this.paradeService.saveChapter(parade);
			final Chapter chapter = this.chapterService.findByPrincipal();
			parades = this.paradeService.findParadesByArea(chapter.getArea().getId());
			result = new ModelAndView("parade/list");
			result.addObject("requestURI", "parade/accept.do");
			result.addObject("parades", parades);
			result.addObject("s", s);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}
	@RequestMapping(value = "/reject", method = RequestMethod.GET)
	public ModelAndView reject(@RequestParam final int paradeId) {
		ModelAndView result;

		try {

			final Parade parade = this.paradeService.findOne(paradeId);
			final Area a = this.chapterService.findByPrincipal().getArea();
			Assert.notNull(paradeId);
			Assert.isTrue(parade.getBrotherhood().getArea().equals(a));
			Assert.isTrue(parade.getStatus().equals("SUBMITTED"));
			result = new ModelAndView("parade/edit");
			result.addObject("requestURI", "parade/reject.do");
			result.addObject("parade", parade);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}
		return result;

	}
	@RequestMapping(value = "/reject", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Parade parade, final BindingResult binding) {
		ModelAndView res;
		final Parade paradeFinal = this.paradeService.rejectRecostruction(parade, binding);
		final Collection<Parade> parades;
		if (binding.hasErrors())
			res = new ModelAndView("parade/edit");
		try {
			final String s = "SUBMITTED";
			res = new ModelAndView("parade/list");
			this.paradeService.saveChapter(paradeFinal);
			final Chapter chapter = this.chapterService.findByPrincipal();
			parades = this.paradeService.findParadesByArea(chapter.getArea().getId());
			res.addObject("parades", parades);
			res.addObject("requestURI", "parade/list.do");
			res.addObject("s", s);

		} catch (final Throwable oops) {
			if (parade.getExplanation().isEmpty()) {
				res = new ModelAndView("parade/edit");
				res.addObject("parade", parade);
				res.addObject("message", "request.explanation.error");
			} else
				res = new ModelAndView("redirect:/#");
		}
		return res;
	}
}
