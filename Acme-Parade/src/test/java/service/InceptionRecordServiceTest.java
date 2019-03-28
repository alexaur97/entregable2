
package service;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.InceptionRecordService;
import utilities.AbstractTest;
import domain.InceptionRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class InceptionRecordServiceTest extends AbstractTest {

	@Autowired
	private InceptionRecordService	inceptionRecordService;


	@Test
	public void testCreateInceptionRecord() {
		super.authenticate("brotherhood1");
		final InceptionRecord inceptionRecord = this.inceptionRecordService.create();
		inceptionRecord.setDescription("description");
		inceptionRecord.setTitle("title");
		final Collection<String> pictures = new ArrayList<>();
		pictures.add("photo");
		inceptionRecord.setPictures(pictures);
		this.inceptionRecordService.save(inceptionRecord);
		super.unauthenticate();
	}
}
