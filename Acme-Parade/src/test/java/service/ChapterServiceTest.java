
package service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import services.ChapterService;
import utilities.AbstractTest;
import domain.Area;
import domain.Chapter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ChapterServiceTest extends AbstractTest {

	@Autowired
	private ChapterService	chapterService;


	// Valid Case FR 7.1 ACME PARADE

	//	@Test
	//	public void testChapterCreate() {
	//		super.authenticate(null);
	//		final ChapterRegisterForm registerForm = new ChapterRegisterForm();
	//		registerForm.setName("Test Name");
	//		registerForm.setSurName("Test Surname");
	//		registerForm.setPhoto("https://google.com/");
	//		registerForm.setEmail("test@gmail.com");
	//		registerForm.setPhone("666666666");
	//		registerForm.setUsername("Testing");
	//		registerForm.setPassword("Testing");
	//		registerForm.setConfirmPassword("Testing");
	//		registerForm.setTerms(true);
	//		registerForm.setTitle("Test Title");
	//		final Chapter chapter = this.chapterService.reconstruct(registerForm);
	//		this.chapterService.save(chapter);
	//		final Collection<Chapter> result = this.chapterService.findAll();
	//		Boolean bol = false;
	//		for (final Chapter c : result)
	//			if (c.getName().equals(chapter.getName()))
	//				bol = true;
	//		Assert.isTrue(bol);
	//		super.unauthenticate();
	//	}
	//	// Invalid Case FR 7.1 ACME PARADE - Name can´t be null
	//	@Test(expected = ConstraintViolationException.class)
	//	public void testChapterCreateNegative() {
	//		super.authenticate(null);
	//		final ChapterRegisterForm registerForm = new ChapterRegisterForm();
	//		registerForm.setSurName("Test Surname");
	//		registerForm.setPhoto("https://google.com/");
	//		registerForm.setEmail("test@gmail.com");
	//		registerForm.setPhone("666666666");
	//		registerForm.setUsername("Testing");
	//		registerForm.setPassword("Testing");
	//		registerForm.setConfirmPassword("Testing");
	//		registerForm.setTerms(true);
	//		registerForm.setTitle("Test Title");
	//		final Chapter chapter = this.chapterService.reconstruct(registerForm);
	//		this.chapterService.save(chapter);
	//		final Collection<Chapter> result = this.chapterService.findAll();
	//		Boolean bol = false;
	//		for (final Chapter c : result)
	//			if (c.getName().equals(chapter.getName()))
	//				bol = true;
	//		Assert.isTrue(bol);
	//		super.unauthenticate();
	//	}

	// Test para el requisito funcional 2.1

	@Test
	public void testAssignAreaGood() {
		super.authenticate("chapter1");
		final int chapterId = super.getEntityId("chapter1");
		this.chapterService.restriccionesAssignArea(chapterId);
		final Chapter chapter = this.chapterService.findOne(chapterId);
		final Area area = chapter.getArea();
		chapter.setArea(area);
		final Chapter chapterFinal = this.chapterService.reconstructAssign(chapter, null);
		this.chapterService.save(chapterFinal);

	}

	// Este test es para el requisito funcional 2.1 donde un chapter se auto asigna un area para coordinar. 
	// En este caso rompemos la regla de negocio forzando a que no sea el chapter el que intente
	// asignarse un area, violando la seguridad. 

	@Test(expected = IllegalArgumentException.class)
	public void testAssignAreaError() {
		super.authenticate("brotherhood1");
		final int chapterId = super.getEntityId("chapter1");
		this.chapterService.restriccionesAssignArea(chapterId);
		final Chapter chapter = this.chapterService.findOne(chapterId);
		final Area area = chapter.getArea();
		chapter.setArea(area);
		final Chapter chapterFinal = this.chapterService.reconstructAssign(chapter, null);
		this.chapterService.save(chapterFinal);

	}

}
