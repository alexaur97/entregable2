
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SegmentRepository;
import security.Authority;
import security.LoginService;
import domain.Parade;
import domain.Path;
import domain.Segment;

@Service
@Transactional
public class SegmentService {

	@Autowired
	private SegmentRepository	segmentRepository;

	@Autowired
	private ParadeService		paradeService;

	@Autowired
	private PathService			pathService;

	@Autowired
	private BrotherhoodService	brotherhoodService;


	// FR 3.3 ACME PARADE
	public Collection<Segment> findSegmentsByPath(final int pathId) {
		Assert.isTrue(this.pathService.checkBrotherhoodPath(this.pathService.findOne(pathId)));
		Collection<Segment> result;
		result = this.segmentRepository.findSegmentsByPath(pathId);
		return result;
	}

	// FR 3.3 ACME PARADE
	public Segment findOne(final int segmentId) {
		final Segment result = this.segmentRepository.findOne(segmentId);
		Assert.isTrue(this.checkBrotherhoodSegment(result));
		return result;
	}

	// FR 3.3 ACME PARADE
	private Boolean checkBrotherhoodSegment(final Segment segment) {
		Boolean result = false;
		final Collection<Parade> parades = this.paradeService.findByPrincipal();
		for (final Parade p : parades)
			for (final Path pa : this.pathService.findPathsByParade(p.getId()))
				if (pa.getSegments().contains(segment))
					result = true;
		return result;
	}

	// FR 3.3 ACME PARADE
	public Segment create() {
		final Authority auth = new Authority();
		auth.setAuthority(Authority.BROTHERHOOD);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(auth));
		return new Segment();
	}

	// FR 3.3 ACME PARADE
	public void delete(final Segment segment) {
		Assert.isTrue(this.checkBrotherhoodSegment(segment));
		this.segmentRepository.delete(segment.getId());
	}

	// FR 3.3 ACME PARADE
	public Segment save(final Segment segment, final Path path, final Parade parade) {
		final Authority auth = new Authority();
		auth.setAuthority(Authority.BROTHERHOOD);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(auth));
		final Segment result;
		path.getSegments().add(segment);
		this.pathService.save(path, parade);
		result = this.segmentRepository.save(segment);
		return result;
	}
}
