
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.HistoryRepository;
import domain.History;

@Service
@Transactional
public class HistoryService {

	@Autowired
	private HistoryRepository	historyRepository;


	// FR 4.1.1 ACME PARADE
	public Collection<Double> statsRecordsPerHistory() {
		final Collection<Double> result = this.historyRepository.statsRecordsPerHistory();
		Assert.notNull(result);
		return result;
	}

	// FR 4.1.2 ACME PARADE
	public History findLargest() {
		final History result = this.historyRepository.largestHistory();
		Assert.notNull(result);
		return result;
	}

	// FR 4.1.3 ACME PARADE
	public Collection<History> findLargerThanAverage() {
		final Collection<History> result = this.historyRepository.largerHistoriesThanAverage();
		Assert.notNull(result);
		return result;
	}

	public History findByBrotherhood(final Integer id) {
		final History result = this.historyRepository.findByBrotherhood(id);
		return result;
	}

	public History findByInceptionRecord(final Integer id) {
		final History result = this.historyRepository.findByInceptionRecord(id);
		return result;
	}

}
