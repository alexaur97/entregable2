
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ChapterRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Chapter;
import forms.ChapterRegisterForm;

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

	public Chapter create() {
		final Chapter c = new Chapter();
		final UserAccount ua = new UserAccount();
		c.setUserAccount(ua);
		final Authority a = new Authority();
		a.setAuthority(Authority.CHAPTER);
		final Collection<Authority> authorities = new ArrayList<Authority>();
		authorities.add(a);
		c.getUserAccount().setAuthorities(authorities);
		return c;
	}

	public Chapter save(final Chapter c) {
		Assert.notNull(c);
		return this.chapterRepository.save(c);
	}

	public Chapter reconstruct(final ChapterRegisterForm r) {
		Assert.isTrue(r.getPassword().equals(r.getConfirmPassword()));
		final Chapter result = this.create();
		final UserAccount userAccount = result.getUserAccount();

		final Md5PasswordEncoder pe = new Md5PasswordEncoder();
		final String password = pe.encodePassword(r.getPassword(), null);

		userAccount.setUsername(r.getUsername());
		userAccount.setPassword(password);

		result.setUserAccount(userAccount);

		result.setName(r.getName());
		result.setMiddleName(r.getMiddleName());
		result.setSurname(r.getSurName());
		result.setPhoto(r.getPhoto());
		result.setEmail(r.getEmail());
		result.setPhoneNumber(r.getPhone());
		result.setAddress(r.getAddress());

		result.setTitle(r.getTitle());

		return result;
	}

}
