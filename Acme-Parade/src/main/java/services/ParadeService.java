
package services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ParadeRepository;
import domain.Brotherhood;
import domain.Member;
import domain.Parade;

@Service
@Transactional
public class ParadeService {

	//Repositorios

	@Autowired
	private ParadeRepository		paradeRepository;

	//Service
	@Autowired
	private BrotherhoodService		brotherhoodService;

	@Autowired
	private MemberService			memberService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private Validator				validator;


	//Metodos CRUD  FR 10.2

	public Collection<Parade> findAll() {
		final Collection<Parade> res = this.paradeRepository.findAll();
		//Assert.notNull(res);
		return res;

	}

	public Collection<Parade> findParadesAvailableForMember() {
		final Collection<Brotherhood> brotherhoods = this.brotherhoodService.findAll();
		final Member member = this.memberService.findByPrincipal();
		final Collection<Parade> res = new ArrayList<Parade>();
		for (final Brotherhood b : brotherhoods)
			if (b.getMembers().contains(member))
				res.addAll(this.paradeRepository.findParadesByBrotherhood(b.getId()));

		//Assert.notNull(res);
		return res;
	}

	public Parade findOne(final int paradeId) {
		final Parade res = this.paradeRepository.findOne(paradeId);
		//Assert.notNull(res);
		return res;
	}

	public void delete(final Parade parade) {
		Assert.notNull(parade);
		//this.brotherhoodService.findByPrincipal();
		this.paradeRepository.delete(parade.getId());
	}

	public Parade save(final Parade parade) {
		final Parade result;
		final Brotherhood bh = this.brotherhoodService.findByPrincipal();
		Assert.notNull(parade);

		parade.setBrotherhood(bh);

		result = this.paradeRepository.save(parade);
		return result;

	}

	public Parade create() {
		//Assert.isTrue(this.brotherhoodService.findByPrincipal().equals(Authority.BROTHERHOOD));
		return new Parade();
	}

	public Collection<Parade> findParadesByBrotherhoodForList(final int idBrotherhood) {
		//Assert.notNull(idBrotherhood);
		final Collection<Parade> res = this.paradeRepository.findParadesByBrotherhood(idBrotherhood);
		return res;
	}

	// FR 8.2 - FR 10.2

	public Collection<Parade> findParadesByBrotherhood(final int idBrotherhood) {
		//Assert.notNull(idBrotherhood);
		final Collection<Parade> res = this.paradeRepository.findParadesByBrotherhood(idBrotherhood);
		return res;
	}
	public Collection<Parade> findFinalParadesByBrotherhood(final int idBrotherhood) {
		final Collection<Parade> all = this.paradeRepository.findParadesByBrotherhood(idBrotherhood);
		final Collection<Parade> res = new ArrayList<>();
		for (final Parade parade : all)
			if (parade.getMode().equals("FINAL"))
				res.add(parade);
		return res;
	}

	// FR 12.3.5

	public Collection<Parade> paradesBefore30Days() {
		this.administratorService.findByPrincipal();
		final Collection<Parade> res = this.paradeRepository.paradesBefore30Days();
		//Assert.notNull(res);
		return res;
	}

	//Other Methods--------------------

	public Parade reconstruct(final Parade parade, final BindingResult binding) {
		final Parade res = parade;
		if (parade.getMoment() != null) {

			final String pattern = "YYMMdd";
			final DateFormat df = new SimpleDateFormat(pattern);
			final Date fecha = parade.getMoment();
			final String fechaFormateada = df.format(fecha);

			final String cadena = this.creaString();
			final String ticker = fechaFormateada + "-" + cadena;
			res.setTicker(ticker);
		}

		if (parade.getId() != 0) {

			final Parade p2 = this.paradeRepository.findOne(res.getId());
			res.setBrotherhood(p2.getBrotherhood());

		}
		this.validator.validate(res, binding);

		return res;
	}

	public String creaString() {
		final char[] elementos = {
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'Ñ', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
		};

		final char[] conjunto = new char[5];
		String cadena;
		for (int i = 0; i < 5; i++) {

			final int el = (int) (Math.random() * 27);

			conjunto[i] = elementos[el];
		}
		cadena = new String(conjunto);
		return cadena;
	}

}
