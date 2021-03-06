
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.Validator;

import repositories.AreaRepository;
import domain.Area;
import domain.Brotherhood;
import domain.Chapter;
import domain.Parade;

@Service
@Transactional
public class AreaService {

	//Repositorio

	@Autowired
	private AreaRepository			areaRepository;

	//Servicios
	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private BrotherhoodService		brotherhoodService;

	@Autowired
	private ChapterService			chapterService;

	@Autowired
	private ParadeService			paradeService;

	@Autowired
	private Validator				validator;


	//Metodos CRUD

	public Collection<Area> findAll() {
		final Collection<Area> result = this.areaRepository.findAll();
		return result;
	}

	public Area findOne(final int AreaId) {
		final Area area = this.areaRepository.findOne(AreaId);
		return area;
	}

	public Area save(final Area area) {
		final Area result;

		Assert.notNull(area);
		this.administratorService.findByPrincipal();
		result = this.areaRepository.save(area);
		return result;

	}

	public void delete(final Area area) {
		Assert.notNull(area);
		this.administratorService.findByPrincipal();
		Assert.isTrue(!this.hasSettle(area));
		this.areaRepository.delete(area.getId());

	}

	public Area create() {
		this.administratorService.findByPrincipal();
		return new Area();
	}

	public Boolean hasSettle(final Area area) {
		Boolean res = true;
		final Collection<Brotherhood> b = this.brotherhoodService.findBrotherhoodByArea(area.getId());

		if (b.isEmpty())
			res = false;
		return res;

	}

	public Collection<Area> areasSinAsignar() {
		Collection<Area> res;
		final Collection<Area> todas = this.areaRepository.findAll();
		final Collection<Area> asignadas = this.areaRepository.findAreasAsignadas();
		todas.removeAll(asignadas);
		res = todas;
		return res;
	}
	public Double ratioAreaWithoutChapter() {
		this.administratorService.findByPrincipal();

		final Double a = (double) this.chapterService.countChapterWithArea();
		final Double b = (double) this.areaRepository.count();
		final Double result = 1 - (a / b);
		return result;
	}
	public List<Chapter> chapters10() {
		Double acum = 0.0;
		Double media = 0.0;
		final List<Chapter> result = new ArrayList<>();
		this.administratorService.findByPrincipal();

		final Collection<Chapter> chapters = this.chapterService.findAll();
		final List<Double> comparar = new ArrayList<>();
		final List<Chapter> compararC = new ArrayList<>();
		if (chapters.isEmpty())
			media = 0.0;
		else {
			for (final Chapter chapter : chapters) {
				Double a;
				if (chapter.getArea() == null)
					a = 0.0;
				else {
					final Integer id = chapter.getArea().getId();
					final Collection<Parade> parades = this.paradeService.findParadesByArea(id);
					a = (double) parades.size();
					comparar.add(a);
					compararC.add(chapter);
				}
				acum = acum + a;
			}
			media = acum / chapters.size();
		}
		final Double mediaMas10 = media + (media * 0.1);
		Integer index = 0;
		for (final Double d : comparar) {
			if (d >= mediaMas10)
				result.add(compararC.get(index));
			index++;
		}
		return result;

	}
	//ORDEN DE LA LISTA MEDIA, MIN, MAX, DESV
	public List<Double> statsParadesChapters() {
		this.administratorService.findByPrincipal();
		Double min = -1.0;
		Double max = 0.0;
		Double acum = 0.0;
		Double media = 0.0;
		Double desv = 0.0;
		final List<Double> acumList = new ArrayList<>();

		final Collection<Chapter> chapters = this.chapterService.findAll();
		if (chapters.isEmpty()) {
			min = 0.0;
			media = 0.0;
			desv = 0.0;
		} else {
			for (final Chapter chapter : chapters) {
				Double a;
				if (chapter.getArea() == null)
					a = 0.0;
				else {
					final Integer id = chapter.getArea().getId();
					final Collection<Parade> parades = this.paradeService.findParadesByArea(id);
					a = (double) parades.size();
				}
				acum = acum + a;
				acumList.add(a);
				if (a < min || min == -1)
					min = a;
				if (a > max)
					max = a;
			}

			media = acum / chapters.size();
			for (Double a : acumList) {
				a = (a - media) * (a - media);
				desv = desv + a;
			}
			desv = desv / chapters.size();
			desv = Math.sqrt(desv);
		}
		final List<Double> result = new ArrayList<>();
		result.add(media);
		result.add(min);
		result.add(max);
		result.add(desv);
		return result;

	}
	public Collection<Area> findAreasLibres() {
		final Collection<Area> a = this.areaRepository.findAll();
		final Collection<Area> b = this.areaRepository.findAreasAsignadas();
		a.removeAll(b);
		return a;
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
