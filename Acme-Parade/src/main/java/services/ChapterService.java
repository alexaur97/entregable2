
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ChapterRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Chapter;
import forms.ActorEditForm;
import forms.ChapterRegisterForm;

@Service
@Transactional
public class ChapterService {

	@Autowired
	private ChapterRepository	chapterRepository;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private Validator			validator;


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

	public Chapter reconstructAssign(final Chapter chapter, final BindingResult binding) {
		final Chapter result = chapter;
		final Chapter res = this.chapterRepository.findOne(this.findByPrincipal().getId());
		result.setAddress(res.getAddress());
		result.setEmail(res.getEmail());
		result.setId(res.getId());
		result.setMiddleName(res.getMiddleName());
		result.setName(res.getName());
		result.setPhoneNumber(res.getPhoneNumber());
		result.setPhoto(res.getPhoto());
		result.setSurname(res.getSurname());
		result.setUserAccount(res.getUserAccount());
		result.setVersion(res.getVersion());
		result.setTitle(res.getTitle());
		this.validator.validate(result, binding);
		return result;

	}
	Integer countChapterWithArea() {
		return this.chapterRepository.findChapterWithArea();
	}

	public Chapter reconstructEdit(final ActorEditForm actorEditForm) {
		final Chapter res;
		res = this.findByPrincipal();
		res.setName(actorEditForm.getName());
		res.setMiddleName(actorEditForm.getMiddleName());
		res.setSurname(actorEditForm.getSurname());
		res.setPhoto(actorEditForm.getPhoto());
		res.setEmail(actorEditForm.getEmail());
		res.setPhoneNumber(actorEditForm.getPhoneNumber());
		res.setAddress(actorEditForm.getAddress());
		Assert.notNull(res);
		return res;
	}
}
