
package controllers.all;

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

import services.BrotherhoodService;
import services.ParadeService;
import services.PathService;
import services.SegmentService;
import controllers.AbstractController;
import domain.Parade;
import domain.Path;
import domain.Segment;

@Controller
@RequestMapping("/path")
public class PathController extends AbstractController {

	@Autowired
	private PathService			pathService;

	@Autowired
	private SegmentService		segmentService;

	@Autowired
	private ParadeService		paradeService;

	@Autowired
	private BrotherhoodService	brotherhoodService;


	@RequestMapping(value = "/listByParade", method = RequestMethod.GET)
	public ModelAndView listByParade(final int paradeId) {
		ModelAndView result;
		Collection<Path> paths;

		try {
			Assert.notNull(paradeId);

			paths = this.pathService.findPathsByParade(paradeId);

			result = new ModelAndView("path/listByParade");
			result.addObject("requestURI", "path/listByParade.do?=" + paradeId);
			result.addObject("paths", paths);
			result.addObject("paradeId", paradeId);
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int paradeId) {
		ModelAndView result;
		Path path;
		path = new Path();
		final Collection<Path> paths;
		try {
			Assert.notNull(paradeId);
			final Parade parade = this.paradeService.findOne(paradeId);
			path.setSegments(null);
			final Path pa = this.pathService.save(path);
			final Parade par = this.paradeService.addPath(parade, pa);
			paths = this.pathService.findPathsByParade(par.getId());
			result = new ModelAndView("path/listByParade");
			result.addObject("requestURI", "path/listByParade.do?=" + par.getId());
			result.addObject("paths", paths);
			result.addObject("paradeId", par.getId());
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int pathId) {
		ModelAndView result;
		Path path;
		try {
			path = this.pathService.findOne(pathId);
			Assert.isTrue(this.pathService.checkBrotherhoodPath(path));
			final Parade parade = this.paradeService.findParadeByPath(path);
			Collection<Path> paths;
			this.pathService.delete(path);
			paths = this.pathService.findPathsByParade(parade.getId());
			result = new ModelAndView("path/listByParade");
			result.addObject("requestURI", "path/listByParade.do?paradeId=" + parade.getId());
			result.addObject("paths", paths);
			result.addObject("paradeId", parade.getId());
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}

	@RequestMapping(value = "/deleteSegment", method = RequestMethod.GET)
	public ModelAndView deleteSegment(@RequestParam final int segmentId) {
		ModelAndView result;
		Segment segment;
		try {
			segment = this.segmentService.findOne(segmentId);
			Assert.notNull(segment);
			final Path path = this.pathService.findPathBySegment(segment);
			Assert.isTrue(this.pathService.checkBrotherhoodPath(path));
			final Parade parade = this.paradeService.findParadeByPath(path);
			final Collection<Path> paths;
			Assert.notNull(segment);
			this.segmentService.delete(segment);
			paths = this.pathService.findPathsByParade(parade.getId());
			result = new ModelAndView("path/listByParade");
			result.addObject("requestURI", "path/listByParade.do?paradeId=" + parade.getId());
			result.addObject("paths", paths);
			result.addObject("paradeId", parade.getId());
		} catch (final Exception e) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}

	@RequestMapping(value = "/editSegment", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int segmentId) {
		ModelAndView result = new ModelAndView("path/edit");

		try {
			final Segment segment = this.segmentService.findOne(segmentId);
			Assert.notNull(segment);
			final Path path = this.pathService.findPathBySegment(segment);
			Assert.isTrue(this.pathService.checkBrotherhoodPath(path));
			final Parade parade = this.paradeService.findParadeByPath(path);
			Assert.isTrue(parade.getBrotherhood().equals(this.brotherhoodService.findByPrincipal()));
			result.addObject("segment", segment);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@ModelAttribute("segment") Segment segment, final BindingResult binding) {
		ModelAndView result;
		Path path;
		try {
			path = this.pathService.findPathBySegment(segment);

			segment = this.segmentService.reconstruct(segment, binding);
			final Parade parade = this.paradeService.findParadeByPath(path);

			if (binding.hasErrors())
				result = this.createEditModelAndView(segment);
			else
				try {
					final Boolean b = this.segmentService.validateOrigin(segment, path);
					if (!b)
						result = this.createEditModelAndViewSegment(segment, "segment.origin.error");
					else if (segment.getDestinationTime().before(segment.getOriginTime()))
						result = this.createEditModelAndViewSegment(segment, "segment.date.error");
					else {
						this.segmentService.save(segment);
						result = new ModelAndView("path/listByParade");
						result.addObject("requestURI", "path/listByParade.do?paradeId=" + parade.getId());
						result.addObject("paths", this.pathService.findPathsByParade(parade.getId()));
						result.addObject("paradeId", parade.getId());
					}
				} catch (final Throwable oops) {
					result = this.createEditModelAndView(segment, "segment.commit.error");
				}
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}
	protected ModelAndView createEditModelAndView(final Segment segment) {
		return this.createEditModelAndView(segment, null);
	}

	protected ModelAndView createEditModelAndView(final Segment segment, final String messageCode) {
		final ModelAndView result;
		result = new ModelAndView("path/edit");
		result.addObject("segment", segment);
		result.addObject("message", messageCode);

		return result;
	}

	// Create

	@RequestMapping(value = "/createSegment", method = RequestMethod.GET)
	public ModelAndView createSegment(@RequestParam final int pathId) {
		ModelAndView result;

		try {

			Segment segment;
			segment = new Segment();
			segment.setId(0);
			segment.setSequence(pathId);
			result = this.createEditModelAndViewSegment(segment);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}

	@RequestMapping(value = "/createSeg", method = RequestMethod.POST, params = "save")
	public ModelAndView saveSegment(@ModelAttribute("segment") Segment segment, final BindingResult binding) {
		ModelAndView result;
		Path path;
		try {
			path = this.pathService.findOne(segment.getSequence());
			segment = this.segmentService.reconstruct(segment, binding);
			final Parade parade = this.paradeService.findParadeByPath(path);

			if (binding.hasErrors())
				result = this.createEditModelAndViewSegment(segment);
			else
				try {
					segment.setSequence(path.getSegments().size() + 1);
					final Boolean b = this.segmentService.validateOrigin(segment, path);
					if (!b) {
						segment.setSequence(path.getId());
						result = this.createEditModelAndViewSegment(segment, "segment.origin.error");
					} else if (segment.getDestinationTime().before(segment.getOriginTime())) {
						segment.setSequence(path.getId());
						result = this.createEditModelAndViewSegment(segment, "segment.date.error");
					} else {
						this.segmentService.save(segment);
						this.pathService.addSegment(path, segment);
						result = new ModelAndView("path/listByParade");
						result.addObject("requestURI", "path/listByParade.do?paradeId=" + parade.getId());
						result.addObject("paths", this.pathService.findPathsByParade(parade.getId()));
						result.addObject("paradeId", parade.getId());
					}
				} catch (final Throwable oops) {
					result = this.createEditModelAndViewSegment(segment, "segment.commit.error");
				}
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/#");
		}
		return result;
	}
	protected ModelAndView createEditModelAndViewSegment(final Segment segment) {
		return this.createEditModelAndViewSegment(segment, null);
	}

	protected ModelAndView createEditModelAndViewSegment(final Segment segment, final String messageCode) {
		final ModelAndView result;
		result = new ModelAndView("path/createSeg");
		result.addObject("segment", segment);
		result.addObject("message", messageCode);
		return result;
	}

}
