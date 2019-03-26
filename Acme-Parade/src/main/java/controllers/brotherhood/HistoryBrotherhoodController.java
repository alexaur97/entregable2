
package controllers.brotherhood;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BrotherhoodService;
import services.HistoryService;
import controllers.AbstractController;
import domain.Brotherhood;
import domain.History;
import domain.InceptionRecord;
import domain.LegalRecord;
import domain.LinkRecord;
import domain.MiscellaneousRecord;
import domain.PeriodRecord;

@Controller
@RequestMapping("history/brotherhood")
public class HistoryBrotherhoodController extends AbstractController {

	@Autowired
	private HistoryService		historyService;

	//	@Autowired
	//	private PeriodRecordService			periodRecorService;
	//
	//	@Autowired
	//	private MiscellaneousRecordService	miscellaneousRecordService;
	//
	//	@Autowired
	//	private InceptionRecordService		inceptionRecordService;
	//
	//	@Autowired
	//	private LegalRecordService			legalRecordService;
	//
	//	@Autowired
	//	private LinkRecordService			linkRecordService;
	//
	@Autowired
	private BrotherhoodService	brotherhoodService;


	@RequestMapping(value = "/myList", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		History history;
		try {

			final Brotherhood b = this.brotherhoodService.findByPrincipal();
			Assert.notNull(b.getId());
			history = this.historyService.findHistoryByBrotherhood(b.getId());

			if (!history.equals(null)) {

				Assert.isTrue(b.equals(history.getBrotherhood()));

				final InceptionRecord inceptionRecord = history.getInceptionRecord();
				final Collection<LegalRecord> legalRecord = history.getLegalRecord();
				final Collection<MiscellaneousRecord> miscellaneousRecord = history.getMiscellaneousRecord();
				final Collection<LinkRecord> linkRecord = history.getLinkRecord();
				final Collection<PeriodRecord> periodRecord = history.getPeriodRecord();
				result = new ModelAndView("history/myList");
				result.addObject("inceptionRecords", inceptionRecord);
				result.addObject("legalRecords", legalRecord);
				result.addObject("miscellaneousRecords", miscellaneousRecord);
				result.addObject("linkRecords", linkRecord);
				result.addObject("periodRecords", periodRecord);

			} else
				result = new ModelAndView("redirect:/history/create.do");

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result;
		final History history = new History();

		try {
			this.brotherhoodService.findByPrincipal();
			history.setId(0);
			result = this.createEditModelAndView(history);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/#");

		}

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int historyId) {
		ModelAndView res = new ModelAndView("history/edit");
		try {
			this.brotherhoodService.findByPrincipal();
			final History history = this.historyService.findOne(historyId);
			Assert.notNull(history);

			final Collection<PeriodRecord> pr = history.getPeriodRecord();
			final Collection<MiscellaneousRecord> mr = history.getMiscellaneousRecord();
			final InceptionRecord ir = history.getInceptionRecord();
			final Collection<LinkRecord> lir = history.getLinkRecord();
			final Collection<LegalRecord> ler = history.getLegalRecord();

			final Collection<History> hist = this.historyService.findAll();
			Assert.isTrue(hist.contains(history));
			res.addObject("history", history);
			res.addObject(ler);
			res.addObject(lir);
			res.addObject(ir);
			res.addObject(mr);
			res.addObject(pr);
		} catch (final Throwable oops) {
			res = new ModelAndView("redirect:/#");
		}
		return res;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int historyId) {
		ModelAndView result;
		History history;
		try {
			history = this.historyService.findOne(historyId);
			result = new ModelAndView("history/list");
			result.addObject("history", history);
			result.addObject("legalRecords", history.getLegalRecord());
			result.addObject("inceptionRecords", history.getInceptionRecord());
			result.addObject("linkRecords", history.getLinkRecord());
			result.addObject("miscellaneousRecords", history.getMiscellaneousRecord());
			result.addObject("periodRecords", history.getPeriodRecord());

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("history") final History history, final BindingResult binding) {
		ModelAndView res;

		if (binding.hasErrors())
			res = this.createEditModelAndView(history);

		else
			try {
				this.historyService.save(history);
				res = new ModelAndView("redirect:/history/list.do");

			} catch (final Throwable oops) {
				res = this.createEditModelAndView(history, "history.commit.error");
			}
		return res;
	}
	@RequestMapping(value = "edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final History history, final BindingResult binding) {
		ModelAndView result;

		try {

			this.historyService.delete(history);
			result = new ModelAndView("redirect:/history/list.do");
		} catch (final Throwable oops) {
			final String msg = oops.getMessage();
			result = this.createEditModelAndView(history, msg);

		}

		return result;
	}

	protected ModelAndView createEditModelAndView(final History history) {
		return this.createEditModelAndView(history, null);
	}
	protected ModelAndView createEditModelAndView(final History history, final String messageCode) {
		final ModelAndView res;
		res = new ModelAndView("history/edit");
		res.addObject("history", history);

		return res;
	}

}
