
package service;

import java.util.Collection;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import services.ChapterService;
import utilities.AbstractTest;
import domain.Chapter;
import forms.ChapterRegisterForm;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class ChapterServiceTest extends AbstractTest {

	@Autowired
	private ChapterService	chapterService;


	// Valid Case FR 7.1 ACME PARADE
	// 
	@Test
	public void testStatsCreate() {
		super.authenticate(null);
		final ChapterRegisterForm registerForm = new ChapterRegisterForm();
		registerForm.setName("Test Name");
		registerForm.setSurName("Test Surname");
		registerForm.setPhoto("https://google.com/");
		registerForm.setEmail("test@gmail.com");
		registerForm.setPhone("666666666");
		registerForm.setUsername("Testing");
		registerForm.setPassword("Testing");
		registerForm.setConfirmPassword("Testing");
		registerForm.setTerms(true);
		registerForm.setTitle("Test Title");
		final Chapter chapter = this.chapterService.reconstruct(registerForm);
		this.chapterService.save(chapter);
		final Collection<Chapter> result = this.chapterService.findAll();
		Boolean bol = false;
		for (final Chapter c : result)
			if (c.getName().equals(chapter.getName()))
				bol = true;
		Assert.isTrue(bol);
		super.unauthenticate();
	}
	// Invalid Case FR 7.1 ACME PARADE - Name can´t be null
	@Test(expected = ConstraintViolationException.class)
	public void testStatsCreateNegative() {
		super.authenticate(null);
		final ChapterRegisterForm registerForm = new ChapterRegisterForm();
		registerForm.setSurName("Test Surname");
		registerForm.setPhoto("https://google.com/");
		registerForm.setEmail("test@gmail.com");
		registerForm.setPhone("666666666");
		registerForm.setUsername("Testing");
		registerForm.setPassword("Testing");
		registerForm.setConfirmPassword("Testing");
		registerForm.setTerms(true);
		registerForm.setTitle("Test Title");
		final Chapter chapter = this.chapterService.reconstruct(registerForm);
		this.chapterService.save(chapter);
		final Collection<Chapter> result = this.chapterService.findAll();
		Boolean bol = false;
		for (final Chapter c : result)
			if (c.getName().equals(chapter.getName()))
				bol = true;
		Assert.isTrue(bol);
		super.unauthenticate();
	}
}
