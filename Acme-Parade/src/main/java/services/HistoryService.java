
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.HistoryRepository;
import domain.Brotherhood;
import domain.History;

@Service
@Transactional
public class HistoryService {

	@Autowired
	private HistoryRepository	historyRepository;

	@Autowired
	private BrotherhoodService	brotherhoodService;


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
	public History findByBrotherhood(final Integer id) {
		final History result = this.historyRepository.findByBrotherhood(id);
		return result;
	}

	public History findByInceptionRecord(final Integer id) {
		final History result = this.historyRepository.findByInceptionRecord(id);
		return result;
	}

	public History findByMiscellaneousRecord(final Integer id) {
		final History result = this.historyRepository.findByMiscellaneousRecord(id);
		return result;
	}

	public History save(final History history) {
		final Brotherhood principal = this.brotherhoodService.findByPrincipal();
		if (history.getId() == 0)
			history.setBrotherhood(principal);
		else
			Assert.isTrue(history.getBrotherhood().getId() == principal.getId());
		final History saved = this.historyRepository.save(history);
		return saved;
	}
}
