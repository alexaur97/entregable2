
package controllers.brotherhood;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.BrotherhoodService;
import services.DropOutService;
import services.MemberService;
import controllers.AbstractController;
import domain.Brotherhood;
import domain.DropOut;
import domain.Member;

@Controller
@RequestMapping("dropOut/brotherhood")
public class DropOutBrotherhoodController extends AbstractController {

	@Autowired
	DropOutService		dropOutService;
	@Autowired
	MemberService		memberService;
	@Autowired
	BrotherhoodService	brotherhoodService;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(final int memberId) {
		ModelAndView result;
		try {
			final Member member = this.memberService.findMembersById(memberId);
			final Brotherhood brotherhood = this.brotherhoodService.findByPrincipal();
			final Date date = new Date();
			final DropOut dropOut = this.dropOutService.create(member, brotherhood, date);
			this.dropOutService.save(dropOut);
			final Collection<Member> members = this.brotherhoodService.findByPrincipal().getMembers();
			result = new ModelAndView("member/list");
			result.addObject("members", members);
			result.addObject("requestURI", "dropOut/brotherhood/create.do");
			result.addObject("brotherhood", this.brotherhoodService.findByPrincipal());

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}
		return result;

	}
}
