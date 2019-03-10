
package controllers.all;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.BrotherhoodService;
import services.ParadeService;
import controllers.AbstractController;
import domain.Parade;

@Controller
@RequestMapping("/parade")
public class ParadeController extends AbstractController {

	// Supporting services ----------------------------------------------------
	@Autowired
	private ParadeService		paradeService;
	@Autowired
	private BrotherhoodService	brotherhoodService;


	@RequestMapping(value = "/listByBrotherhood", method = RequestMethod.GET)
	public ModelAndView listByBrotherhood(final int brotherhoodId) {
		ModelAndView result;
		Collection<Parade> parades;

		try {
			Assert.notNull(brotherhoodId);

			parades = this.paradeService.findFinalParadesByBrotherhood(brotherhoodId);

			result = new ModelAndView("parade/listByBrotherhood");
			result.addObject("requestURI", "parade/listByBrotherhood.do?=" + brotherhoodId);
			result.addObject("parades", parades);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}

}
