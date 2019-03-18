
package services;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.Validator;

import repositories.InceptionRecordRepository;
import domain.Brotherhood;
import domain.History;
import domain.InceptionRecord;

@Service
@Transactional
public class InceptionRecordService {

	@Autowired
	private InceptionRecordRepository	inceptionRecordRepository;

	@Autowired
	private BrotherhoodService			brotherhoodService;

	@Autowired
	private HistoryService				historyService;

	@Autowired
	private Validator					validator;


	public InceptionRecord create() {
		this.brotherhoodService.findByPrincipal();
		return new InceptionRecord();
	}

	public Collection<InceptionRecord> findAll() {
		final Collection<InceptionRecord> result = this.inceptionRecordRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public InceptionRecord findOne(final Integer id) {
		final Brotherhood b = this.brotherhoodService.findByPrincipal();
		final InceptionRecord result = this.inceptionRecordRepository.findOne(id);
		final History h2 = this.historyService.findByInceptionRecord(result.getId());
		Assert.notNull(result);
		Assert.isTrue(h2.getBrotherhood().getId() == b.getId());
		return result;
	}

	public InceptionRecord save(final InceptionRecord inceptionRecord) {
		final Brotherhood b = this.brotherhoodService.findByPrincipal();
		if (inceptionRecord.getId() != 0) {
			final History h = this.historyService.findByInceptionRecord(inceptionRecord.getId());
			Assert.isTrue(h.getBrotherhood().getId() == b.getId());
		}
		final InceptionRecord result = this.inceptionRecordRepository.save(inceptionRecord);
		Assert.notNull(result);

		return result;
	}
	public InceptionRecord findByBrotherhood(final Integer id) {
		final InceptionRecord result = this.inceptionRecordRepository.findByBrotherhood(id);
		return result;
	}

	//	public InceptionRecord reconstruct(final InceptionRecord inceptionRecord, final BindingResult binding) {
	//		final InceptionRecord result;
	//		if (inceptionRecord.getId() == 0)
	//			result = inceptionRecord;
	//		else
	//			result = this.inceptionRecordRepository.findOne(inceptionRecord.getId());
	//
	//		this.validator.validate(result, binding);
	//		return result;
	//	}

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
