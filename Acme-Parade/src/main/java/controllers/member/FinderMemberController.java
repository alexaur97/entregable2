package controllers.member;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AreaService;
import services.FinderService;
import services.MemberService;
import services.ParadeService;
import controllers.AbstractController;
import domain.Area;
import domain.Finder;
import domain.Parade;
import domain.Request;

@Controller
@RequestMapping("finder/member")
public class FinderMemberController extends AbstractController {

	@Autowired
	MemberService	memberService;

	@Autowired
	ParadeService	paradeService;
	
	@Autowired
	FinderService	finderService;
	
	@Autowired
	AreaService	areaService;
	
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView search() {
		ModelAndView result = new ModelAndView("finder/view");

		try {
			Finder finder = this.finderService.getFinderFromMember(memberService.findByPrincipal().getId());
			Collection<Parade> parades = finder.getParades();
			Collection<Area> areas = areaService.findAll();

			result.addObject("parades", parades);
			result.addObject("areas", areas);
			result.addObject("finder", finder);
			//result.addObject("resquestURI", "/finder/member/view.do");
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}
	
	@RequestMapping(value = "/view", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Finder finder, final BindingResult binding) {
		ModelAndView res = new ModelAndView("finder/view");
		Collection<Parade> parades = finder.getParades();
		Collection<Area> areas = areaService.findAll();

		if (binding.hasErrors()) {
			res.addObject("parades", parades);
			res.addObject("areas", areas);
			res.addObject("finder", finder);
		} else
			try {
				finder = this.finderService.reconstruct(finder);
				this.finderService.save(finder);
				res = new ModelAndView("redirect:/finder/member/view.do");
			} catch (final Throwable oops) {
				res.addObject("message", "request.commit.error");
				res.addObject("parades", parades);
				res.addObject("areas", areas);
				res.addObject("finder", finder);
			}

		return res;
	}
	
	@RequestMapping(value = "/view", method = RequestMethod.POST, params = "clean")
	public ModelAndView clean(Finder finder, final BindingResult binding) {
		ModelAndView res = new ModelAndView("finder/view");
		Collection<Parade> parades = new ArrayList<Parade>();
		Collection<Area> areas = areaService.findAll();

		if (binding.hasErrors()) {
			res.addObject("parades", parades);
			res.addObject("areas", areas);
			res.addObject("finder", finder);
		} else
			try {
				finder = this.finderService.reconstruct(finder);
				this.finderService.saveAfterClean(finder);
				res = new ModelAndView("redirect:/finder/member/view.do");
			} catch (final Throwable oops) {
				res.addObject("message", "request.commit.error");
				res.addObject("parades", parades);
				res.addObject("areas", areas);
				res.addObject("finder", finder);
			}

		return res;
	}

	
}


