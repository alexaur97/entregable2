
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PathRepository;
import security.Authority;
import security.LoginService;
import domain.Brotherhood;
import domain.Parade;
import domain.Path;
import domain.Segment;

@Service
@Transactional
public class PathService {

	@Autowired
	private PathRepository		pathRepository;

	@Autowired
	private ParadeService		paradeService;

	@Autowired
	private BrotherhoodService	brotherhoodService;


	// FR 3.3 ACME PARADE
	public Collection<Path> findPathsByParade(final int paradeId) {
		Assert.isTrue(this.checkBrotherhood(paradeId));
		Collection<Path> result;
		result = this.pathRepository.findPathsByParade(paradeId);
		return result;
	}

	// FR 3.3 ACME PARADE
	public Path findOne(final int pathId) {
		final Path result = this.pathRepository.findOne(pathId);
		Assert.isTrue(this.checkBrotherhoodPath(result));
		return result;
	}

	// FR 3.3 ACME PARADE
	public Path create() {
		final Authority auth = new Authority();
		auth.setAuthority(Authority.BROTHERHOOD);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(auth));
		return new Path();
	}

	// FR 3.3 ACME PARADE
	public void delete(final Path path) {
		Assert.isTrue(this.checkBrotherhoodPath(path));
		final Parade parade = this.paradeService.findParadeByPath(path);
		Collection<Path> paths;
		paths = parade.getPaths();
		paths.remove(path);
		parade.setPaths(paths);
		this.paradeService.save(parade);
		this.pathRepository.delete(path.getId());
	}
	// FR 3.3 ACME PARADE
	public Path save(final Path path) {
		final Authority auth = new Authority();
		auth.setAuthority(Authority.BROTHERHOOD);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(auth));
		final Path result;
		result = this.pathRepository.save(path);
		return result;
	}

	// FR 3.3 ACME PARADE
	private Boolean checkBrotherhood(final int paradeId) {
		Boolean result = false;
		final Brotherhood paradeBrotherhood = this.paradeService.findOne(paradeId).getBrotherhood();
		result = LoginService.getPrincipal().getId() == paradeBrotherhood.getUserAccount().getId();
		return result;
	}

	// FR 3.3 ACME PARADE
	public Boolean checkBrotherhoodPath(final Path path) {
		Boolean result = false;
		final Collection<Parade> parades = this.paradeService.findByPrincipal();
		for (final Parade p : parades)
			if (p.getPaths().contains(path))
				result = true;
		return result;
	}

	// FR 3.3
	public Path addSegment(final Path path, final Segment segment) {
		Path result;
		final Collection<Segment> segments = path.getSegments();
		segments.add(segment);
		path.setSegments(segments);
		result = this.save(path);
		return result;
	}

	public Path findPathBySegment(final Segment segment) {
		final Collection<Parade> parades = this.paradeService.findByPrincipal();
		Path result = null;
		for (final Parade p : parades)
			for (final Path pa : p.getPaths())
				if (pa.getSegments().contains(segment))
					result = pa;
		Assert.notNull(result);
		return result;
	}

	// FR 3.3 ACME PARADE
	public Path findOneBySegment(final int pathId) {
		final Path result = this.pathRepository.findOne(pathId);
		return result;
	}
}
