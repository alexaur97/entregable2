
package service;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import services.HistoryService;
import utilities.AbstractTest;
import domain.History;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class HistoryServiceTest extends AbstractTest {

	@Autowired
	private HistoryService	historyService;


	@Test
	public void testStatsHistories() {
		super.authenticate("admin1");
		final Collection<Double> s1 = this.historyService.statsRecordsPerHistory();
		final Collection<History> s2 = this.historyService.findLargerThanAverage();
		final History s3 = this.historyService.findLargest();
		super.unauthenticate();
	}
}
