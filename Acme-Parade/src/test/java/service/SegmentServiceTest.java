
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
import services.SegmentService;
import utilities.AbstractTest;
import domain.Path;
import domain.Segment;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class SegmentServiceTest extends AbstractTest {

	@Autowired
	private PathService		pathService;

	@Autowired
	private ParadeService	paradeService;

	@Autowired
	private SegmentService	segmentService;


	// Valid Case FR 3.3 ACME PARADE / EDIT SEGMENT
	@Test
	public void testSegmentEdit() {
		super.authenticate("brotherhood1");

		// SEGMENT ID
		final int id = 112;

		final Segment segment = this.segmentService.findOne(id);
		final Path path = this.pathService.findPathBySegment(segment);
		Assert.isTrue(this.pathService.checkBrotherhoodPath(path));
		segment.setOriginX(30);
		this.segmentService.save(segment);
		final Segment result = this.segmentService.findOne(id);
		Assert.isTrue(result.getOriginX() == 30);
		super.unauthenticate();
	}

	// Invalid Case FR 3.3 ACME PARADE EDIT SEGMENT - Segment from a Path from a Parade from other Brotherhood
	@Test(expected = IllegalArgumentException.class)
	public void testSegmentEditNegative() {
		super.authenticate(null);

		// SEGMENT ID
		final int id = 113;

		final Segment segment = this.segmentService.findOne(id);
		final Path path = this.pathService.findPathBySegment(segment);
		Assert.isTrue(this.pathService.checkBrotherhoodPath(path));
		segment.setOriginX(30);
		this.segmentService.save(segment);
		final Segment result = this.segmentService.findOne(id);
		Assert.isTrue(result.getOriginX() == 30);
		super.unauthenticate();
	}

	// Valid Case FR 3.3 ACME PARADE / DELETE Segment
	@Test
	public void testSegmentDelete() {
		super.authenticate("brotherhood1");

		// SEGMENT ID
		final int id = 112;

		final Segment segment = this.segmentService.findOne(id);
		final Path path = this.pathService.findPathBySegment(segment);
		Assert.isTrue(this.pathService.checkBrotherhoodPath(path));
		this.segmentService.delete(segment);
		final Collection<Segment> result = this.segmentService.findSegmentsByPath(path.getId());
		Assert.isTrue(!result.contains(segment));
		super.unauthenticate();
	}

	// Invalid Case FR 3.3 ACME PARADE / DELETE Segment - Segment from a Path from a Parade from other Brotherhood
	@Test(expected = IllegalArgumentException.class)
	public void testSegmentDeleteNegative() {
		super.authenticate("brotherhood2");

		// SEGMENT ID
		final int id = 112;

		final Segment segment = this.segmentService.findOne(id);
		final Path path = this.pathService.findPathBySegment(segment);
		Assert.isTrue(this.pathService.checkBrotherhoodPath(path));
		this.segmentService.delete(segment);
		final Collection<Segment> result = this.segmentService.findSegmentsByPath(path.getId());
		Assert.isTrue(!result.contains(segment));
		super.unauthenticate();
	}
}
