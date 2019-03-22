
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.History;

@Repository
public interface HistoryRepository extends JpaRepository<History, Integer> {

	// FR 4.1.1 ACME PARADE
	@Query("select avg(1 + h.periodRecord.size + h.miscellaneousRecord.size + h.legalRecord.size + h.linkRecord.size)," + "min(1 + h.periodRecord.size + h.miscellaneousRecord.size + h.legalRecord.size + h.linkRecord.size),"
		+ "max(1 + h.periodRecord.size + h.miscellaneousRecord.size + h.legalRecord.size + h.linkRecord.size)," + "stddev(1 + h.periodRecord.size + h.miscellaneousRecord.size + h.legalRecord.size + h.linkRecord.size) " + "from History h")
	Collection<Double> statsRecordsPerHistory();

	// FR 4.1.2 ACME PARADE
	@Query("select h from History h where (1 + h.periodRecord.size + h.miscellaneousRecord.size + h.legalRecord.size + h.linkRecord.size) = (select max(1 + hi.periodRecord.size + hi.miscellaneousRecord.size + hi.legalRecord.size + hi.linkRecord.size) from History hi)")
	History largestHistory();

	// FR 4.1.3 ACME PARADE
	@Query("select h from History h where (1 + h.periodRecord.size + h.miscellaneousRecord.size + h.legalRecord.size + h.linkRecord.size) > (select avg(1 + hi.periodRecord.size + hi.miscellaneousRecord.size + hi.legalRecord.size + hi.linkRecord.size) from History hi)")
	Collection<History> largerHistoriesThanAverage();

	@Query("select h from History h where h.brotherhood.id = ?1")
	History findHistoryByBrotherhood(int id);

	@Query("select h from History h where h.brotherhood.id=?1")
	History findByBrotherhood(Integer id);

	@Query("select h from History h where h.inceptionRecord.id=?1")
	History findByInceptionRecord(Integer id);

	@Query("select h from History h join h.miscellaneousRecord m where m.id=?1")
	History findByMiscellaneousRecord(Integer id);

	@Query("select h from History h join h.linkRecord m where m.id=?1")
	History findByLinkRecord(Integer id);

	@Query("select h from History h join h.periodRecord m where m.id=?1")
	History findByPeriodRecord(Integer id);

	@Query("select h from History h join h.legalRecord m where m.id=?1")
	History findByLegalRecord(Integer id);
}
