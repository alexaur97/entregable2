
package service;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.BrotherhoodService;
import services.HistoryService;
import services.LegalRecordService;
import utilities.AbstractTest;
import domain.History;
import domain.LegalRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class LegalRecordServiceTest extends AbstractTest {

	@Autowired
	private LegalRecordService	legalRecordService;

	@Autowired
	private BrotherhoodService	brotherhoodService;

	@Autowired
	private HistoryService		historyService;


	// Este test testea el requisito 3.1
	// Un actor autentificado como BROTHERHOOD edita un LegalRecord de su historia
	// Sin ningún problema

	@Test
	public void testEditLegalRecord() {
		super.authenticate("brotherhood1");
		final Integer historyId = super.getEntityId("history1");
		final History history = this.historyService.findOne(historyId);
		final LegalRecord legalRecord = (LegalRecord) history.getLegalRecord().toArray()[0];
		legalRecord.setTitle("title");
		legalRecord.setDescription("description");
		legalRecord.setLegalName("ln");
		legalRecord.setVatNumber(21.0);
		final Collection<String> laws = new ArrayList<>();
		laws.add("law");
		legalRecord.setLaws(laws);
		this.legalRecordService.save(legalRecord);
		super.unauthenticate();
	}

	// Este test testea el requisito 3.1
	// Un actor autentificado como BROTHERHOOD elimina un LegalRecord de su historia
	// Sin ningún problema

	@Test
	public void testDeleteLegalRecord() {
		super.authenticate("brotherhood1");
		final Integer historyId = super.getEntityId("history1");
		final History history = this.historyService.findOne(historyId);
		final LegalRecord legalRecord = (LegalRecord) history.getLegalRecord().toArray()[0];
		this.legalRecordService.delete(legalRecord.getId());
		super.unauthenticate();
	}

	// Este test testea el requisito 3.1
	// Un actor autentificado como BROTHERHOOD intenta editar un LegalRecord de una historia
	// que no es suya. Se lanza una excepción.

	@Test(expected = IllegalArgumentException.class)
	public void testEditLegalRecordOtherBrotherhood() {
		super.authenticate("brotherhood2");
		final Integer historyId = super.getEntityId("history1");
		final History history = this.historyService.findOne(historyId);
		final LegalRecord legalRecord = (LegalRecord) history.getLegalRecord().toArray()[0];
		legalRecord.setTitle("title");
		legalRecord.setDescription("description");
		legalRecord.setLegalName("ln");
		legalRecord.setVatNumber(21.0);
		final Collection<String> laws = new ArrayList<>();
		laws.add("law");
		legalRecord.setLaws(laws);
		this.legalRecordService.save(legalRecord);
		super.unauthenticate();
	}

	// Este test testea el requisito 3.1
	// Un actor autentificado como BROTHERHOOD intenta eliminar un LegalRecord de una historia
	// que no es suya y se lanza una excepción.

	@Test(expected = IllegalArgumentException.class)
	public void testDeleteLegalRecordOtherBrotherhood() {
		super.authenticate("brotherhood2");
		final Integer historyId = super.getEntityId("history1");
		final History history = this.historyService.findOne(historyId);
		final LegalRecord legalRecord = (LegalRecord) history.getLegalRecord().toArray()[0];
		this.legalRecordService.delete(legalRecord.getId());
		super.unauthenticate();
	}

	// Este test testea el requisito 3.1
	// Un actor que no está autentificado como BROTHERHOOD intenta editar un LegalRecord de una historia.
	// Se lanza una excepción.

	@Test(expected = IllegalArgumentException.class)
	public void testEditLegalRecordNonBrotherhood() {
		super.authenticate("member1");
		final Integer historyId = super.getEntityId("history1");
		final History history = this.historyService.findOne(historyId);
		final LegalRecord legalRecord = (LegalRecord) history.getLegalRecord().toArray()[0];
		legalRecord.setTitle("title");
		legalRecord.setDescription("description");
		legalRecord.setLegalName("ln");
		legalRecord.setVatNumber(21.0);
		final Collection<String> laws = new ArrayList<>();
		laws.add("law");
		legalRecord.setLaws(laws);
		this.legalRecordService.save(legalRecord);
		super.unauthenticate();
	}

	// Este test testea el requisito 3.1
	// Un actor que no está autentificado como BROTHERHOOD intenta eliminar un LegalRecord de una historia
	// y se lanza una excepción.

	@Test(expected = IllegalArgumentException.class)
	public void testDeleteLegalRecordNonBrotherhood() {
		super.authenticate("member1");
		final Integer historyId = super.getEntityId("history1");
		final History history = this.historyService.findOne(historyId);
		final LegalRecord legalRecord = (LegalRecord) history.getLegalRecord().toArray()[0];
		this.legalRecordService.delete(legalRecord.getId());
		super.unauthenticate();
	}

}
