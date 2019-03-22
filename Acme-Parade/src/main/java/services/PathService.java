
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


	// FR 3.3
	public Collection<Path> findPathsByParade(final int paradeId) {
		Assert.isTrue(this.checkBrotherhood(paradeId));
		Collection<Path> result;
		result = this.pathRepository.findPathsByParade(paradeId);
		return result;
	}

	// FR 3.3
	public Path findOne(final int pathId) {
		final Path result = this.pathRepository.findOne(pathId);
		Assert.isTrue(this.checkBrotherhoodPath(result));
		return result;
	}

	// FR 3.3
	public Path create() {
		final Authority auth = new Authority();
		auth.setAuthority(Authority.BROTHERHOOD);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(auth));
		return new Path();
	}

	// FR 3.3

	public void delete(final Path path) {
		Assert.isTrue(this.checkBrotherhoodPath(path));
		this.pathRepository.delete(path.getId());
	}

	// FR 3.3
	public Path save(final Path path) {
		final Authority auth = new Authority();
		auth.setAuthority(Authority.BROTHERHOOD);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(auth));
		final Path result;
		result = this.pathRepository.save(path);
		return result;

	}

	private Boolean checkBrotherhood(final int paradeId) {
		Boolean result = false;
		final Brotherhood paradeBrotherhood = this.paradeService.findOne(paradeId).getBrotherhood();
		result = LoginService.getPrincipal().getId() == paradeBrotherhood.getUserAccount().getId();
		return result;
	}

	private Boolean checkBrotherhoodPath(final Path path) {
		Boolean result = false;
		final Brotherhood principal = this.brotherhoodService.findByPrincipal();
		final Collection<Parade> parades = this.paradeService.findByPrincipal();
		for (final Parade p : parades)
			if (p.getPaths().contains(path) && p.getBrotherhood().getId() == principal.getId())
				result = true;
		return result;
	}
}
