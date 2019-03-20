
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

	@Query("select p from Parade p where p.brotherhood.area.id = ?1 group by status")
	Collection<Parade> findParadesByArea(int id);

	// FR 12.3.5
	@Query("select p from Parade p where DATEDIFF(p.moment,CURRENT_DATE)<=30 and p.moment>CURRENT_DATE")
	Collection<Parade> paradesBefore30Days();
	
	@Query("select p from Parade p where " +
			"((p.title like %?1%) or (p.description like %?1%) or (p.ticker like %?1%)) and "+
			"((p.title like %?1%) or (p.description like %?1%)) and "+
			"(p.brotherhood.area.id = ?4 )")
	Collection<Parade> searchParades(String keyword, Date dateFrom, Date dateTo, int areaId);
	
	@Query("select p from Parade p where " +
			"((p.title like %?1%) or (p.description like %?1%) or (p.ticker like %?1%)) and "+
			"(p.moment >= ?2 ) and "+
			"(p.brotherhood.area.id = ?3 )")
	Collection<Parade> searchParadesWithoutEndDate(String keyword, Date dateFrom, int areaId);
	
	@Query("select p from Parade p where " +
			"((p.title like %?1%) or (p.description like %?1% ) or (p.ticker like %?1%)) and "+
			"(p.moment >= ?2) and (p.moment <= ?3)")
	Collection<Parade> searchParadesWithoutArea(String keyword, Date dateFrom, Date dateTo);
	
	@Query("select p from Parade p where " +
			"((p.title like %?1%) or (p.description like %?1%) or (p.ticker like %?1%)) and "+
			"(p.moment >= ?2 )")
	Collection<Parade> searchParadesWithoutEndDateOrArea(String keyword, Date dateFrom);
 
}
