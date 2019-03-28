
package service;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.BrotherhoodService;
import services.HistoryService;
import services.LinkRecordService;
import utilities.AbstractTest;
import domain.Brotherhood;
import domain.History;
import domain.LinkRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class LinkRecordServiceTest extends AbstractTest {

	@Autowired
	private LinkRecordService	linkRecordService;
	@Autowired
	private HistoryService		historyService;
	@Autowired
	private BrotherhoodService	brotherhoodService;


	// Este test testea el requisito 3.1
	// Un actor autentificado como BROTHERHOOD crea un LinkRecord en su historia
	// Sin ningún problema

	@Test
	public void testCreateLinkRecord() {
		super.authenticate("brotherhood1");
		final LinkRecord linkRecord = this.linkRecordService.create();
		linkRecord.setTitle("title");
		linkRecord.setDescription("description");
		final Integer brotherhoodId = super.getEntityId("brotherhood2");
		final Brotherhood brotherhood = this.brotherhoodService.findOne(brotherhoodId);
		linkRecord.setBrotherhood(brotherhood);
		this.linkRecordService.save(linkRecord);
		super.unauthenticate();
	}

	// Este test testea el requisito 3.1
	// Un actor autentificado como BROTHERHOOD elimina un LinkRecord de su historia
	// Sin ningún problema

	@Test
	public void testDeleteLinkRecord() {
		super.authenticate("brotherhood1");
		final Integer historyId = super.getEntityId("history1");
		final History history = this.historyService.findOne(historyId);
		final LinkRecord linkRecord = (LinkRecord) history.getLinkRecord().toArray()[0];
		this.linkRecordService.delete(linkRecord);
		super.unauthenticate();
	}

	// Este test testea el requisito 3.1
	// Una BROTHERHOOD intenta asociarse con ella misma al crear un LinkRecord.
	// Se lanza una excepción.

	@Test(expected = IllegalArgumentException.class)
	public void testCreateLinkRecordWitgSameBrotherhood() {
		super.authenticate("brotherhood1");
		final LinkRecord linkRecord = this.linkRecordService.create();
		linkRecord.setTitle("title");
		linkRecord.setDescription("description");
		final Integer brotherhoodId = super.getEntityId("brotherhood1");
		final Brotherhood brotherhood = this.brotherhoodService.findOne(brotherhoodId);
		linkRecord.setBrotherhood(brotherhood);
		this.linkRecordService.save(linkRecord);
		super.unauthenticate();
	}

	// Este test testea el requisito 3.1
	// Un actor autentificado como BROTHERHOOD intenta eliminar un LinkRecord de una historia
	// que no es suya y se lanza una excepción.

	@Test(expected = IllegalArgumentException.class)
	public void testDeleteLinkRecordOtherBrotherhood() {
		super.authenticate("brotherhood2");
		final Integer historyId = super.getEntityId("history1");
		final History history = this.historyService.findOne(historyId);
		final LinkRecord linkRecord = (LinkRecord) history.getLinkRecord().toArray()[0];
		this.linkRecordService.delete(linkRecord);
		super.unauthenticate();
	}

	// Este test testea el requisito 3.1
	// Un actor que no está autentificado como BROTHERHOOD intenta crear un LinkRecord en una historia.
	// Se lanza una excepción.

	@Test(expected = IllegalArgumentException.class)
	public void testCreateLinkRecordNonBrotherhood() {
		super.authenticate("member1");
		final LinkRecord linkRecord = this.linkRecordService.create();
		linkRecord.setTitle("title");
		linkRecord.setDescription("description");
		final Integer brotherhoodId = super.getEntityId("brotherhood1");
		final Brotherhood brotherhood = this.brotherhoodService.findOne(brotherhoodId);
		linkRecord.setBrotherhood(brotherhood);
		this.linkRecordService.save(linkRecord);
		super.unauthenticate();
	}

	// Este test testea el requisito 3.1
	// Un actor que no está autentificado como BROTHERHOOD intenta eliminar un LinkRecord de una historia
	// y se lanza una excepción.

	@Test(expected = IllegalArgumentException.class)
	public void testDeleteLinkRecordNonBrotherhood() {
		super.authenticate("member1");
		final Integer historyId = super.getEntityId("history1");
		final History history = this.historyService.findOne(historyId);
		final LinkRecord linkRecord = (LinkRecord) history.getLinkRecord().toArray()[0];
		this.linkRecordService.delete(linkRecord);
		super.unauthenticate();
	}

}
