
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.FinderRepository;

@Service
@Transactional
public class FinderService {

	@Autowired
	private FinderRepository	finderRepository;


	public Collection<Double> findStatsResultsFinders() {
		final Collection<Double> result = this.finderRepository.findStatsResultsFinders();
		Assert.notEmpty(result);
		return result;
	}

	public Collection<Double> findEmptyVsNonEmptyFindersRatio() {
		final Collection<Double> result = this.finderRepository.findEmptyVsNonEmptyFindersRatio();
		Assert.notEmpty(result);
		return result;
	}
}
