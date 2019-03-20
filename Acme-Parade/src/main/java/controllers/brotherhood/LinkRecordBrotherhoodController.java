
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
import services.LinkRecordService;
import controllers.AbstractController;
import domain.History;
import domain.LinkRecord;

@Controller
@RequestMapping("linkRecord/brotherhood")
public class LinkRecordBrotherhoodController extends AbstractController {

	@Autowired
	private LinkRecordService	linkRecordService;

	@Autowired
	private HistoryService		historyService;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		LinkRecord linkRecord;
		try {
			linkRecord = this.linkRecordService.create();
			result = new ModelAndView("linkRecord/create");
			result.addObject("linkRecord", linkRecord);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int linkRecordId) {
		ModelAndView result;
		LinkRecord linkRecord;
		try {
			linkRecord = this.linkRecordService.findOne(linkRecordId);
			result = new ModelAndView("linkRecord/edit");
			result.addObject("linkRecord", linkRecord);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int linkRecordId) {
		ModelAndView result;
		LinkRecord linkRecord;
		try {
			linkRecord = this.linkRecordService.findOne(linkRecordId);
			result = new ModelAndView("linkRecord/display");
			result.addObject("linkRecord", linkRecord);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final LinkRecord linkRecord, final BindingResult binding) {
		ModelAndView result;
		if (linkRecord.getId() == 0)
			result = new ModelAndView("linkRecord/create");
		else
			result = new ModelAndView("linkRecord/edit");
		if (binding.hasErrors())
			result.addObject("linkRecord", linkRecord);
		else
			try {
				this.linkRecordService.save(linkRecord);
				result = new ModelAndView("redirect:/linkRecord/brotherhood/display.do?linkRecordId=" + linkRecord.getId());
			} catch (final Throwable oops) {
				result.addObject("message", "linkRecord.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final LinkRecord linkRecord) {
		ModelAndView result;
		try {
			final History history = this.historyService.findByLinkRecord(linkRecord.getId());
			this.linkRecordService.delete(linkRecord);
			result = new ModelAndView("redirect:/history/brotherhood/display.do?historyId=" + history.getId());
		} catch (final Throwable oops) {
			result = new ModelAndView("linkRecord/edit");
			result.addObject("message", "linkRecord.commit.error");
		}
		return result;
	}

}
