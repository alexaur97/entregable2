
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.HistoryRepository;
import domain.History;

@Service
@Transactional
public class HistoryService {

	@Autowired
	private HistoryRepository	historyRepository;


	//	// FR 4.1.1 ACME PARADE
	//	public Collection<Double> statsRecordsPerHistory() {
	//		final Collection<Double> result = this.historyRepository.statsRecordsPerHistory();
	//		Assert.notNull(result);
	//		return result;
	//	}
	//
	//	// FR 4.1.2 ACME PARADE
	//	public History findLargest() {
	//		final History result = this.historyRepository.largestHistory();
	//		Assert.notNull(result);
	//		return result;
	//	}
	//
	//	// FR 4.1.3 ACME PARADE
	//	public Collection<History> findLargerThanAverage() {
	//		final Collection<History> result = this.historyRepository.largerHistoriesThanAverage();
	//		Assert.notNull(result);
	//		return result;
	//	}
	public Collection<History> findAll() {

		final Collection<History> res = this.historyRepository.findAll();
		return res;

	}

	public History findOne(final int idHistory) {
		final History res = this.historyRepository.findOne(idHistory);
		return res;
	}

	public History findHistoryByBrotherhood(final int idBrotherhood) {
		final History res = this.historyRepository.findHistoryByBrotherhood(idBrotherhood);
		return res;

	}
	public History create() {
		return new History();

	}

}
