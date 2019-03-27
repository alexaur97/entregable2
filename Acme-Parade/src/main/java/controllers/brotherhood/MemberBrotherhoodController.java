
package controllers.brotherhood;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.BrotherhoodService;
import services.DropOutService;
import services.EnrolmentService;
import services.MemberService;
import controllers.AbstractController;
import domain.Brotherhood;
import domain.DropOut;
import domain.Enrolment;
import domain.Member;

@Controller
@RequestMapping("member/brotherhood")
public class MemberBrotherhoodController extends AbstractController {

	@Autowired
	MemberService		memberService;
	@Autowired
	BrotherhoodService	brotherhoodService;
	@Autowired
	EnrolmentService	enrolmentService;
	@Autowired
	DropOutService		dropOutService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		try {
			final Collection<Member> members = this.brotherhoodService.findByPrincipal().getMembers();
			result = new ModelAndView("member/list");
			result.addObject("members", members);
			result.addObject("requestURI", "member/brotherhood/list.do");
			result.addObject("brotherhood", this.brotherhoodService.findByPrincipal());

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public ModelAndView profile(final int memberId) {
		ModelAndView result;
		Member member;
		Brotherhood me;

		try {
			Assert.notNull(memberId);
			me = this.brotherhoodService.findByPrincipal();
			member = this.memberService.findMembersById(memberId);
			Assert.isTrue(me.getMembers().contains(member));
			final Collection<Enrolment> enrolments = this.enrolmentService.enrolmentByMember(memberId);
			final Collection<DropOut> dropOuts = this.dropOutService.dropOutByMember(memberId);
			result = new ModelAndView("member/profile");
			result.addObject("requestURI", "member/profile.do?=" + memberId);
			result.addObject("member", member);
			result.addObject("dropOuts", dropOuts);
			result.addObject("enrolments", enrolments);
			result.addObject("brotherhood", this.brotherhoodService.findByPrincipal());

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}
}
