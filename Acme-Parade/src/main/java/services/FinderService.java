
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Finder;
import domain.Member;
import domain.Parade;

import repositories.FinderRepository;

@Service
@Transactional
public class FinderService {

	@Autowired
	private FinderRepository	finderRepository;
	
	@Autowired
	private ParadeService	paradeService;
	
	@Autowired
	private MemberService	memberService;	
	
	@Autowired
	private ConfigurationParametersService	configParamsService;


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
	
	public Finder getFinderFromMember(int id) {
		return this.finderRepository.getFinderFromMember(id);
	}
	
	public void createFinder(Member memberCreated) {
		Finder finder = new Finder();
		Collection<Parade> parades = new ArrayList<Parade>();
		finder.setKeyword("");
		finder.setMember(memberCreated);
		finder.setLastSearch(new Date());
		finder.setParades(parades);
		this.finderRepository.save(finder);
	}

	public void save(Finder finder) {
		Assert.notNull(finder);
		finder.setLastSearch(new Date());
		finder.setMember(this.memberService.findByPrincipal());
		finder.setParades(this.paradeService.searchParades(finder.keyword, finder.getMinDate(), finder.getMaxDate(), finder.getArea()));
		this.finderRepository.save(finder);
	}

	public Finder reconstruct(Finder finder) {
		Assert.notNull(finder);
		Finder result = this.finderRepository.findOne(finder.getId());
		result.setArea(finder.getArea());
		result.setKeyword(finder.getKeyword());
		result.setMaxDate(finder.getMaxDate());
		result.setMinDate(finder.getMinDate());
		return result;
	}

	public void saveAfterClean(Finder finder) {
		Assert.notNull(finder);
		Collection<Parade> parades = new ArrayList<Parade>();
		finder.setLastSearch(new Date());
		finder.setMember(this.memberService.findByPrincipal());
		finder.setParades(parades);
		this.finderRepository.save(finder);
	}
	
	public void cleanFinder(Finder finder){
		Assert.notNull(finder);
		Collection<Parade> parades = new ArrayList<Parade>();
		finder.setArea(null);
		finder.setKeyword("");
		finder.setMaxDate(null);
		finder.setMinDate(null);
		finder.setLastSearch(new Date());
		//finder.setMember(this.memberService.findByPrincipal());
		finder.setParades(parades);
		this.finderRepository.save(finder);		
	}

	public void cleanCacheIfNecessary() {
		int cachedHours = this.configParamsService.find().getFinderCachedHours();
		Collection<Finder> finders = finderRepository.findAll();
		Date now = new Date();
		for(Finder f: finders){
			if((now.getTime()-f.getLastSearch().getTime())/3600000 >= cachedHours){
				cleanFinder(f);
			}
		}
		
	}
}
