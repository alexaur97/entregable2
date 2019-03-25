
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

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
	private Validator			validator;


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
		final Path path = this.pathService.findPathBySegment(segment);
		Collection<Segment> segments;
		segments = path.getSegments();
		segments.remove(segment);
		path.setSegments(segments);
		this.pathService.save(path);
		this.segmentRepository.delete(segment.getId());
	}

	// FR 3.3 ACME PARADE
	public Segment save(final Segment segment) {
		final Authority auth = new Authority();
		auth.setAuthority(Authority.BROTHERHOOD);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(auth));
		return this.segmentRepository.save(segment);
	}

	public Segment reconstruct(final Segment segment, final BindingResult binding) {
		final Segment res = segment;
		this.validator.validate(res, binding);
		return res;
	}

	public Boolean validateOrigin(final Segment segment, final Path path) {
		Boolean b = true;
		if (path.getSegments().size() > 1)
			for (final Segment s : path.getSegments())
				if (s.getSequence() == segment.getSequence() - 1 && segment.getSequence() > 1)
					if (!(s.getDestinationX() == segment.getOriginX() && s.getDestinationY() == segment.getOriginY())) {
						b = false;
						break;
					}
		return b;
	}

}
