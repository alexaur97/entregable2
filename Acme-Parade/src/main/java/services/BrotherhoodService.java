
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.BrotherhoodRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Area;
import domain.Brotherhood;
import domain.Member;
import forms.ActorEditForm;
import forms.BrotherhoodRegisterForm;

@Service
@Transactional
public class BrotherhoodService {

	@Autowired
	private BrotherhoodRepository	brotherhoodRepository;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private HistoryService			historyService;

	@Autowired
	private AreaService				areaService;


	public Brotherhood save(final Brotherhood b) {
		Assert.notNull(b);
		//		this.findByPrincipal();
		return this.brotherhoodRepository.save(b);

	}
	//RF.2 List the brotherhoods in the system

	public Collection<Brotherhood> findAll() {
		Collection<Brotherhood> result;
		result = this.brotherhoodRepository.findAll();
		return result;
	}

	public Brotherhood findOne(final int id) {
		Assert.isTrue(id != 0);
		Brotherhood result;
		result = this.brotherhoodRepository.findOne(id);
		return result;
	}

	public Brotherhood create() {

		final Brotherhood b = new Brotherhood();
		final UserAccount ua = new UserAccount();
		b.setUserAccount(ua);
		final Authority a = new Authority();
		a.setAuthority(Authority.BROTHERHOOD);
		final Collection<Authority> authorities = new ArrayList<Authority>();
		authorities.add(a);
		b.getUserAccount().setAuthorities(authorities);
		return b;

	}

	public void delete(final Brotherhood brotherhood) {
		Assert.notNull(brotherhood);
		this.findByPrincipal();
		this.brotherhoodRepository.delete(brotherhood);

	}

	public Collection<Brotherhood> findBrotherhoodByMemberBelong(final int id) {
		Assert.isTrue(id != 0);
		final Authority auth = new Authority();
		auth.setAuthority(Authority.MEMBER);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(auth));
		final Collection<Brotherhood> result = new ArrayList<Brotherhood>();
		Collection<Brotherhood> brotherhoods;
		brotherhoods = this.brotherhoodRepository.findBrotherhoodByMemberBelong(id);
		for (final Brotherhood b : brotherhoods)
			if (b.getMembers().contains(this.actorService.findByPrincipal()))
				result.add(b);
		return result;
	}

	public Collection<Brotherhood> findBrotherhoodByMemberHasBelonged(final int id) {
		Assert.isTrue(id != 0);
		final Authority auth = new Authority();
		auth.setAuthority(Authority.MEMBER);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(auth));
		final Collection<Brotherhood> result = new ArrayList<Brotherhood>();
		Collection<Brotherhood> brotherhoods;
		brotherhoods = this.brotherhoodRepository.findBrotherhoodByMemberHasBelonged(id);
		for (final Brotherhood b : brotherhoods)
			if (!(b.getMembers().contains(this.actorService.findByPrincipal())))
				result.add(b);
		return result;

	}

	public Brotherhood findByPrincipal() {
		final UserAccount user = LoginService.getPrincipal();
		Assert.notNull(user);

		final Brotherhood b = this.findByUserId(user.getId());
		Assert.notNull(b);
		this.actorService.auth(b, Authority.BROTHERHOOD);
		return b;
	}

	public Brotherhood findByUserId(final int id) {
		final Brotherhood b = this.brotherhoodRepository.findByUserId(id);
		return b;
	}

	//---Ale----

	public Collection<Brotherhood> findLargest() {
		final Collection<Brotherhood> result = new ArrayList<>();
		final Collection<Brotherhood> largest = this.brotherhoodRepository.findLargest();
		Assert.notNull(largest);
		if (largest.size() > 3)
			for (int i = 0; i < 3; i++) {
				final Brotherhood b = (Brotherhood) largest.toArray()[i];
				result.add(b);
			}
		else
			result.addAll(largest);
		return result;
	}

	public Collection<Brotherhood> findSmallest() {
		final Collection<Brotherhood> result = new ArrayList<>();
		final Collection<Brotherhood> smallest = this.brotherhoodRepository.findSmallest();
		Assert.notNull(smallest);
		if (smallest.size() > 3)
			for (int i = 0; i < 3; i++) {
				final Brotherhood b = (Brotherhood) smallest.toArray()[i];
				result.add(b);
			}
		else
			result.addAll(smallest);
		return result;
	}

	public Brotherhood reconstruct(final BrotherhoodRegisterForm r) {
		Assert.isTrue(r.getPassword().equals(r.getConfirmPassword()));
		final Brotherhood result = this.create();
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
		result.setEstablishmentDate(r.getEstablishmentDate());
		result.setPhotos(r.getPhotos());
		final Collection<Member> members = new ArrayList<>();
		result.setMembers(members);

		return result;
	}

	//---Ale----
	//JAVI
	public Brotherhood reconstructEdit(final ActorEditForm actorEditForm) {
		final Brotherhood res;
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

	public Boolean validatePictures(final Collection<String> pictures) {
		final String regex = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=_|!:,.;]*[-a-zA-Z0-9+&@#/%=_|]";
		final Pattern patt = Pattern.compile(regex);
		Boolean b = true;

		if (!pictures.isEmpty())
			for (final String s : pictures) {
				final Matcher matcher = patt.matcher(s);
				if (!matcher.matches()) {
					b = false;
					break;
				}
			}
		return b;
	}

	//
	//	public Collection<Double> findStatsBrotherhoodPerArea() {
	//		final Collection<Double> result = new ArrayList<>();
	//		final Collection<Double> col = new ArrayList<>();
	//		final Collection<Area> areas = this.areaService.findAll();
	//		Double acum = 0.0;
	//		Double max = 0.0;
	//		Double min = (double) this.brotherhoodRepository.findAll().size();
	//		Double stddev = 0.0;
	//		Double sq = 0.0;
	//		for (final Area a : areas) {
	//			final Double d = this.brotherhoodRepository.findNumberOfBrotherhoodsPerArea(a.getId());
	//			acum = acum + d;
	//			sq = sq + d * d;
	//			if (d > max)
	//				max = d;
	//			if (d < min)
	//				min = d;
	//
	//		}
	//		final Integer areasSize = this.areaService.findAll().size();
	//		final Double avg = acum / areasSize;
	//		stddev = Math.sqrt(sq / acum - avg * avg);
	//
	//		result.add(avg);
	//		result.add(min);
	//		result.add(max);
	//		result.add(stddev);
	//		return result;
	//	}

	//	public Map<Area, Double> findRatioBrotherhoodPerArea() {
	//		final Map<Area, Double> result = new HashMap<>();
	//		final Collection<Area> areas = this.areaService.findAll();
	//
	//		for (final Area a : areas) {
	//			final Double d = this.brotherhoodRepository.findRatioBrotherhoodsPerArea(a.getId());
	//			result.put(a, d);
	//		}
	//		return result;
	//	}
	//	
	//	public Collection<Brotherhood> findBrotherhoodByArea(final int id) {
	//		final Collection<Brotherhood> res = this.brotherhoodRepository.findBrotherhoodByArea(id);
	//		return res;
	//	}

	public Collection<Double> findStatsBrotherhoodPerArea() {
		final Collection<Double> result = new ArrayList<>();
		final Collection<Double> col = new ArrayList<>();
		final Collection<Area> areas = this.areaService.findAll();
		Double acum = 0.0;
		Double max = 0.0;
		Double min = (double) this.brotherhoodRepository.findAll().size();
		Double stddev = 0.0;
		Double sq = 0.0;
		for (final Area a : areas) {
			final Double d = this.brotherhoodRepository.findNumberOfBrotherhoodsPerArea(a.getId());
			acum = acum + d;
			sq = sq + d * d;
			if (d > max)
				max = d;
			if (d < min)
				min = d;

		}
		final Integer areasSize = this.areaService.findAll().size();
		final Double avg = acum / areasSize;
		final Double den = acum - avg * avg;
		stddev = Math.sqrt(sq / den);

		result.add(avg);
		result.add(min);
		result.add(max);
		result.add(stddev);
		return result;
	}

	public Map<Area, Double> findRatioBrotherhoodPerArea() {
		final Map<Area, Double> result = new HashMap<>();
		final Collection<Area> areas = this.areaService.findAll();

		for (final Area a : areas) {
			final Double d = this.brotherhoodRepository.findRatioBrotherhoodsPerArea(a.getId());
			result.put(a, d);
		}
		return result;
	}

	public Collection<Brotherhood> findBrotherhoodByArea(final int id) {
		final Collection<Brotherhood> res = this.brotherhoodRepository.findBrotherhoodByArea(id);
		return res;
	}

	public Double countBrotherhoodsPerArea(final int id) {
		return this.brotherhoodRepository.findNumberOfBrotherhoodsPerArea(id);
	}
	//	// FR 4.1.2 ACME PARADE
	//	public Brotherhood findBrotherhoodWithLargestHistory() {
	//		final Brotherhood result = this.historyService.findLargest().getBrotherhood();
	//		Assert.notNull(result);
	//		return result;
	//	}
	//	// FR 4.1.3 ACME PARADE
	//	public Collection<Brotherhood> findBrotherhoodsWithLargerHistoryThanAverage() {
	//		final Collection<History> histories = this.historyService.findLargerThanAverage();
	//		final Collection<Brotherhood> result = new ArrayList<>();
	//		for (final History h : histories)
	//			result.add(h.getBrotherhood());
	//		Assert.notNull(result);
	//		return result;
	//	}
}
