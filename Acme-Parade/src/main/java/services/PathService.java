
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
		this.pathRepository.delete(path.getId());
	}

	// FR 3.3 ACME PARADE
	public Path save(final Path path, final Parade parade) {
		final Authority auth = new Authority();
		auth.setAuthority(Authority.BROTHERHOOD);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(auth));
		final Path result;
		parade.getPaths().add(path);
		this.paradeService.save(parade);
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
}
