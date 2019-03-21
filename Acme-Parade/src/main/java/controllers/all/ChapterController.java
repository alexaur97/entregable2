
package controllers.all;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ChapterService;
import controllers.AbstractController;
import domain.Chapter;
import forms.ChapterRegisterForm;

@Controller
@RequestMapping("/chapter")
public class ChapterController extends AbstractController {

	// Supporting services ----------------------------------------------------

	@Autowired
	private ChapterService	chapterService;

	@Autowired
	private ActorService	actorService;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		try {
			final ChapterRegisterForm registerForm = new ChapterRegisterForm();
			result = this.createEditModelAndView(registerForm);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}

	protected ModelAndView createEditModelAndView(final ChapterRegisterForm chapterRegisterForm) {
		return this.createEditModelAndView(chapterRegisterForm, null);
	}

	protected ModelAndView createEditModelAndView(final ChapterRegisterForm chapterRegisterForm, final String messageCode) {
		final ModelAndView result;
		result = new ModelAndView("chapter/edit");
		result.addObject("chapterRegisterForm", chapterRegisterForm);
		result.addObject("message", messageCode);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final ChapterRegisterForm chapterRegisterForm, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(chapterRegisterForm);
		else
			try {
				final Chapter chapter = this.chapterService.reconstruct(chapterRegisterForm);
				this.chapterService.save(chapter);
				result = new ModelAndView("redirect:/security/login.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(chapterRegisterForm);

				final Collection<String> accounts = this.actorService.findAllAccounts();
				final Collection<String> emails = this.actorService.findAllEmails();

				if (accounts.contains(chapterRegisterForm.getUsername()))
					result.addObject("message", "register.username.error");
				else if (emails.contains(chapterRegisterForm.getEmail()))
					result.addObject("message", "register.email.error");
				else if (!chapterRegisterForm.getConfirmPassword().equals(chapterRegisterForm.getPassword()))
					result.addObject("message", "register.password.error");
				else
					result.addObject("message", "register.commit.error");
			}
		return result;
	}
}
