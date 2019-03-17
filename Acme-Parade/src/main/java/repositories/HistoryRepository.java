
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.History;

public interface HistoryRepository extends JpaRepository<History, Integer> {

	@Query("select h from History h where h.brotherhood.id = ?1")
	History findHistoryByBrotherhood(int id);
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

}
