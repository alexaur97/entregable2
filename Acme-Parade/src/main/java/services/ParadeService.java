
package services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ParadeRepository;
import security.Authority;
import security.LoginService;
import domain.Area;
import domain.Brotherhood;
import domain.Chapter;
import domain.Float;
import domain.Member;
import domain.Parade;
import domain.Path;
import domain.Segment;

@Service
@Transactional
public class ParadeService {

	// Repositorios

	@Autowired
	private ParadeRepository				paradeRepository;

	// Service
	@Autowired
	private BrotherhoodService				brotherhoodService;

	@Autowired
	private ConfigurationParametersService	configurationParametersService;

	@Autowired
	private MemberService					memberService;

	@Autowired
	private AdministratorService			administratorService;

	@Autowired
	private Validator						validator;

	@Autowired
	private ActorService					actorService;

	@Autowired
	private ChapterService					chapterService;

	@Autowired
	private SegmentService					segmentService;

	@Autowired
	private PathService						pathService;


	// Metodos CRUD FR 10.2

	public Collection<Parade> findAll() {
		final Collection<Parade> res = this.paradeRepository.findAll();
		// Assert.notNull(res);
		return res;
	}

	public Collection<Parade> findParadesAvailableForMember() {
		final Collection<Brotherhood> brotherhoods = this.brotherhoodService.findAll();
		final Member member = this.memberService.findByPrincipal();
		final Collection<Parade> res = new ArrayList<Parade>();
		for (final Brotherhood b : brotherhoods)
			if (b.getMembers().contains(member))
				res.addAll(this.paradeRepository.findParadesByBrotherhood(b.getId()));

		// Assert.notNull(res);
		return res;
	}

	public Collection<Parade> searchParades(final String keyword, final Date dateFrom, final Date dateTo, final Area area) {
		Collection<Parade> chaptersByKeyWord = new ArrayList<>();
		Collection<Parade> chaptersByDateFrom = new ArrayList<>();
		Collection<Parade> chaptersByDateTo = new ArrayList<>();
		Collection<Parade> chaptersByArea = new ArrayList<>();
		Collection<Parade> result = new ArrayList<>();
		if (keyword.isEmpty())
			chaptersByKeyWord = this.paradeRepository.findAll();
		else
			chaptersByKeyWord = this.paradeRepository.searchParadesKeyWord(keyword);
		if (Objects.equals(null, dateFrom))
			chaptersByDateFrom = this.paradeRepository.findAll();
		else
			chaptersByDateFrom = this.paradeRepository.searchParadesDateFrom(dateFrom);

		if (Objects.equals(null, dateTo))
			chaptersByDateTo = this.paradeRepository.findAll();
		else
			chaptersByDateTo = this.paradeRepository.searchParadesDateTo(dateTo);

		if (Objects.equals(null, area))
			chaptersByArea = this.paradeRepository.findAll();
		else
			chaptersByArea = this.paradeRepository.searchParadesArea(area.getId());

		chaptersByKeyWord.retainAll(chaptersByDateFrom);
		chaptersByKeyWord.retainAll(chaptersByDateTo);
		chaptersByKeyWord.retainAll(chaptersByArea);
		result = chaptersByKeyWord;
		return result;

	}
	public Parade findOne(final int paradeId) {
		Assert.notNull(paradeId);
		final Parade res = this.paradeRepository.findOne(paradeId);
		// Assert.notNull(res);
		return res;
	}

	public void delete(final Parade parade) {
		Assert.notNull(parade);
		// this.brotherhoodService.findByPrincipal();
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
	public Parade saveChapter(final Parade parade) {
		final Parade result;
		Assert.notNull(parade);
		if (parade.getStatus() == "REJECTED") {

			Assert.isTrue(!(parade.getExplanation() == null));
			if (!(parade.getExplanation() == null))
				Assert.isTrue(!(parade.getExplanation().isEmpty()));
		}
		final Area a = this.chapterService.findByPrincipal().getArea();
		Assert.isTrue(parade.getBrotherhood().getArea().equals(a));
		Assert.isTrue(this.actorService.authEdit(this.actorService.findByPrincipal(), "CHAPTER"));
		result = this.paradeRepository.save(parade);
		return result;

	}
	public Parade create() {
		// Assert.isTrue(this.brotherhoodService.findByPrincipal().equals(Authority.BROTHERHOOD));
		return new Parade();
	}

	public Collection<Parade> findParadesByBrotherhoodForList(final int idBrotherhood) {
		// Assert.notNull(idBrotherhood);
		final Collection<Parade> res = this.paradeRepository.findParadesByBrotherhood(idBrotherhood);
		return res;
	}

	// FR 8.2 - FR 10.2

	public Collection<Parade> findParadesByBrotherhood(final int idBrotherhood) {
		// Assert.notNull(idBrotherhood);
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

	public Collection<Parade> findFinalParades() {
		final Collection<Parade> all = this.paradeRepository.findAll();
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
		// Assert.notNull(res);
		return res;
	}

	// Other Methods--------------------

	public Parade reconstruct(final Parade parade, final BindingResult binding) {
		final Parade res = parade;
		if (parade.getMode().equals("DRAFT"))
			res.setStatus("CLEARED");
		else
			res.setStatus("SUBMITTED");

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
			res.setPaths(p2.getPaths());

		}
		this.validator.validate(res, binding);

		return res;
	}

	public String creaString() {
		String cadena = null;

		final char[] elementos = {
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
		};

		final char[] conjunto = new char[5];

		for (int i = 0; i < 5; i++) {

			final int el = (int) (Math.random() * 25);

			conjunto[i] = elementos[el];
		}
		cadena = new String(conjunto);

		return cadena;
	}
	public Collection<Parade> findParadesByArea(final int id) {
		Collection<Parade> res;
		res = this.paradeRepository.findParadesByArea(id);
		return res;
	}

	public Parade rejectRecostruction(final Parade parade, final BindingResult binding) {
		final Parade res = parade;
		final Parade a = this.findOne(parade.getId());
		res.setStatus("REJECTED");
		res.setBrotherhood(a.getBrotherhood());
		res.setDescription(a.getDescription());
		res.setFloats(a.getFloats());
		res.setMode(a.getMode());
		res.setMoment(a.getMoment());
		res.setPaths(a.getPaths());
		res.setTicker(a.getTicker());
		res.setTitle(a.getTitle());
		return res;
	}

	public Parade copyParade(final Parade parade) {

		final Brotherhood brotherhood = this.brotherhoodService.findByPrincipal();

		// nos aseguramos de que el brotherhood que tiene la parade es el mismo brotherhood que esta logueado
		Assert.isTrue(parade.getBrotherhood().equals(brotherhood));

		boolean finalStatus = true;
		if (!(parade.getMode().equals("FINAL")))
			finalStatus = false;
		Assert.isTrue(finalStatus, "finalStatus");

		final Parade res = new Parade();

		final String pattern = "YYMMdd";
		final DateFormat df = new SimpleDateFormat(pattern);
		final Date fecha = parade.getMoment();
		final String fechaFormateada = df.format(fecha);

		final String cadena = this.creaString();
		final String ticker = fechaFormateada + "-" + cadena;
		res.setTicker(ticker);

		final Collection<Float> floats = new ArrayList<>(parade.getFloats());
		final Collection<Path> paths = new ArrayList<>(parade.getPaths());
		res.setStatus("CLEARED");
		res.setTitle(parade.getTitle());
		res.setExplanation(null);
		res.setMode("DRAFT");
		res.setDescription(parade.getDescription());
		res.setFloats(floats);
		res.setVersion(parade.getVersion());
		res.setBrotherhood(parade.getBrotherhood());
		res.setMoment(parade.getMoment());

		for (final Path p : paths) {
			final Collection<Path> copiados = new ArrayList<>();
			final Path copiado = this.copyPath(p);
			copiados.add(copiado);
			res.setPaths(copiados);
		}

		return res;
	}

	public Segment copySegment(final Segment segment) {

		final Segment res = new Segment();

		res.setOriginX(segment.getOriginX());
		res.setOriginY(segment.getOriginY());
		res.setDestinationX(segment.getDestinationX());
		res.setDestinationY(segment.getDestinationY());
		res.setOriginTime(segment.getOriginTime());
		res.setDestinationTime(segment.getDestinationTime());
		res.setSequence(segment.getSequence());
		res.setVersion(segment.getVersion());

		this.segmentService.save(res);

		return res;
	}
	public Path copyPath(final Path path) {

		final Path res = new Path();

		res.setVersion(path.getVersion());

		final Collection<Segment> segments = new ArrayList<>(path.getSegments());

		for (final Segment s : segments) {
			final Collection<Segment> copiados = new ArrayList<>();
			final Segment copiado = this.copySegment(s);
			copiados.add(copiado);
			res.setSegments(copiados);

		}

		this.pathService.save(path);
		return res;

	}

	public Collection<Parade> findParadesAcceptedByBrotherhood(final int idBrotherhood) {
		final Collection<Parade> res = this.paradeRepository.findParadesAcceptedByBrotherhood(idBrotherhood);
		return res;
	}

	public Collection<Parade> findParadesRejectedByBrotherhood(final int idBrotherhood) {
		final Collection<Parade> res = this.paradeRepository.findParadesRejectedByBrotherhood(idBrotherhood);
		return res;
	}

	public Collection<Parade> findParadesSubmittedByBrotherhood(final int idBrotherhood) {
		final Collection<Parade> res = this.paradeRepository.findParadesSubmittedByBrotherhood(idBrotherhood);
		return res;
	}

	public Collection<Parade> findParadesClearedByBrotherhood(final int idBrotherhood) {
		final Collection<Parade> res = this.paradeRepository.findParadesClearedByBrotherhood(idBrotherhood);
		return res;
	}

	// FR 3.3
	public Collection<Parade> findByPrincipal() {
		final Authority auth = new Authority();
		auth.setAuthority(Authority.BROTHERHOOD);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(auth));
		final Collection<Parade> result;
		final Brotherhood b = this.brotherhoodService.findByPrincipal();
		result = this.findParadesByBrotherhood(b.getId());
		return result;
	}
	public Collection<Double> modeStat() {
		this.administratorService.findByPrincipal();
		final Collection<Double> result = this.paradeRepository.modeStats();
		return result;
	}
	public Collection<Double> statusStat() {
		this.administratorService.findByPrincipal();
		final Collection<Double> result = this.paradeRepository.statusStats();
		return result;
	}

	// FR 3.3
	public Parade addPath(final Parade parade, final Path path) {
		Parade result;
		final Collection<Path> paths = parade.getPaths();
		paths.add(path);
		parade.setPaths(paths);
		result = this.save(parade);
		return result;
	}

	public Parade findParadeByPath(final Path path) {
		final Collection<Parade> parades = this.findByPrincipal();
		Parade result = null;
		for (final Parade p : parades)
			if (p.getPaths().contains(path))
				result = p;
		Assert.notNull(result);
		return result;
	}
	public Collection<Parade> findFinalParadeByArea(final int id) {
		return this.paradeRepository.findFinalParadeByArea(id);
	}
	public void restriccionesRejectGet(final Integer paradeId) {
		final Parade parade = this.paradeRepository.findOne(paradeId);
		Assert.notNull(paradeId);
		final Area a = this.chapterService.findByPrincipal().getArea();
		Assert.isTrue(parade.getBrotherhood().getArea().equals(a));
		Assert.isTrue(parade.getStatus().equals("SUBMITTED"));
	}
	public Parade acceptParadeChanges(final int paradeId) {
		Assert.notNull(paradeId);
		final Chapter chapter = this.chapterService.findByPrincipal();
		Assert.notNull(chapter.getArea());
		final Parade parade = this.paradeRepository.findOne(paradeId);
		Assert.isTrue(chapter.getArea().equals(parade.getBrotherhood().getArea()));
		Assert.isTrue(parade.getStatus().equals("SUBMITTED"));
		parade.setStatus("ACCEPTED");
		return parade;

	}
}
