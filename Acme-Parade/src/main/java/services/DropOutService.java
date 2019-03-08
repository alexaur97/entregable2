
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.DropOutRepository;
import security.LoginService;
import domain.Brotherhood;
import domain.DropOut;
import domain.Member;

@Service
@Transactional
public class DropOutService {

	// Repositorios propios
	@Autowired
	private DropOutRepository	dropOutRepository;

	// Servicios ajenos
	@Autowired
	private BrotherhoodService	brotherhoodService;


	// M�todos CRUD

	public Collection<DropOut> findAll() {
		Collection<DropOut> result;
		result = this.dropOutRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public DropOut findOne(int dropOutId) {
		Assert.isTrue(dropOutId != 0);
		DropOut result;
		result = this.dropOutRepository.findOne(dropOutId);
		Assert.notNull(result);
		return result;
	}

	public void delete(DropOut dropOut) {
		Assert.notNull(dropOut);
		this.dropOutRepository.delete(dropOut);
	}

	public void save(DropOut dropOut) {
		Assert.notNull(dropOut);
		Member m = dropOut.getMember();
		Brotherhood b = dropOut.getBrotherhood();
		Collection<Member> ms = b.getMembers();
		ms.remove(m);
		b.setMembers(ms);
		this.brotherhoodService.save(b);
		this.dropOutRepository.save(dropOut);
	}
	// FR 10.3 - 11.2
	public DropOut create(Member member, Brotherhood brotherhood, Date moment) {
		Assert.notNull(member);
		Assert.notNull(brotherhood);
		Assert.notNull(moment);
		Assert.isTrue(LoginService.getPrincipal().getId() == member.getUserAccount().getId() || LoginService.getPrincipal().getId() == brotherhood.getUserAccount().getId());
		Assert.isTrue(brotherhood.getMembers().contains(member));
		DropOut result = new DropOut();
		result.setBrotherhood(brotherhood);
		result.setMember(member);
		result.setMoment(moment);
		return result;
	}
	public Collection<DropOut> dropOutByMember(int id) {
		Collection<DropOut> res;
		res = this.dropOutRepository.dropOutByMember(id);
		return res;
	}
}
