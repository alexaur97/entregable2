
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
import services.MiscellaneousRecordService;
import controllers.AbstractController;
import domain.MiscellaneousRecord;

@Controller
@RequestMapping("miscellaneousRecord/brotherhood")
public class MiscellaneousRecordBrotherhoodController extends AbstractController {

	@Autowired
	private MiscellaneousRecordService	miscellaneousRecordService;

	@Autowired
	private HistoryService				historyService;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		MiscellaneousRecord miscellaneousRecord;
		try {
			miscellaneousRecord = this.miscellaneousRecordService.create();
			result = new ModelAndView("miscellaneousRecord/create");
			result.addObject("miscellaneousRecord", miscellaneousRecord);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int miscellaneousRecordId) {
		ModelAndView result;
		MiscellaneousRecord miscellaneousRecord;
		try {
			miscellaneousRecord = this.miscellaneousRecordService.findOne(miscellaneousRecordId);
			result = new ModelAndView("miscellaneousRecord/edit");
			result.addObject("miscellaneousRecord", miscellaneousRecord);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int miscellaneousRecordId) {
		ModelAndView result;
		MiscellaneousRecord miscellaneousRecord;
		try {
			miscellaneousRecord = this.miscellaneousRecordService.findOne(miscellaneousRecordId);
			result = new ModelAndView("miscellaneousRecord/display");
			result.addObject("miscellaneousRecord", miscellaneousRecord);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final MiscellaneousRecord miscellaneousRecord, final BindingResult binding) {
		ModelAndView result;
		if (miscellaneousRecord.getId() == 0)
			result = new ModelAndView("miscellaneousRecord/create");
		else
			result = new ModelAndView("miscellaneousRecord/edit");
		if (binding.hasErrors())
			result.addObject("miscellaneousRecord", miscellaneousRecord);
		else
			try {
				this.miscellaneousRecordService.save(miscellaneousRecord);
				result = new ModelAndView("redirect:/miscellaneousRecord/brotherhood/display.do?miscellaneousRecordId=" + miscellaneousRecord.getId());
			} catch (final Throwable oops) {
				result.addObject("message", "miscellaneousRecord.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final MiscellaneousRecord miscellaneousRecord) {
		ModelAndView result;
		try {
			this.miscellaneousRecordService.delete(miscellaneousRecord);
			result = new ModelAndView("redirect:/history/brotherhood/myList.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("miscellaneousRecord/edit");
			result.addObject("message", "miscellaneousRecord.commit.error");
		}
		return result;
	}

}
