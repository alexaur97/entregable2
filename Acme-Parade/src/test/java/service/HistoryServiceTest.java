
package service;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import services.HistoryService;
import utilities.AbstractTest;
import domain.Brotherhood;
import domain.History;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class HistoryServiceTest extends AbstractTest {

	@Autowired
	private HistoryService	historyService;


	// FR 4.1.1 ACME PARADE
	@Test
	public void testStatsHistories() {
		super.authenticate("admin");
		final Collection<Double> s = this.historyService.statsRecordsPerHistory();
		final Collection<History> larger = this.historyService.findLargerThanAverage();
		final History largest = this.historyService.findLargest();
		final Collection<History> histories = this.historyService.findAll();
		Brotherhood largestReal = new Brotherhood();
		int larguestSize = 0;
		Double max = 0.0;
		Double min = 0.0;
		Double total = 0.0;
		for (final History h : histories) {
			final int a = 1 + h.getLegalRecord().size() + h.getLinkRecord().size() + h.getMiscellaneousRecord().size() + h.getPeriodRecord().size();
			total += a;
			if (min == 0.0 || a < min)
				min = (double) a;
			if (a > larguestSize) {
				larguestSize = a;
				max = (double) larguestSize;
				largestReal = h.getBrotherhood();
			}
		}
		//		final Double media = total / histories.size();
		//		Double deviation = 0.0;
		//		for (final History h : histories) {
		//			final int a = 1 + h.getLegalRecord().size() + h.getLinkRecord().size() + h.getMiscellaneousRecord().size() + h.getPeriodRecord().size();
		//			final double b = (media - a) * (media - a);
		//			deviation = deviation + b;
		//			if (a > media)
		//				Assert.isTrue(larger.contains(h));
		//		}
		//		deviation = deviation / histories.size();
		//		deviation = Math.sqrt(deviation);

		Assert.isTrue(largestReal.equals(largest.getBrotherhood()));

		super.unauthenticate();
	}
	
	// FR 4.1.1 ACME PARADE - Not logged as Administrator
	@Test(expected = IllegalArgumentException.class)
	public void testStatsHistoriesNegative() {
		super.authenticate(null);
		final Collection<Double> s = this.historyService.statsRecordsPerHistory();
		final Collection<History> larger = this.historyService.findLargerThanAverage();
		final History largest = this.historyService.findLargest();
		final Collection<History> histories = this.historyService.findAll();
		Brotherhood largestReal = new Brotherhood();
		int larguestSize = 0;
		Double max = 0.0;
		Double min = 0.0;
		Double total = 0.0;
		for (final History h : histories) {
			final int a = 1 + h.getLegalRecord().size() + h.getLinkRecord().size() + h.getMiscellaneousRecord().size() + h.getPeriodRecord().size();
			total += a;
			if (min == 0.0 || a < min)
				min = (double) a;
			if (a > larguestSize) {
				larguestSize = a;
				max = (double) larguestSize;
				largestReal = h.getBrotherhood();
			}
		}
		//		final Double media = total / histories.size();
		//		Double deviation = 0.0;
		//		for (final History h : histories) {
		//			final int a = 1 + h.getLegalRecord().size() + h.getLinkRecord().size() + h.getMiscellaneousRecord().size() + h.getPeriodRecord().size();
		//			final double b = (media - a) * (media - a);
		//			deviation = deviation + b;
		//			if (a > media)
		//				Assert.isTrue(larger.contains(h));
		//		}
		//		deviation = deviation / histories.size();
		//		deviation = Math.sqrt(deviation);

		Assert.isTrue(largestReal.equals(largest.getBrotherhood()));

		super.unauthenticate();
	}
}
