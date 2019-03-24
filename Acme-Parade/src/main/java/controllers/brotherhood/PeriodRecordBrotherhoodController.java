
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
import services.PeriodRecordService;
import controllers.AbstractController;
import domain.PeriodRecord;

@Controller
@RequestMapping("periodRecord/brotherhood")
public class PeriodRecordBrotherhoodController extends AbstractController {

	@Autowired
	private PeriodRecordService	periodRecordService;

	@Autowired
	private HistoryService		historyService;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		PeriodRecord periodRecord;
		try {
			periodRecord = this.periodRecordService.create();
			result = new ModelAndView("periodRecord/create");
			result.addObject("periodRecord", periodRecord);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int periodRecordId) {
		ModelAndView result;
		PeriodRecord periodRecord;
		try {
			periodRecord = this.periodRecordService.findOne(periodRecordId);
			result = new ModelAndView("periodRecord/edit");
			result.addObject("periodRecord", periodRecord);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int periodRecordId) {
		ModelAndView result;
		PeriodRecord periodRecord;
		try {
			periodRecord = this.periodRecordService.findOne(periodRecordId);
			result = new ModelAndView("periodRecord/display");
			result.addObject("periodRecord", periodRecord);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final PeriodRecord periodRecord, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = new ModelAndView("periodRecord/edit");
			result.addObject("periodRecord", periodRecord);
		} else
			try {
				final Boolean b = this.periodRecordService.validatePictures(periodRecord.getPictures());
				if (!b) {
					result = new ModelAndView("periodRecord/edit");
					result.addObject("message", "periodRecord.photo.error");
				} else {
					this.periodRecordService.save(periodRecord);
					result = new ModelAndView("redirect:/periodRecord/brotherhood/display.do?periodRecordId=" + periodRecord.getId());
				}
			} catch (final Throwable oops) {
				if (periodRecord.getStartYear() > periodRecord.getEndYear()) {
					result = new ModelAndView("periodRecord/edit");
					result.addObject("message", "periodRecord.years.error");
				} else {
					result = new ModelAndView("periodRecord/edit");
					result.addObject("message", "periodRecord.commit.error");
				}
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final PeriodRecord periodRecord) {
		ModelAndView result;
		try {
			this.periodRecordService.delete(periodRecord.getId());
			result = new ModelAndView("redirect:/history/brotherhood/myList.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("periodRecord/edit");
			result.addObject("message", "periodRecord.commit.error");
		}
		return result;
	}

}
