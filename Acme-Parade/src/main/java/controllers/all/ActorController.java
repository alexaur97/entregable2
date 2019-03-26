
package controllers.all;

import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AdministratorService;
import services.BrotherhoodService;
import services.ChapterService;
import services.MemberService;
import controllers.AbstractController;
import domain.Actor;
import domain.Administrator;
import domain.Brotherhood;
import domain.Chapter;
import domain.Member;
import forms.ActorEditForm;

@Controller
@RequestMapping("/actor")
public class ActorController extends AbstractController {

	@Autowired
	private ActorService			actorService;
	@Autowired
	private BrotherhoodService		brotherhoodService;
	@Autowired
	private MemberService			memberService;
	@Autowired
	private AdministratorService	administratorService;
	@Autowired
	private ChapterService			chapterService;


	//JAVI
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;

		try {
			final Actor actor = this.actorService.findByPrincipal();
			final ActorEditForm actorEditForm = this.actorService.toForm(actor);
			result = new ModelAndView("actor/edit");
			result.addObject("actorEditForm", actorEditForm);
			final Locale l = LocaleContextHolder.getLocale();
			final String lang = l.getLanguage();
			result.addObject("lang", lang);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView save(@Valid final ActorEditForm actorEditForm, final BindingResult binding) {
		ModelAndView res;
		if (binding.hasErrors()) {
			res = new ModelAndView("actor/edit");
			res.addObject("actorEditForm", actorEditForm);
			final Locale l = LocaleContextHolder.getLocale();
			final String lang = l.getLanguage();
			res.addObject("lang", lang);

		} else
			try {
				final Actor actor = this.actorService.findByPrincipal();
				if (this.actorService.authEdit(actor, "BROTHERHOOD")) {
					final Brotherhood brotherhood = this.brotherhoodService.reconstructEdit(actorEditForm);
					this.brotherhoodService.save(brotherhood);
				} else if (this.actorService.authEdit(actor, "MEMBER")) {
					final Member member = this.memberService.reconstructEdit(actorEditForm);
					this.memberService.save(member);
				} else if (this.actorService.authEdit(actor, "ADMINISTRATOR")) {
					final Administrator administrator = this.administratorService.reconstructEdit(actorEditForm);
					this.administratorService.save(administrator);
				} else {
					final Chapter chapter = this.chapterService.reconstructEdit(actorEditForm);
					this.chapterService.save(chapter);
				}
				res = new ModelAndView("redirect:/#");
			} catch (final Throwable oops) {
				res = new ModelAndView("actor/edit");
				final Locale l = LocaleContextHolder.getLocale();
				final String lang = l.getLanguage();
				res.addObject("lang", lang);

				res.addObject("requestURI", "actor/edit.do");
				res.addObject("message", "actor.commit.error");
			}
		return res;
	}
	//JAVI

}
