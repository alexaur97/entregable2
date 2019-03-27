
package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Parade;

@Repository
public interface ParadeRepository extends JpaRepository<Parade, Integer> {

	// FR 8.2 - FR 10.2
	@Query("select p from Parade p where p.brotherhood.id = ?1")
	Collection<Parade> findParadesByBrotherhood(int id);

	@Query("select p from Parade p where p.brotherhood.area.id = ?1 order by status")
	Collection<Parade> findParadesByArea(int id);

	// FR 12.3.5
	@Query("select p from Parade p where DATEDIFF(p.moment,CURRENT_DATE)<=30 and p.moment>CURRENT_DATE")
	Collection<Parade> paradesBefore30Days();

	@Query("select p from Parade p where p.brotherhood.id = ?1 and p.status = 'ACCEPTED' ")
	Collection<Parade> findParadesAcceptedByBrotherhood(int id);

	@Query("select p from Parade p where p.brotherhood.id = ?1 and p.status = 'REJECTED' ")
	Collection<Parade> findParadesRejectedByBrotherhood(int id);

	@Query("select p from Parade p where p.brotherhood.id = ?1 and p.status = 'SUBMITTED' ")
	Collection<Parade> findParadesSubmittedByBrotherhood(int id);

	@Query("select p from Parade p where p.brotherhood.id = ?1 and p.status = 'CLEARED' ")
	Collection<Parade> findParadesClearedByBrotherhood(int id);

	@Query("select sum(case when p.mode='DRAFT' then 1.0 else 0.0 end),sum(case when p.mode='FINAL' then 1.0 else 0.0 end)from Parade p")
	Collection<Double> modeStats();
	@Query("select sum(case when p.status='SUBMITTED' then 1.0 else 0.0 end),sum(case when p.status='ACCEPTED' then 1.0 else 0.0 end),sum(case when p.status='REJECTED' then 1.0 else 0.0 end)from Parade p")
	Collection<Double> statusStats();
	@Query("select p from Parade p where p.mode='FINAL' and p.brotherhood.area.id=?1 order by status")
	Collection<Parade> findFinalParadeByArea(int id);

	@Query("select p from Parade p where (p.title like %?1%)or (p.description like %?1%)or (p.ticker like %?1%)")
	Collection<Parade> searchParadesKeyWord(String keyword);

	@Query("select p from Parade p where p.moment >= ?1 ")
	Collection<Parade> searchParadesDateFrom(Date dateFrom);

	@Query("select p from Parade p where p.moment <= ?1 ")
	Collection<Parade> searchParadesDateTo(Date dateTo);

	@Query("select p from Parade p where p.brotherhood.area.id")
	Collection<Parade> searchParadesArea(int areaId);

}
