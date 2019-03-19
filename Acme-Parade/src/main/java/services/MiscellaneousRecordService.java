
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.MiscellaneousRecordRepository;
import domain.Brotherhood;
import domain.History;
import domain.MiscellaneousRecord;

@Service
@Transactional
public class MiscellaneousRecordService {

	@Autowired
	private MiscellaneousRecordRepository	miscellaneousRecordRepository;

	@Autowired
	private BrotherhoodService				brotherhoodService;

	@Autowired
	private HistoryService					historyService;


	public MiscellaneousRecord create() {
		this.brotherhoodService.findByPrincipal();
		return new MiscellaneousRecord();
	}

	public Collection<MiscellaneousRecord> findAll() {
		final Collection<MiscellaneousRecord> result = this.miscellaneousRecordRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public MiscellaneousRecord findOne(final Integer id) {
		final Brotherhood b = this.brotherhoodService.findByPrincipal();
		final MiscellaneousRecord result = this.miscellaneousRecordRepository.findOne(id);
		final History h2 = this.historyService.findByMiscellaneousRecord(result.getId());
		Assert.notNull(result);
		Assert.isTrue(h2.getBrotherhood().getId() == b.getId());
		return result;
	}

	public MiscellaneousRecord save(final MiscellaneousRecord miscellaneousRecord) {
		final Brotherhood b = this.brotherhoodService.findByPrincipal();
		final History history = this.historyService.findByBrotherhood(b.getId());
		if (miscellaneousRecord.getId() != 0) {
			final History h = this.historyService.findByMiscellaneousRecord(miscellaneousRecord.getId());
			Assert.isTrue(h.getBrotherhood().getId() == b.getId());
		} else {
			final Collection<MiscellaneousRecord> records = history.getMiscellaneousRecord();
			records.add(miscellaneousRecord);
			history.setMiscellaneousRecord(records);
			final History saved = this.historyService.save(history);
			Assert.notNull(saved);
		}

		final MiscellaneousRecord result = this.miscellaneousRecordRepository.save(miscellaneousRecord);
		Assert.notNull(result);

		return result;
	}

	public void delete(final MiscellaneousRecord miscellaneousRecord) {
		Assert.notNull(miscellaneousRecord);
		final Brotherhood principal = this.brotherhoodService.findByPrincipal();
		final Collection<MiscellaneousRecord> records = this.findByBrotherhood(principal.getId());
		Assert.isTrue(records.contains(miscellaneousRecord));
		final History history = this.historyService.findByBrotherhood(principal.getId());
		records.remove(miscellaneousRecord);
		history.setMiscellaneousRecord(records);
		this.historyService.save(history);
		this.miscellaneousRecordRepository.delete(miscellaneousRecord);
		final Collection<MiscellaneousRecord> m = this.miscellaneousRecordRepository.findAll();
		System.out.println(m);
	}

	public Collection<MiscellaneousRecord> findByBrotherhood(final Integer id) {
		final Collection<MiscellaneousRecord> result = this.miscellaneousRecordRepository.findByBrotherhood(id);
		return result;
	}

}
