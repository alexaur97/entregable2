
package controllers.brotherhood;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BrotherhoodService;
import services.InceptionRecordService;
import controllers.AbstractController;
import domain.InceptionRecord;

@Controller
@RequestMapping("inceptionRecord/brotherhood")
public class InceptionRecordBrotherhoodController extends AbstractController {

	@Autowired
	private InceptionRecordService	inceptionRecordService;
	@Autowired
	private BrotherhoodService		brotherhoodService;


	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int inceptionRecordId) {
		ModelAndView result;
		InceptionRecord inceptionRecord;
		try {
			inceptionRecord = this.inceptionRecordService.findOne(inceptionRecordId);
			result = new ModelAndView("inceptionRecord/edit");
			result.addObject("inceptionRecord", inceptionRecord);
			result.addObject("brotherhood", this.brotherhoodService.findByPrincipal());

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int inceptionRecordId) {
		ModelAndView result;
		InceptionRecord inceptionRecord;
		try {
			inceptionRecord = this.inceptionRecordService.findOne(inceptionRecordId);
			result = new ModelAndView("inceptionRecord/display");
			result.addObject("inceptionRecord", inceptionRecord);
			result.addObject("brotherhood", this.brotherhoodService.findByPrincipal());

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid InceptionRecord inceptionRecord, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = new ModelAndView("inceptionRecord/edit");
			result.addObject("inceptionRecord", inceptionRecord);
		} else
			try {
				final Boolean b = this.inceptionRecordService.validatePictures(inceptionRecord.getPictures());
				if (!b) {
					result = new ModelAndView("inceptionRecord/edit");
					result.addObject("message", "inceptionRecord.photo.error");
					result.addObject("brotherhood", this.brotherhoodService.findByPrincipal());

				} else {
					inceptionRecord = this.inceptionRecordService.save(inceptionRecord);
					result = new ModelAndView("redirect:/inceptionRecord/brotherhood/display.do?inceptionRecordId=" + inceptionRecord.getId());
				}
			} catch (final Throwable oops) {
				result = new ModelAndView("inceptionRecord/edit");
				result.addObject("message", "inceptionRecord.commit.error");
				result.addObject("brotherhood", this.brotherhoodService.findByPrincipal());

			}
		return result;
	}

}
