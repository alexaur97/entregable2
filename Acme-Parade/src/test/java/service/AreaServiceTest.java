
package service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import services.AreaService;
import services.ChapterService;
import utilities.AbstractTest;
import domain.Chapter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class AreaServiceTest extends AbstractTest {

	@Autowired
	private AreaService		areaService;
	@Autowired
	private ChapterService	chapterService;


	@Test(expected = IllegalArgumentException.class)
	public void testStatsAreaError() {
		super.authenticate(null);
		this.areaService.statsParadesChapters();
		this.areaService.chapters10();
		this.areaService.ratioAreaWithoutChapter();
		this.areaService.chapters10();
	}
	@Test
	public void testStatsAreaGood() {
		super.authenticate("admin");
		final Chapter a = this.chapterService.findOne(super.getEntityId("chapter2"));
		final Chapter b = this.chapterService.findOne(super.getEntityId("chapter3"));
		final Collection<Chapter> chaptersb = new ArrayList<>();
		chaptersb.add(a);
		chaptersb.add(b);
		final List<Double> stats = this.areaService.statsParadesChapters();
		final Double media = 0.67;
		final Double min = 0.00;
		final Double max = 1.00;
		final Double des = 0.47;
		final Double mediab = (double) Math.round(stats.get(0) * 100) / 100;
		final Double minb = (double) Math.round(stats.get(1) * 100) / 100;
		final Double maxb = (double) Math.round(stats.get(2) * 100) / 100;
		final Double desb = (double) Math.round(stats.get(3) * 100) / 100;
		Assert.isTrue(mediab.equals(media));
		Assert.isTrue(minb.equals(min));
		Assert.isTrue(maxb.equals(max));
		Assert.isTrue(desb.equals(des));
		final Double ratioAreaWithoutChapter = this.areaService.ratioAreaWithoutChapter();
		Assert.isTrue(ratioAreaWithoutChapter == 0.6);
		final Collection<Chapter> chapters = this.areaService.chapters10();
		Assert.isTrue(chapters.equals(chaptersb));

	}
}
