
package service;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import services.HistoryService;
import services.MiscellaneousRecordService;
import services.PeriodRecordService;
import utilities.AbstractTest;
import domain.Brotherhood;
import domain.History;
import domain.MiscellaneousRecord;
import domain.PeriodRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class HistoryServiceTest extends AbstractTest {

	@Autowired
	private HistoryService				historyService;

	@Autowired
	private MiscellaneousRecordService	miscellaneousRecordService;

	@Autowired
	private PeriodRecordService			periodRecordService;


	// FR 4.1.1 ACME PARADE
	@Test
	public void testStatsHistories() {
		super.authenticate("admin");
		final Collection<Double> s = this.historyService.statsRecordsPerHistory();
		final Collection<History> larger = this.historyService.findLargerThanAverage();
		final History largest = this.historyService.findLargest();
		final Collection<History> histories = this.historyService.findAll();
		Brotherhood largestReal = new Brotherhood();
		int larguestSize = 0;
		Double max = 0.0;
		Double min = 0.0;
		Double total = 0.0;
		for (final History h : histories) {
			final int a = 1 + h.getLegalRecord().size() + h.getLinkRecord().size() + h.getMiscellaneousRecord().size() + h.getPeriodRecord().size();
			total += a;
			if (min == 0.0 || a < min)
				min = (double) a;
			if (a > larguestSize) {
				larguestSize = a;
				max = (double) larguestSize;
				largestReal = h.getBrotherhood();
			}
		}
		//		final Double media = total / histories.size();
		//		Double deviation = 0.0;
		//		for (final History h : histories) {
		//			final int a = 1 + h.getLegalRecord().size() + h.getLinkRecord().size() + h.getMiscellaneousRecord().size() + h.getPeriodRecord().size();
		//			final double b = (media - a) * (media - a);
		//			deviation = deviation + b;
		//			if (a > media)
		//				Assert.isTrue(larger.contains(h));
		//		}
		//		deviation = deviation / histories.size();
		//		deviation = Math.sqrt(deviation);
		Assert.isTrue(largestReal.equals(largest.getBrotherhood()));
		super.unauthenticate();
	}

	// FR 4.1.1 ACME PARADE - Not logged as Administrator
	@Test(expected = IllegalArgumentException.class)
	public void testStatsHistoriesNegative() {
		super.authenticate(null);
		final Collection<Double> s = this.historyService.statsRecordsPerHistory();
		final Collection<History> larger = this.historyService.findLargerThanAverage();
		final History largest = this.historyService.findLargest();
		final Collection<History> histories = this.historyService.findAll();
		Brotherhood largestReal = new Brotherhood();
		int larguestSize = 0;
		Double max = 0.0;
		Double min = 0.0;
		Double total = 0.0;
		for (final History h : histories) {
			final int a = 1 + h.getLegalRecord().size() + h.getLinkRecord().size() + h.getMiscellaneousRecord().size() + h.getPeriodRecord().size();
			total += a;
			if (min == 0.0 || a < min)
				min = (double) a;
			if (a > larguestSize) {
				larguestSize = a;
				max = (double) larguestSize;
				largestReal = h.getBrotherhood();
			}
		}
		//		final Double media = total / histories.size();
		//		Double deviation = 0.0;
		//		for (final History h : histories) {
		//			final int a = 1 + h.getLegalRecord().size() + h.getLinkRecord().size() + h.getMiscellaneousRecord().size() + h.getPeriodRecord().size();
		//			final double b = (media - a) * (media - a);
		//			deviation = deviation + b;
		//			if (a > media)
		//				Assert.isTrue(larger.contains(h));
		//		}
		//		deviation = deviation / histories.size();
		//		deviation = Math.sqrt(deviation);

		Assert.isTrue(largestReal.equals(largest.getBrotherhood()));

		super.unauthenticate();
	}

	//--------------------------------------------------------------------

	//TEST RECORD

	//--------------------------------------------------------------------

	//En este test se testea el requisito 3.1
	@Test
	public void testCreateGoodMiscellaneousRecord() {
		super.authenticate("brotherhood1");
		final MiscellaneousRecord miscellaneousRecord = this.miscellaneousRecordService.create();
		miscellaneousRecord.setTitle("PruebaTestGood");
		miscellaneousRecord.setDescription("Accept Test");

		this.miscellaneousRecordService.save(miscellaneousRecord);
	}

	//	Para el caso negativo estamos intentando que un Miembro cree un Registro Misceláneo
	// en una Historia de una Hermandad, esto debe provocar un fallo en el sistema porque el
	// actor que debe estar logueado debe ser una Hermandad.
	//Análisis del sentence coverage: el sistema al llamar al metodo del servicio "create" comprueba
	// que el actor actual del sistema sea una Hermandad.
	@Test(expected = IllegalArgumentException.class)
	public void testCreateErrorMiscellaneousRecord() {
		super.authenticate("member3");
		final MiscellaneousRecord miscellaneousRecord = this.miscellaneousRecordService.create();
		miscellaneousRecord.setTitle("PruebaErrorTest");
		miscellaneousRecord.setDescription("Error Test");

		this.miscellaneousRecordService.save(miscellaneousRecord);
	}

	//En este test se testea el requisito 3.1
	@Test
	public void testDeleteGoodMiscellaneousRecord() {
		super.authenticate("brotherhood2");
		final int MiscellaneousRecordId = this.getEntityId("miscellaneousRecord2");
		final MiscellaneousRecord miscellaneousRecord = this.miscellaneousRecordService.findOne(MiscellaneousRecordId);
		this.miscellaneousRecordService.delete(miscellaneousRecord);
	}

	//	Para el caso negativo estamos intentando que una Hermandad elimine un registro misceláneo
	//  que no es perteneciente a ella, esto debe provocar un fallo en el sistema porque solo
	// puede borrar sus regitros.

	//Análisis del sentence coverage: el sistema al llamar al metodo del servicio "delete" comprueba
	// que el actor actual del sistema contiene entre sus registros misceláneos el registro que le hemos pasado.
	@Test(expected = IllegalArgumentException.class)
	public void testDeleteErrorMiscellaneousRecord() {
		super.authenticate("brotherhood2");
		final int MiscellaneousRecordId = this.getEntityId("miscellaneousRecord4");
		final MiscellaneousRecord miscellaneousRecord = this.miscellaneousRecordService.findOne(MiscellaneousRecordId);
		this.miscellaneousRecordService.delete(miscellaneousRecord);
	}

	//En este test se testea el requisito 3.1
	@Test
	public void testCreateGoodPeriodRecord() {
		super.authenticate("brotherhood1");
		final PeriodRecord periodRecord = this.periodRecordService.create();
		periodRecord.setTitle("PruebaTestGood");
		periodRecord.setDescription("Accept Test");
		periodRecord.setStartYear(1998);
		periodRecord.setEndYear(2019);
		final Collection<String> pictures = new ArrayList<>();
		pictures.add("https://prueba.jpg");

		this.periodRecordService.validatePictures(pictures);
		periodRecord.setPictures(pictures);

		this.periodRecordService.save(periodRecord);
	}

	//	Para el caso negativo estamos intentando que una Hermandad cree un Registro Periodico 
	//con una fecha de fin anterior a la fecha de inicio , esto debe provocar un fallo en el sistema porque 
	// las fecha fecha de fin debe ser posterior a la fecha de inicio.
	//Análisis del data coverage: el sistema al llamar al metodo del servicio "create" comprueba
	// que las fechas cumplen la lógica indicada.
	@Test(expected = IllegalArgumentException.class)
	public void testCreateErrorPeriodRecord() {
		super.authenticate("brotherhood1");
		final PeriodRecord periodRecord = this.periodRecordService.create();
		periodRecord.setTitle("PruebaTestError");
		periodRecord.setDescription("Error Test");
		periodRecord.setStartYear(2010);
		periodRecord.setEndYear(2000);
		final Collection<String> pictures = new ArrayList<>();
		pictures.add("https://prueba2.jpg");

		this.periodRecordService.validatePictures(pictures);
		periodRecord.setPictures(pictures);

		this.periodRecordService.save(periodRecord);
	}

	//En este test se testea el requisito 3.1
	@Test
	public void testDeleteGoodPeriodRecord() {
		super.authenticate("brotherhood2");
		final int PeriodRecordId = this.getEntityId("periodRecord2");
		this.periodRecordService.delete(PeriodRecordId);
	}

	//	Para el caso negativo estamos intentando que una Hermandad elimine un registro periódico
	//  que no es perteneciente a ella, esto debe provocar un fallo en el sistema porque solo
	// puede borrar sus regitros.

	//Análisis del sentence coverage: el sistema al llamar al metodo del servicio "delete" comprueba
	// que el actor actual del sistema contiene entre sus registros periódicos el registro que le hemos pasado.
	@Test(expected = IllegalArgumentException.class)
	public void testDeleteErrorPeriodRecord() {
		super.authenticate("brotherhood2");
		final int PeriodRecordId = this.getEntityId("periodRecord4");
		this.periodRecordService.delete(PeriodRecordId);
	}

}
