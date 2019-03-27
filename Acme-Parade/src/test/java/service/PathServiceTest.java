
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


	// Valid Case FR 3.3 ACME PARADE / CREATE PATH
	@Test
	public void testPathCreate() {
		super.authenticate("brotherhood1");

		// Parade ID
		final int id = 117;

		final Path path = this.pathService.create();
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

	// Invalid Case FR 3.3 ACME PARADE / CREATE PATH - Not logged as brotherhood
	@Test(expected = IllegalArgumentException.class)
	public void testPathCreateNegative() {
		super.authenticate(null);

		// Parade ID
		final int id = 117;

		final Path path = this.pathService.create();
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

	// Valid Case FR 3.3 ACME PARADE / DELETE PATH
	@Test
	public void testPathDelete() {
		super.authenticate("brotherhood1");

		// PATH ID
		final int id = 118;

		// PARADE ID
		final int pid = 117;

		final Path path = this.pathService.findOne(id);
		Assert.isTrue(this.pathService.checkBrotherhoodPath(path));
		this.pathService.delete(path);
		final Collection<Path> result = this.pathService.findPathsByParade(pid);
		Assert.isTrue(!result.contains(path));
		super.unauthenticate();
	}

	// Invalid Case FR 3.3 ACME PARADE / DELETE PATH - Path from a Parade from other Brotherhood
	@Test(expected = IllegalArgumentException.class)
	public void testPathDeleteNegative() {
		super.authenticate("brotherhood2");

		// PATH ID
		final int id = 118;

		// PARADE ID
		final int pid = 117;

		final Path path = this.pathService.findOne(id);
		Assert.isTrue(this.pathService.checkBrotherhoodPath(path));
		this.pathService.delete(path);
		final Collection<Path> result = this.pathService.findPathsByParade(pid);
		Assert.isTrue(!result.contains(path));
		super.unauthenticate();
	}
}
