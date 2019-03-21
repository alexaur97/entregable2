
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Segment;

@Repository
public interface SegmentRepository extends JpaRepository<Segment, Integer> {

	// FR 3.3
	@Query("select p.segments from Path p where p.id = ?1")
	Collection<Segment> findSegmentsByPath(int id);

}
