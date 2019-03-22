
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Path;

@Repository
public interface PathRepository extends JpaRepository<Path, Integer> {

	// FR 3.3
	@Query("select p.paths from Parade p where p.id = ?1")
	Collection<Path> findPathsByParade(int id);

}
