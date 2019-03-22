
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.PeriodRecord;

@Repository
public interface PeriodRecordRepository extends JpaRepository<PeriodRecord, Integer> {

	@Query("select h.periodRecord from History h where h.brotherhood.id=?1")
	Collection<PeriodRecord> findByBrotherhood(Integer id);
}
