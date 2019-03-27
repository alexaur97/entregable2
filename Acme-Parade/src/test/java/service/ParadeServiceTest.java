
package service;

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
		super.authenticate("chapter1");
		final int paradeId = super.getEntityId("parade3");
		final Parade parade = this.paradeService.acceptParadeChanges(paradeId);
		this.paradeService.save(parade);
	}

	//IGUAL QUE EL ANTERIOR PERO COGIENDO UNA PARADE DE UN AREA QUE NO PERTENECE EL CHAPTER
	@Test(expected = IllegalArgumentException.class)
	public void testAcceptParadeError() {
		super.authenticate("chapter1");
		final int paradeId = super.getEntityId("parade3");
		final Parade parade = this.paradeService.acceptParadeChanges(paradeId);
		this.paradeService.saveChapter(parade);
	}
	@Test
	public void testRejectParadeGood() {
		super.authenticate("chapter1");
		final int paradeId = super.getEntityId("parade3");
		this.paradeService.restriccionesRejectGet(paradeId);
		final Parade parade = this.paradeService.findOne(paradeId);
		parade.setExplanation("Explicacion");
		final Parade paradeFinal = this.paradeService.rejectRecostruction(parade, null);
		this.paradeService.saveChapter(paradeFinal);
	}
	@Test(expected = IllegalArgumentException.class)
	public void testRejectParadeError() {
		super.authenticate("chapter1");
		final int paradeId = super.getEntityId("parade3");
		this.paradeService.restriccionesRejectGet(paradeId);
		final Parade parade = this.paradeService.findOne(paradeId);
		final Parade paradeFinal = this.paradeService.rejectRecostruction(parade, null);
		this.paradeService.saveChapter(paradeFinal);
	}
}
