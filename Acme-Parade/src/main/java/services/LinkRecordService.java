
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.LinkRecordRepository;
import domain.Brotherhood;
import domain.History;
import domain.LinkRecord;

@Service
@Transactional
public class LinkRecordService {

	@Autowired
	private LinkRecordRepository	linkRecordRepository;

	@Autowired
	private BrotherhoodService		brotherhoodService;

	@Autowired
	private HistoryService			historyService;

	@Autowired
	private Validator				validator;


	public LinkRecord create() {
		this.brotherhoodService.findByPrincipal();
		return new LinkRecord();
	}

	public Collection<LinkRecord> findAll() {
		final Collection<LinkRecord> result = this.linkRecordRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public LinkRecord findOne(final Integer id) {
		final Brotherhood b = this.brotherhoodService.findByPrincipal();
		final LinkRecord result = this.linkRecordRepository.findOne(id);
		final History h2 = this.historyService.findByLinkRecord(result.getId());
		Assert.notNull(result);
		Assert.isTrue(h2.getBrotherhood().getId() == b.getId());
		return result;
	}

	public LinkRecord save(final LinkRecord linkRecord) {
		final Brotherhood principal = this.brotherhoodService.findByPrincipal();
		final History history = this.historyService.findByBrotherhood(principal.getId());
		Assert.notNull(linkRecord.getBrotherhood());
		if (linkRecord.getId() != 0) {
			final History h = this.historyService.findByLinkRecord(linkRecord.getId());
			Assert.isTrue(h.getBrotherhood().getId() == principal.getId());
		} else {
			Assert.isTrue(!linkRecord.getBrotherhood().equals(principal));
			final Collection<LinkRecord> records = history.getLinkRecord();
			records.add(linkRecord);
			history.setLinkRecord(records);
			final History saved = this.historyService.save(history);
			Assert.notNull(saved);
		}

		final LinkRecord result = this.linkRecordRepository.save(linkRecord);
		Assert.notNull(result);

		return result;
	}

	public void delete(final LinkRecord linkRecord) {
		Assert.notNull(linkRecord);
		final Brotherhood principal = this.brotherhoodService.findByPrincipal();
		final Collection<LinkRecord> records = this.findByBrotherhood(principal.getId());
		Assert.isTrue(records.contains(linkRecord));
		final History history = this.historyService.findByBrotherhood(principal.getId());
		records.remove(linkRecord);
		history.setLinkRecord(records);
		this.historyService.save(history);
		this.linkRecordRepository.delete(linkRecord);
		final Collection<LinkRecord> m = this.linkRecordRepository.findAll();
		System.out.println(m);
	}

	public Collection<LinkRecord> findByBrotherhood(final Integer id) {
		final Collection<LinkRecord> result = this.linkRecordRepository.findByBrotherhood(id);
		return result;
	}

	public LinkRecord reconstruct(final LinkRecord linkRecord, final BindingResult binding) {
		final LinkRecord result = linkRecord;
		if (linkRecord.getId() != 0) {
			final LinkRecord lr = this.findOne(linkRecord.getId());
			result.setBrotherhood(lr.getBrotherhood());
		}
		this.validator.validate(result, binding);
		return result;
	}

}
