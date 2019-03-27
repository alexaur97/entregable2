
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
import services.HistoryService;
import services.LegalRecordService;
import controllers.AbstractController;
import domain.LegalRecord;

@Controller
@RequestMapping("legalRecord/brotherhood")
public class LegalRecordBrotherhoodController extends AbstractController {

	@Autowired
	private LegalRecordService	legalRecordService;

	@Autowired
	private HistoryService		historyService;

	@Autowired
	private BrotherhoodService	brotherhoodService;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		LegalRecord legalRecord;
		try {
			legalRecord = this.legalRecordService.create();
			result = new ModelAndView("legalRecord/create");
			result.addObject("legalRecord", legalRecord);
			result.addObject("brotherhood", this.brotherhoodService.findByPrincipal());

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int legalRecordId) {
		ModelAndView result;
		LegalRecord legalRecord;
		try {
			legalRecord = this.legalRecordService.findOne(legalRecordId);
			result = new ModelAndView("legalRecord/edit");
			result.addObject("legalRecord", legalRecord);
			result.addObject("brotherhood", this.brotherhoodService.findByPrincipal());

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int legalRecordId) {
		ModelAndView result;
		LegalRecord legalRecord;
		try {
			legalRecord = this.legalRecordService.findOne(legalRecordId);
			result = new ModelAndView("legalRecord/display");
			result.addObject("legalRecord", legalRecord);
			result.addObject("brotherhood", this.brotherhoodService.findByPrincipal());

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final LegalRecord legalRecord, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = new ModelAndView("legalRecord/edit");
			result.addObject("legalRecord", legalRecord);
			result.addObject("brotherhood", this.brotherhoodService.findByPrincipal());

		} else
			try {

				this.legalRecordService.save(legalRecord);
				result = new ModelAndView("redirect:/legalRecord/brotherhood/display.do?legalRecordId=" + legalRecord.getId());

			} catch (final Throwable oops) {
				result = new ModelAndView("legalRecord/edit");
				result.addObject("message", "legalRecord.commit.error");
				result.addObject("brotherhood", this.brotherhoodService.findByPrincipal());

			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final LegalRecord legalRecord) {
		ModelAndView result;
		try {
			this.legalRecordService.delete(legalRecord.getId());
			result = new ModelAndView("redirect:/history/brotherhood/myList.do");
			result.addObject("brotherhood", this.brotherhoodService.findByPrincipal());

		} catch (final Throwable oops) {
			result = new ModelAndView("legalRecord/edit");
			result.addObject("message", "legalRecord.commit.error");
			result.addObject("brotherhood", this.brotherhoodService.findByPrincipal());

		}
		return result;
	}

}
