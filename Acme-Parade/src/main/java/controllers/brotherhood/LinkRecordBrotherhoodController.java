
package controllers.brotherhood;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BrotherhoodService;
import services.HistoryService;
import services.LinkRecordService;
import controllers.AbstractController;
import domain.Brotherhood;
import domain.LinkRecord;

@Controller
@RequestMapping("linkRecord/brotherhood")
public class LinkRecordBrotherhoodController extends AbstractController {

	@Autowired
	private LinkRecordService	linkRecordService;

	@Autowired
	private HistoryService		historyService;

	@Autowired
	private BrotherhoodService	brotherhoodService;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		LinkRecord linkRecord;
		try {
			linkRecord = this.linkRecordService.create();
			result = new ModelAndView("linkRecord/create");
			result.addObject("linkRecord", linkRecord);
			final Collection<Brotherhood> brotherhoods = this.brotherhoodService.findAll();
			final Brotherhood principal = this.brotherhoodService.findByPrincipal();
			brotherhoods.remove(principal);
			result.addObject("brotherhoods", brotherhoods);
			result.addObject("brotherhood", this.brotherhoodService.findByPrincipal());

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
			result.addObject("brotherhood", this.brotherhoodService.findByPrincipal());

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
			result.addObject("brotherhood", this.brotherhoodService.findByPrincipal());

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("linkRecord") LinkRecord linkRecord, final BindingResult binding) {
		ModelAndView result;
		linkRecord = this.linkRecordService.reconstruct(linkRecord, binding);
		if (linkRecord.getId() == 0) {
			result = new ModelAndView("linkRecord/create");
			final Collection<Brotherhood> brotherhoods = this.brotherhoodService.findAll();
			final Brotherhood principal = this.brotherhoodService.findByPrincipal();
			brotherhoods.remove(principal);
			result.addObject("brotherhoods", brotherhoods);
			result.addObject("brotherhood", this.brotherhoodService.findByPrincipal());

		} else
			result = new ModelAndView("linkRecord/edit");
		if (binding.hasErrors())
			result.addObject("linkRecord", linkRecord);
		else
			try {
				final LinkRecord saved = this.linkRecordService.save(linkRecord);
				result = new ModelAndView("redirect:/linkRecord/brotherhood/display.do?linkRecordId=" + saved.getId());
			} catch (final Throwable oops) {
				if (linkRecord.getBrotherhood() == null)
					result.addObject("message", "linkRecord.brotherhood.error");
				else
					result.addObject("message", "linkRecord.commit.error");
			}
		result.addObject("brotherhood", this.brotherhoodService.findByPrincipal());

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final LinkRecord linkRecord) {
		ModelAndView result;
		try {
			this.linkRecordService.delete(linkRecord);
			result = new ModelAndView("redirect:/history/brotherhood/myList.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("linkRecord/edit");
			result.addObject("message", "linkRecord.commit.error");
			result.addObject("brotherhood", this.brotherhoodService.findByPrincipal());

		}
		return result;
	}

}
