
package service;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import services.ParadeService;
import services.PathService;
import utilities.AbstractTest;
import domain.Parade;
import domain.Path;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class PathServiceTest extends AbstractTest {

	@Autowired
	private PathService		pathService;

	@Autowired
	private ParadeService	paradeService;


	// Valid Case FR 3.3 ACME PARADE CREATE
	@Test
	public void testPathCreate() {
		super.authenticate("brotherhood1");
		final Path path = this.pathService.create();
		final int id = 117;
		final Parade parade = this.paradeService.findOne(id);
		final Path pa = this.pathService.save(path);
		final Parade par = this.paradeService.addPath(parade, pa);
		final Collection<Path> result = this.pathService.findPathsByParade(par.getId());
		Boolean bol = false;
		for (final Path p : result)
			if (p.getId() == pa.getId())
				bol = true;
		Assert.isTrue(bol);
		super.unauthenticate();
	}

	// Invalid Case FR 3.3 ACME PARADE CREATE - Not logged as brotherhood
	@Test(expected = IllegalArgumentException.class)
	public void testPathCreateNegative() {
		super.authenticate(null);
		final Path path = this.pathService.create();
		final int id = 117;
		final Parade parade = this.paradeService.findOne(id);
		final Path pa = this.pathService.save(path);
		final Parade par = this.paradeService.addPath(parade, pa);
		final Collection<Path> result = this.pathService.findPathsByParade(par.getId());
		Boolean bol = false;
		for (final Path p : result)
			if (p.getId() == pa.getId())
				bol = true;
		Assert.isTrue(bol);
		super.unauthenticate();
	}

	// Valid Case FR 3.3 ACME PARADE DELETE
	@Test
	public void testPathDelete() {
		super.authenticate("brotherhood1");
		final int id = 118;
		final Path path = this.pathService.findOne(id);
		final int pid = 117;
		this.pathService.delete(path);
		final Collection<Path> result = this.pathService.findPathsByParade(pid);
		Boolean bol = false;
		for (final Path p : result)
			if (p.getId() == id)
				bol = true;
		Assert.isTrue(bol);
		super.unauthenticate();
	}
}
