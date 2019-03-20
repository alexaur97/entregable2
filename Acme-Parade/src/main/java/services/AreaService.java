
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

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
	public Double ratioAreaWithoutChapter() {
		final Collection<Area> areas = this.areaRepository.findAll();
		final Collection<Chapter> chapters = this.chapterService.findAll();
		final Collection<Area> areasSin = new ArrayList<>();
		final Double res;
		Boolean acum;
		for (final Area area : areas) {
			acum = true;
			for (final Chapter chapter : chapters)
				if (chapter.getArea() == area)
					acum = false;
			if (acum)
				areasSin.add(area);
		}
		res = (double) areasSin.size() / areas.size();
		return res;
	}
	public List<Chapter> chapters10() {
		Double acum = 0.0;
		Double media = 0.0;
		final List<Chapter> result = new ArrayList<>();

		final Collection<Chapter> chapters = this.chapterService.findAll();
		for (final Chapter chapter : chapters) {
			final Integer id = chapter.getArea().getId();
			final Collection<Parade> parades = this.paradeService.findParadesByArea(id);
			final Double a = (double) parades.size();
			acum = acum + a;
		}
		media = acum / chapters.size();
		final Double mediaMas10 = media + (media * 0.1);
		for (final Chapter chapter : chapters) {
			final Integer id = chapter.getArea().getId();
			final Collection<Parade> parades = this.paradeService.findParadesByArea(id);
			final Double a = (double) parades.size();
			if (a >= mediaMas10)
				result.add(chapter);

		}

		return result;

	}
	//ORDEN DE LA LISTA MEDIA, MIN, MAX, DESV
	public List<Double> statsParadesChapters() {
		Double min = -1.0;
		Double max = 0.0;
		Double acum = 0.0;
		Double media = 0.0;
		Double desv = 0.0;
		final Collection<Chapter> chapters = this.chapterService.findAll();
		for (final Chapter chapter : chapters) {
			final Integer id = chapter.getArea().getId();
			final Collection<Parade> parades = this.paradeService.findParadesByArea(id);
			final Double a = (double) parades.size();
			acum = acum + a;
			if (a < min || min == -1)
				min = a;
			if (a > max)
				max = a;
		}
		media = acum / chapters.size();
		for (final Chapter chapter : chapters) {
			final Integer id = chapter.getArea().getId();
			final Collection<Parade> parades = this.paradeService.findParadesByArea(id);
			Double a = (double) parades.size();
			a = (a - media) * (a - media);
			desv = desv + a;

		}
		desv = desv / chapters.size();
		desv = Math.sqrt(desv);
		final List<Double> result = new ArrayList<>();
		result.add(media);
		result.add(min);
		result.add(max);
		result.add(desv);
		return result;

	}
	public String modeStat() {
		Double draftMode = 0.0;
		Double finMode = 0.0;
		final Collection<Parade> parades = this.paradeService.findAll();
		for (final Parade parade : parades)
			if (parade.getMode().equals("DRAFT"))
				draftMode++;
			else
				finMode++;
		draftMode = draftMode / parades.size();
		finMode = finMode / parades.size();
		final String result = "Draft parade: " + draftMode + " Final parade: " + finMode;
		return result;
	}

	public String statusStat() {
		Double sub = 0.0;
		Double acc = 0.0;
		Double rej = 0.0;

		final Collection<Parade> parades = this.paradeService.findAll();
		for (final Parade parade : parades) {
			if (parade.getStatus().equals("SUBMITTED"))
				sub++;
			if (parade.getStatus().equals("ACCEPTED"))
				acc++;
			if (parade.getStatus().equals("REJECTED"))
				rej++;
		}
		sub = sub / parades.size();
		acc = acc / parades.size();
		rej = rej / parades.size();

		final String result = "Submitted parades: " + sub + "Accepted parades: " + acc + "Rejected parades: " + rej;
		return result;
	}
}
