
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.RequestRepository;
import security.Authority;
import security.LoginService;
import domain.Actor;
import domain.Brotherhood;
import domain.Member;
import domain.Request;

@Service
@Transactional
public class RequestService {

	@Autowired
	private RequestRepository	requestRepository;
	@Autowired
	private BrotherhoodService	brotherhoodService;
	@Autowired
	private MemberService		memberService;
	@Autowired
	private ActorService		actorService;
	@Autowired
	private Validator			validator;


	public Request findOne(final int id) {

		Request result;
		result = this.requestRepository.findOne(id);
		return result;
	}

	public void delete(final Request request) {
		final Authority auth = new Authority();
		auth.setAuthority(Authority.MEMBER);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(auth));
		Assert.notNull(request);
		Assert.isTrue(request.getMember().getId() == this.memberService.findByPrincipal().getId());
		Assert.isTrue(request.getStatus().equals("PENDING"));
		this.requestRepository.delete(request);
	}

	//FR 10.6
	public Collection<Request> findRequestByStatusAndBrotherhood(final String status) {
		Collection<Request> result;
		final Authority auth = new Authority();
		auth.setAuthority(Authority.BROTHERHOOD);
		Assert.isTrue(this.actorService.findByPrincipal().getUserAccount().getAuthorities().contains(auth));

		final Integer id = this.brotherhoodService.findByPrincipal().getId();
		result = this.requestRepository.findRequestByStatusAndBrotherhood(status, id);
		return result;
	}

	//FR 11.1
	public Collection<Request> findRequestByStatusAndMember(final String status) {
		Collection<Request> result;
		final Authority auth = new Authority();
		auth.setAuthority(Authority.MEMBER);
		Assert.isTrue(this.actorService.findByPrincipal().getUserAccount().getAuthorities().contains(auth));

		final Integer id = this.memberService.findByPrincipal().getId();
		result = this.requestRepository.findRequestByStatusAndMember(status, id);
		return result;

	}
	public Request create() {
		Request result;
		result = new Request();
		result.setStatus("PENDING");
		result.setRow(0);
		result.setColumn(0);
		return result;
	}
	public Request save(final Request r) {
		Assert.notNull(r);
		final Actor a = this.actorService.findByPrincipal();
		Assert.notNull(r.getMember());
		Assert.isTrue(this.actorService.authEdit(a, "MEMBER") || this.actorService.authEdit(a, "BROTHERHOOD"));
		final Member m = r.getMember();
		final Brotherhood b = r.getParade().getBrotherhood();
		if (this.actorService.authEdit(a, "MEMBER")) {
			Assert.isTrue(this.memberService.findByPrincipal().getId() == m.getId());
			Assert.isTrue(r.getStatus() == "PENDING");
		}
		if (this.actorService.authEdit(a, "BROTHERHOOD"))
			Assert.isTrue(this.brotherhoodService.findByPrincipal().getId() == b.getId());
		//	final Collection<Request> rs = this.requestRepository.findRequestApprovedByParade(r.getParade().getId(), "APPROVED");
		final Brotherhood brotherhood = r.parade.brotherhood;
		Assert.isTrue(brotherhood.getMembers().contains(m));
		final Request res = r;
		return this.requestRepository.save(res);

	}
	//---Ale---

	public Double approvedRatio() {
		final Double result = this.requestRepository.approvedRatio();
		return result;
	}

	public Double pendingRatio() {
		final Double result = this.requestRepository.pendingRatio();
		return result;
	}

	public Double rejectedRatio() {
		final Double result = this.requestRepository.rejectedRatio();
		return result;
	}

	//---Ale---

	//	public Request reconstruct(final Request request) {
	//		final Request res = request;
	//
	//		if (request.getId() != 0) {
	//			final Request r = this.findOne(request.getId());
	//			res.setColumn(r.getColumn());
	//			res.setExplanation(r.getExplanation());
	//			res.setMember(r.getMember());
	//			res.setParade(r.getParade());
	//			res.setRow(r.getRow());
	//			res.setStatus(r.getStatus());
	//		}
	//		return res;
	//	}

	public Request reconstruct(final Request request, final BindingResult binding) {
		final Request res = request;

		final Member m = this.memberService.findByPrincipal();
		res.setMember(m);
		res.setStatus("PENDING");

		if (request.getId() != 0) {
			final Request r = this.findOne(request.getId());
			res.setExplanation(r.getExplanation());
		}

		this.validator.validate(res, binding);

		return res;
	}
	public Request rejectRecostruction(final Request request, final BindingResult binding) {
		final Request res = request;
		final Request a = this.requestRepository.findOne(request.getId());
		res.setStatus("REJECTED");
		res.setMember(a.getMember());
		res.setParade(a.getParade());
		res.setColumn(a.getColumn());
		res.setRow(a.getRow());
		return res;
	}
	public Request acceptRecostruction(final Request request, final BindingResult binding) {
		final Request result = request;
		final int id = request.getId();
		final Request res = this.requestRepository.findOne(id);
		result.setStatus("APPROVED");
		result.setMember(res.member);
		result.setParade(res.parade);
		this.validator.validate(result, binding);
		return result;
	}
	public Boolean posDisp(final int id, final int column, final int row) {
		final Collection<Request> all = this.requestRepository.findRequestApprovedByParade(id, "APPROVED");
		Boolean res = true;
		for (final Request request : all)
			if (request.getColumn() == column && request.getRow() == row)
				res = false;
		return res;
	}

	public String findPos(final int id) {
		final Collection<Request> all = this.requestRepository.findRequestApprovedByParade(id, "APPROVED");
		int posR = 0;
		int posC = 0;
		for (final Request request : all) {
			final int column = request.getColumn();
			final int row = request.getRow();
			if ((column - 1) > 0 && (row - 1) > 0) {
				if (this.posDisp(id, column - 1, row)) {
					posC = column - 1;
					posR = row;
				} else if (this.posDisp(id, column, row - 1)) {
					posC = column;
					posR = row - 1;
				} else if (this.posDisp(id, column - 1, row - 1)) {
					posC = column - 1;
					posR = row - 1;
				}
			} else if (this.posDisp(id, column + 1, row)) {
				posC = column + 1;
				posR = row;
			} else if (this.posDisp(id, column, row + 1)) {
				posC = column;
				posR = row + 1;
			} else if (this.posDisp(id, column + 1, row + 1)) {
				posC = column + 1;
				posR = row + 1;
			}
		}
		final String result = "Columna: " + posC + "Fila: " + posR;
		return result;
	}
}
