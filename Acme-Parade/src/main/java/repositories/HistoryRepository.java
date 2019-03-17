<<<<<<< HEAD

package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.History;
import domain.InceptionRecord;
import domain.LegalRecord;
import domain.LinkRecord;
import domain.MiscellaneousRecord;
import domain.PeriodRecord;

@Repository
public interface HistoryRepository extends JpaRepository<History, Integer> {

	@Query("select h from History h where h.brotherhood.id = ?1")
	History findHistoryByBrotherhood(int id);

	@Query("select h from History h where h.inceptionRecord = ?1")
	InceptionRecord findInceptionRecord(int id);

	@Query("select h from History h where h.legalRecord = ?1")
	Collection<LegalRecord> findLegalRecord(int id);

	@Query("select h from History h where h.linkRecord = ?1")
	Collection<LinkRecord> findLinkRecord(int id);

	@Query("select h from History h where h.miscellaneousRecord = ?1")
	Collection<MiscellaneousRecord> findMiscellaneousRecord(int id);

	@Query("select h from History h where h.periodRecord = ?1")
	Collection<PeriodRecord> findPeriodRecord(int id);

}
=======

package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.History;

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

}
>>>>>>> 17dcc14c894328afb66f087b2898449f288e474c
