
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Finder;

@Repository
public interface FinderRepository extends JpaRepository<Finder, Integer> {

	@Query("select avg(f.parades.size),min(f.parades.size),max(f.parades.size),stddev(f.parades.size) from Finder f")
	Collection<Double> findStatsResultsFinders();

	@Query("select (sum(case when f.keyword='' and f.minDate='' and f.maxDate='' and area is null then 1.0 else 0.0 end)/count(f)),sum(case when f.keyword='' and f.minDate='' and f.maxDate='' and area is null then 0.0 else 1.0 end)/(count(f)) from Finder f")
	Collection<Double> findEmptyVsNonEmptyFindersRatio();
	
	@Query("select f from Finder f where f.member.id = ?1")
	Finder getFinderFromMember(int memberId);
}
