
package controllers.member;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.MemberService;
import services.ParadeService;
import services.RequestService;
import controllers.AbstractController;
import domain.Parade;
import domain.Request;

@Controller
@RequestMapping("request/member")
public class RequestMemberController extends AbstractController {

	@Autowired
	MemberService	memberService;

	@Autowired
	RequestService	requestService;

	@Autowired
	ParadeService	paradeService;


	// List -----------------------------------------------------------	
	@RequestMapping(value = "/list")
	public ModelAndView list() {

		ModelAndView result;
		try {
			Collection<Request> pendingRequests;
			Collection<Request> acceptedRequests;
			Collection<Request> rejectedRequests;

			pendingRequests = this.requestService.findRequestByStatusAndMember("PENDING");
			acceptedRequests = this.requestService.findRequestByStatusAndMember("APPROVED");
			rejectedRequests = this.requestService.findRequestByStatusAndMember("REJECTED");

			result = new ModelAndView("request/list");
			result.addObject("pendingRequests", pendingRequests);
			result.addObject("acceptedRequests", acceptedRequests);
			result.addObject("rejectedRequests", rejectedRequests);
			result.addObject("resquestURI", "/request/member/list.do");
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Request request;
		final Collection<Parade> parades = this.paradeService.findParadesAvailableForMember();

		request = new Request();

		request.setId(0);
		result = new ModelAndView("request/create");
		result.addObject("request", request);
		result.addObject("parades", parades);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Request request, final BindingResult binding) {
		ModelAndView res = new ModelAndView("request/create");
		final Collection<Parade> parades = this.paradeService.findParadesAvailableForMember();

		if (binding.hasErrors()) {
			res.addObject("request", request);
			res.addObject("parades", parades);
		} else
			try {
				request = this.requestService.reconstruct(request, binding);
				this.requestService.save(request);
				res = new ModelAndView("redirect:/request/member/list.do");
			} catch (final Throwable oops) {
				res.addObject("message", "request.commit.error");
				res.addObject("parades", parades);
				res.addObject("request", request);
			}

		return res;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int requestId) {
		ModelAndView result;
		try {
			final Request request = this.requestService.findOne(requestId);
			this.requestService.delete(request);
			result = new ModelAndView("redirect:/request/member/list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}

}
