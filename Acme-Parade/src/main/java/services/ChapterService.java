
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ChapterRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Chapter;

@Service
@Transactional
public class ChapterService {

	@Autowired
	private ChapterRepository	chapterRepository;
	@Autowired
	private ActorService		actorService;


	public Collection<Chapter> findAll() {
		Collection<Chapter> result;
		result = this.chapterRepository.findAll();
		return result;
	}
	public Chapter findOne(final int id) {
		Assert.isTrue(id != 0);
		Chapter result;
		result = this.chapterRepository.findOne(id);
		return result;
	}
	public Chapter findByUserId(final int id) {
		final Chapter c = this.chapterRepository.findByUserId(id);
		return c;
	}
	public Chapter findByPrincipal() {
		final UserAccount user = LoginService.getPrincipal();
		Assert.notNull(user);

		final Chapter c = this.findByUserId(user.getId());
		Assert.notNull(c);
		this.actorService.auth(c, Authority.CHAPTER);
		return c;
	}
}
