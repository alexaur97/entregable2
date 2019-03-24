
package services;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.PeriodRecordRepository;
import domain.Brotherhood;
import domain.History;
import domain.PeriodRecord;

@Service
@Transactional
public class PeriodRecordService {

	@Autowired
	private PeriodRecordRepository	periodRecordRepository;

	//Servicios
	@Autowired
	private BrotherhoodService		brotherhoodService;

	@Autowired
	private HistoryService			historyService;


	public PeriodRecord create() {
		this.brotherhoodService.findByPrincipal();
		return new PeriodRecord();
	}

	public PeriodRecord save(final PeriodRecord periodRecord) {
		final Brotherhood b = this.brotherhoodService.findByPrincipal();
		final History h = this.historyService.findByBrotherhood(b.getId());
		final Integer startYear = periodRecord.getStartYear();
		final Integer endYear = periodRecord.getEndYear();
		Assert.isTrue(startYear <= endYear);
		if (periodRecord.getId() != 0)
			Assert.isTrue(h.getBrotherhood().getId() == b.getId());
		else {
			final Collection<PeriodRecord> records = h.getPeriodRecord();
			records.add(periodRecord);
			h.setPeriodRecord(records);
			this.historyService.save(h);
		}
		final PeriodRecord result = this.periodRecordRepository.save(periodRecord);
		Assert.notNull(result);

		return result;
	}
	public Collection<PeriodRecord> findAll() {
		final Collection<PeriodRecord> result = this.periodRecordRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public PeriodRecord findOne(final Integer id) {
		final Brotherhood b = this.brotherhoodService.findByPrincipal();
		final PeriodRecord result = this.periodRecordRepository.findOne(id);
		final History h2 = this.historyService.findByPeriodRecord(result.getId());
		Assert.notNull(result);
		Assert.isTrue(h2.getBrotherhood().getId() == b.getId());
		return result;
	}
	public void delete(final int id) {
		final Brotherhood b = this.brotherhoodService.findByPrincipal();
		final History h = this.historyService.findByBrotherhood(b.getId());
		final PeriodRecord periodRecord = this.periodRecordRepository.findOne(id);
		final Collection<PeriodRecord> records = h.getPeriodRecord();
		records.remove(periodRecord);
		h.setPeriodRecord(records);
		this.historyService.save(h);
		this.periodRecordRepository.delete(id);
	}
	public Boolean validatePictures(final Collection<String> pictures) {
		final String regex = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=_|!:,.;]*[-a-zA-Z0-9+&@#/%=_|]";
		final Pattern patt = Pattern.compile(regex);
		Boolean b = true;

		if (!pictures.isEmpty())
			for (final String s : pictures) {
				final Matcher matcher = patt.matcher(s);
				if (!matcher.matches()) {
					b = false;
					break;
				}
			}
		return b;
	}

}
