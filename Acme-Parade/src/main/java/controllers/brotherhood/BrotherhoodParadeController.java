
package controllers.brotherhood;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.BrotherhoodService;
import services.FloatService;
import services.ParadeService;
import services.PathService;
import controllers.AbstractController;
import domain.Actor;
import domain.Brotherhood;
import domain.Float;
import domain.Parade;
import domain.Path;

@Controller
@RequestMapping("/brotherhood/parade/")
public class BrotherhoodParadeController extends AbstractController {

	// Supporting services ----------------------------------------------------

	@Autowired
	private ParadeService		paradeService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private BrotherhoodService	brotherhoodService;

	@Autowired
	private FloatService		floatService;

	@Autowired
	private PathService			pathService;


	public BrotherhoodParadeController() {
		super();
	}

	// List -----------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;
		try {
			final Brotherhood currentActor = this.brotherhoodService.findByPrincipal();
			Collection<Parade> parades;
			parades = this.paradeService.findParadesByBrotherhood(currentActor.getId());

			result = new ModelAndView("parade/list");
			result.addObject("requestURI", "parade/list.do");
			result.addObject("parades", parades);
			result.addObject("currentActor", currentActor);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}

	// List -----------------------------------------------------------
	@RequestMapping(value = "/myList", method = RequestMethod.GET)
	public ModelAndView myList() {
		ModelAndView result;
		try {
			final Integer currentActorId = this.brotherhoodService.findByPrincipal().getId();
			Collection<Parade> paradesSubmitted;
			Collection<Parade> paradesAccepted;
			Collection<Parade> paradesRejected;
			Collection<Parade> paradesCleared;

			paradesAccepted = this.paradeService.findParadesAcceptedByBrotherhood(currentActorId);
			paradesRejected = this.paradeService.findParadesRejectedByBrotherhood(currentActorId);
			paradesSubmitted = this.paradeService.findParadesSubmittedByBrotherhood(currentActorId);
			paradesCleared = this.paradeService.findParadesClearedByBrotherhood(currentActorId);

			result = new ModelAndView("parade/myList");
			result.addObject("requestURI", "parade/myList.do");
			result.addObject("paradesSubmitted", paradesSubmitted);
			result.addObject("paradesAccepted", paradesAccepted);
			result.addObject("paradesRejected", paradesRejected);
			result.addObject("paradesCleared", paradesCleared);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result;
		Parade parade;
		parade = new Parade();

		try {
			final Brotherhood bh = this.brotherhoodService.findByPrincipal();
			Assert.notNull(bh.getArea());
			parade.setId(0);
			parade.setBrotherhood(bh);

			final Collection<Float> floats = this.floatService.findFloatsByBrotherhood(bh.getId());

			result = new ModelAndView("parade/edit");
			result.addObject("parade", parade);
			result.addObject("floats", floats);
		} catch (final Throwable oops) {
			if (this.brotherhoodService.findByPrincipal().getArea() == null) {
				result = new ModelAndView("parade/myList");
				final Integer currentActorId = this.actorService.findByPrincipal().getId();
				Collection<Parade> paradesSubmitted;
				Collection<Parade> paradesAccepted;
				Collection<Parade> paradesRejected;
				Collection<Parade> paradesCleared;

				paradesAccepted = this.paradeService.findParadesAcceptedByBrotherhood(currentActorId);
				paradesRejected = this.paradeService.findParadesRejectedByBrotherhood(currentActorId);
				paradesSubmitted = this.paradeService.findParadesSubmittedByBrotherhood(currentActorId);
				paradesCleared = this.paradeService.findParadesClearedByBrotherhood(currentActorId);

				result.addObject("paradesSubmitted", paradesSubmitted);
				result.addObject("paradesAccepted", paradesAccepted);
				result.addObject("paradesRejected", paradesRejected);
				result.addObject("paradesCleared", paradesCleared);

				result.addObject("requestURI", "parade/list.do");
				result.addObject("message", "parade.area.error");
			} else
				result = this.createEditModelAndView(parade, "parade.commit.error");

		}

		return result;
	}

	//Create a copy

	@RequestMapping(value = "/copy", method = RequestMethod.GET)
	public ModelAndView copy(final int paradeId) {
		ModelAndView result;
		try {

			final Brotherhood brotherhood = this.brotherhoodService.findByPrincipal();
			Assert.notNull(paradeId);
			final Parade parade = this.paradeService.findOne(paradeId);
			Assert.notNull(parade);
			Assert.isTrue(parade.getBrotherhood().equals(brotherhood));
			final Parade paradeFinal = this.paradeService.copyParade(parade);
			Assert.notNull(paradeFinal);
			this.paradeService.save(paradeFinal);
			final Actor currentActor = this.actorService.findByPrincipal();
			Collection<Parade> parades;
			parades = this.paradeService.findParadesByBrotherhood(currentActor.getId());

			result = new ModelAndView("parade/list");
			result.addObject("parades", parades);
			result.addObject("currentActor", currentActor);

		} catch (final Throwable oops) {
			final Integer currentActorId = this.actorService.findByPrincipal().getId();
			result = new ModelAndView("parade/myList");
			Collection<Parade> paradesSubmitted;
			Collection<Parade> paradesAccepted;
			Collection<Parade> paradesRejected;
			Collection<Parade> paradesCleared;
			paradesAccepted = this.paradeService.findParadesAcceptedByBrotherhood(currentActorId);
			paradesRejected = this.paradeService.findParadesRejectedByBrotherhood(currentActorId);
			paradesSubmitted = this.paradeService.findParadesSubmittedByBrotherhood(currentActorId);
			paradesCleared = this.paradeService.findParadesClearedByBrotherhood(currentActorId);
			result = new ModelAndView("parade/myList");
			result.addObject("paradesSubmitted", paradesSubmitted);
			result.addObject("paradesAccepted", paradesAccepted);
			result.addObject("paradesRejected", paradesRejected);
			result.addObject("paradesCleared", paradesCleared);
			result.addObject("message", oops.getMessage());
			final String msg = oops.getMessage();
			if (msg.equals("finalStatus")) {
				final Boolean finalStatus = true;
				result.addObject("finalStatus", finalStatus);
			} else
				result = new ModelAndView("redirect:/#");
		}
		return result;

	}
	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int paradeId) {
		ModelAndView res = new ModelAndView("parade/edit");

		try {
			Assert.notNull(paradeId);
			final Parade parade = this.paradeService.findOne(paradeId);
			final Brotherhood bh = this.brotherhoodService.findByPrincipal();

			Assert.isTrue(parade.getBrotherhood().equals(bh));

			final Collection<Float> floats = this.floatService.findFloatsByBrotherhood(bh.getId());
			final Collection<Path> paths = this.pathService.findPathsByParade(paradeId);
			res.addObject("floats", floats);
			res.addObject("paths", paths);
			res.addObject("parade", parade);
		} catch (final Exception e) {
			res = new ModelAndView("redirect:/#");
		}
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("parade") Parade parade, final BindingResult binding) {
		ModelAndView res;

		parade = this.paradeService.reconstruct(parade, binding);

		if (binding.hasErrors())
			res = this.createEditModelAndView(parade);
		else
			try {
				this.paradeService.save(parade);
				res = new ModelAndView("redirect:/brotherhood/parade/myList.do");
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(parade, "parade.commit.error");

			}

		return res;
	}

	@RequestMapping(value = "edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Parade parade, final BindingResult binding) {
		ModelAndView result;

		try {

			this.paradeService.delete(parade);
			result = new ModelAndView("redirect:/brotherhood/parade/myList.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(parade, "parade.commit.error");

		}

		return result;
	}
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int paradeId) {
		ModelAndView result;
		Parade parade;

		try {
			Assert.notNull(paradeId);

			parade = this.paradeService.findOne(paradeId);

			final Brotherhood bh = this.brotherhoodService.findByPrincipal();

			Assert.isTrue(parade.getBrotherhood().equals(bh));

			final Collection<Float> floats = parade.getFloats();
			result = new ModelAndView("parade/show");

			result.addObject("parade", parade);
			result.addObject("floats", floats);

		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}

	protected ModelAndView createEditModelAndView(final Parade parade) {
		return this.createEditModelAndView(parade, null);
	}
	protected ModelAndView createEditModelAndView(final Parade parade, final String messageCode) {
		final ModelAndView res;
		res = new ModelAndView("parade/edit");
		final Brotherhood bh = this.brotherhoodService.findByPrincipal();
		final Collection<Float> floats = this.floatService.findFloatsByBrotherhood(bh.getId());
		res.addObject("parade", parade);
		res.addObject("floats", floats);
		res.addObject("message", messageCode);

		return res;
	}

}
