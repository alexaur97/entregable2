
package controllers.all;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.HistoryService;
import controllers.AbstractController;
import domain.History;
import domain.InceptionRecord;
import domain.LegalRecord;
import domain.LinkRecord;
import domain.MiscellaneousRecord;
import domain.PeriodRecord;

@Controller
@RequestMapping("/history")
public class HistoryController extends AbstractController {

	// Supporting services ----------------------------------------------------

	@Autowired
	private HistoryService	historyService;


	//	@Autowired
	//	private BrotherhoodService	brotherhoodService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(final Integer brotherhoodId) {
		ModelAndView result;
		History history;
		try {
			Assert.notNull(brotherhoodId);

			history = this.historyService.findHistoryByBrotherhood(brotherhoodId);
			final InceptionRecord inceptionRecord = history.getInceptionRecord();
			final Collection<LegalRecord> legalRecord = history.getLegalRecord();
			final Collection<MiscellaneousRecord> miscellaneousRecord = history.getMiscellaneousRecord();
			final Collection<LinkRecord> linkRecord = history.getLinkRecord();
			final Collection<PeriodRecord> periodRecord = history.getPeriodRecord();
			result = new ModelAndView("history/list");
			result.addObject("requestURI", "history/list.do?=" + brotherhoodId);
			result.addObject("inceptionRecords", inceptionRecord);
			result.addObject("legalRecords", legalRecord);
			result.addObject("miscellaneousRecords", miscellaneousRecord);
			result.addObject("linkRecords", linkRecord);
			result.addObject("periodRecords", periodRecord);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}

}
