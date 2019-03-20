
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.HistoryRepository;
import security.Authority;
import security.LoginService;
import domain.Brotherhood;
import domain.History;

@Service
@Transactional
public class HistoryService {

	@Autowired
	private HistoryRepository	historyRepository;

	//servicios
	@Autowired
	private BrotherhoodService	brotherhoodService;


	// FR 4.1.1 ACME PARADE
	public Collection<Double> statsRecordsPerHistory() {
		final Authority auth = new Authority();
		auth.setAuthority(Authority.ADMINISTRATOR);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(auth));
		Collection<Double> result;
		result = this.historyRepository.statsRecordsPerHistory();
		Assert.notNull(result);
		return result;
	}

	// FR 4.1.2 ACME PARADE
	public History findLargest() {
		final Authority auth = new Authority();
		auth.setAuthority(Authority.ADMINISTRATOR);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(auth));
		final History result = this.historyRepository.largestHistory();
		Assert.notNull(result);
		return result;
	}

	// FR 4.1.3 ACME PARADE
	public Collection<History> findLargerThanAverage() {
		final Authority auth = new Authority();
		auth.setAuthority(Authority.ADMINISTRATOR);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(auth));
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

	public History findByLinkRecord(final Integer id) {
		final History result = this.historyRepository.findByLinkRecord(id);
		return result;
	}
	public History findByUserId(final int id) {
		final Brotherhood b = this.brotherhoodService.findByPrincipal();
		final History result = this.historyRepository.findByBrotherhood(b.getId());
		return result;
	}

	public void delete(final History history) {
		final Brotherhood b = this.brotherhoodService.findByPrincipal();
		Assert.isTrue(history.getBrotherhood().equals(b));
		this.historyRepository.delete(history.getId());

	}

}
