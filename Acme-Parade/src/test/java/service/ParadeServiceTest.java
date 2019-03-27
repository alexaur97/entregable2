
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

import services.ParadeService;
import utilities.AbstractTest;
import domain.Parade;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ParadeServiceTest extends AbstractTest {

	@Autowired
	private ParadeService	paradeService;


	//SOLO COGER UN CHAPTER CON AREA UNA PARADA SUYA SUBMITTED Y FINAL
	@Test
	public void testAcceptParadeGood() {
		super.authenticate("chapter2");
		final int paradeId = super.getEntityId("parade3");
		final Parade parade = this.paradeService.acceptParadeChanges(paradeId);
		this.paradeService.saveChapter(parade);
	}

	//IGUAL QUE EL ANTERIOR PERO COGIENDO UNA PARADE DE UN AREA QUE NO PERTENECE EL CHAPTER
	@Test(expected = IllegalArgumentException.class)
	public void testAcceptParadeError() {
		super.authenticate("chapter3");
		final int paradeId = super.getEntityId("parade3");
		final Parade parade = this.paradeService.acceptParadeChanges(paradeId);
		this.paradeService.saveChapter(parade);
	}
	@Test
	public void testRejectParadeGood() {
		super.authenticate("chapter2");
		final int paradeId = super.getEntityId("parade3");
		this.paradeService.restriccionesRejectGet(paradeId);
		final Parade parade = this.paradeService.findOne(paradeId);
		parade.setExplanation("Explicacion");
		final Parade paradeFinal = this.paradeService.rejectRecostruction(parade, null);
		this.paradeService.saveChapter(paradeFinal);
	}
	@Test(expected = IllegalArgumentException.class)
	public void testRejectParadeError() {
		super.authenticate("chapter2");
		final int paradeId = super.getEntityId("parade3");
		this.paradeService.restriccionesRejectGet(paradeId);
		final Parade parade = this.paradeService.findOne(paradeId);
		final Parade paradeFinal = this.paradeService.rejectRecostruction(parade, null);
		this.paradeService.saveChapter(paradeFinal);
	}

	@Test
	public void testStatsParadesGood() {
		super.authenticate("admin");
		final Collection<Double> modeVs = this.paradeService.modeStat();
		final Collection<Double> statusStats = this.paradeService.statusStat();
		//		final List<Double> modeVsList = new ArrayList<Double>(modeVs);
		//		final List<Double> statusStatsList = new ArrayList<Double>(statusStats);

		//		final Double modeVs1 = modeVsList.get(0);
		//		final Double modeVs2 = modeVsList.get(1);
		//		Assert.isTrue(modeVs1 == a);
		//		Assert.isTrue(modeVs2 == b);

	}
	@Test(expected = IllegalArgumentException.class)
	public void testStatsParadesError() {
		super.authenticate(null);
		final Collection<Double> modeVs = this.paradeService.modeStat();
		final Collection<Double> statusStats = this.paradeService.statusStat();
		final List<Double> modeVsList = new ArrayList<>(modeVs);
		final List<Double> statusStatsList = new ArrayList<>(statusStats);

		final Double modeVs1 = modeVsList.get(0);
		final Double modeVs2 = modeVsList.get(1);

		final Double status1 = statusStatsList.get(0);
		final Double status2 = statusStatsList.get(1);
		final Double status3 = statusStatsList.get(2);

		//		Assert.isTrue(modeVs1 == a);
		//		Assert.isTrue(modeVs2 == b);

	}
}
