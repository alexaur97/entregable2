
package controllers.brotherhood;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.HistoryService;
import services.LegalRecordService;
import controllers.AbstractController;
import domain.History;
import domain.LegalRecord;

@Controller
@RequestMapping("legalRecord/brotherhood")
public class LegalRecordBrotherhoodController extends AbstractController {

	@Autowired
	private LegalRecordService	legalRecordService;

	@Autowired
	private HistoryService		historyService;


	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int legalRecordId) {
		ModelAndView result;
		LegalRecord legalRecord;
		try {
			legalRecord = this.legalRecordService.findOne(legalRecordId);
			result = new ModelAndView("legalRecord/edit");
			result.addObject("legalRecord", legalRecord);
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
		} else
			try {

				this.legalRecordService.save(legalRecord);
				result = new ModelAndView("redirect:/legalRecord/brotherhood/display.do?legalRecordId=" + legalRecord.getId());

			} catch (final Throwable oops) {
				result = new ModelAndView("legalRecord/edit");
				result.addObject("message", "legalRecord.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final LegalRecord legalRecord) {
		ModelAndView result;
		try {
			final History history = this.historyService.findByLegalRecord(legalRecord.getId());
			this.legalRecordService.delete(legalRecord.getId());
			result = new ModelAndView("redirect:/history/brotherhood/display.do?historyId=" + history.getId());
		} catch (final Throwable oops) {
			result = new ModelAndView("legalRecord/edit");
			result.addObject("message", "legalRecord.commit.error");
		}
		return result;
	}

}
