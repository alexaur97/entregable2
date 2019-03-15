
package controllers.brotherhood;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.BrotherhoodService;
import services.EnrolmentService;
import services.MemberService;
import services.PositionService;
import controllers.AbstractController;
import domain.Brotherhood;
import domain.Enrolment;
import domain.Member;
import domain.Position;

@Controller
@RequestMapping("enrolment/brotherhood")
public class EnrolmentBrotherhoodController extends AbstractController {

	@Autowired
	EnrolmentService	enrolmentService;

	@Autowired
	PositionService		positionService;

	@Autowired
	MemberService		memberService;

	@Autowired
	BrotherhoodService	brotherhoodService;


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Enrolment enrolment;
		try {
			final Brotherhood b = this.brotherhoodService.findByPrincipal();
			//			Assert.notNull(b.getArea());

			enrolment = new Enrolment();
			enrolment.setId(0);
			final Collection<Member> members = this.memberService.findAllNotIn();
			final Collection<Position> positions = this.positionService.findAll();
			result = new ModelAndView("enrolment/create");
			result.addObject("enrolment", enrolment);
			result.addObject("members", members);
			result.addObject("positions", positions);
		} catch (final Exception e) {
			if (this.brotherhoodService.findByPrincipal().getArea() == null) {
				final Collection<Member> members = this.memberService.findMembersByBrotherhoodPrincipal();
				result = new ModelAndView("member/list");
				result.addObject("members", members);
				result.addObject("message", "enrolment.area.error");
			} else
				result = new ModelAndView("redirect:/#");
		}
		return result;

	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView save(Enrolment enrolment, final BindingResult binding) {
		ModelAndView res;

		if (binding.hasErrors()) {
			res = new ModelAndView("enrolment/create");
			res.addObject("enrolment", enrolment);
		} else
			try {
				enrolment = this.enrolmentService.reconstruct(enrolment);
				this.enrolmentService.save(enrolment);
				res = new ModelAndView("redirect:/member/brotherhood/list.do");
			} catch (final Throwable oops) {
				res = new ModelAndView("member/list");
				final Collection<Member> members = this.brotherhoodService.findByPrincipal().getMembers();
				res.addObject("members", members);
				res.addObject("requestURI", "enrolment/brotherhood/edit.do");
				res.addObject("message", "enrolment.commit.error");
			}

		return res;
	}
}
