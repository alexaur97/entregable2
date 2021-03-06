
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.LegalRecordRepository;
import domain.Brotherhood;
import domain.History;
import domain.LegalRecord;

@Service
@Transactional
public class LegalRecordService {

	//repositorios
	@Autowired
	private LegalRecordRepository	legalRecordRepository;

	//Servicios
	@Autowired
	private BrotherhoodService		brotherhoodService;

	@Autowired
	private HistoryService			historyService;


	public LegalRecord create() {
		this.brotherhoodService.findByPrincipal();
		return new LegalRecord();
	}

	public Collection<LegalRecord> findAll() {
		final Collection<LegalRecord> result = this.legalRecordRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public LegalRecord findOne(final Integer id) {
		final Brotherhood b = this.brotherhoodService.findByPrincipal();
		final LegalRecord result = this.legalRecordRepository.findOne(id);
		final History h2 = this.historyService.findByLegalRecord(result.getId());
		Assert.notNull(result);
		Assert.isTrue(h2.getBrotherhood().getId() == b.getId());
		return result;
	}
	public void delete(final int id) {
		final Brotherhood b = this.brotherhoodService.findByPrincipal();
		final History h = this.historyService.findByBrotherhood(b.getId());
		final LegalRecord legalRecord = this.legalRecordRepository.findOne(id);
		final Collection<LegalRecord> records = h.getLegalRecord();
		Assert.isTrue(records.contains(legalRecord));
		records.remove(legalRecord);
		h.setLegalRecord(records);
		this.historyService.save(h);
		this.legalRecordRepository.delete(id);
	}
	public LegalRecord save(final LegalRecord legalRecord) {
		final Brotherhood b = this.brotherhoodService.findByPrincipal();
		final History h = this.historyService.findByBrotherhood(b.getId());
		if (legalRecord.getId() != 0) {
			Assert.isTrue(h.getBrotherhood().getId() == b.getId());
			Assert.isTrue(h.getLegalRecord().contains(legalRecord));
		} else {
			final Collection<LegalRecord> records = h.getLegalRecord();
			records.add(legalRecord);
			h.setLegalRecord(records);
			this.historyService.save(h);
		}
		final LegalRecord result = this.legalRecordRepository.save(legalRecord);
		Assert.notNull(result);

		return result;
	}
}
