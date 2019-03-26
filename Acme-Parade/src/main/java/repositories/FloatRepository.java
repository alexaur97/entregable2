
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Float;

@Repository
public interface FloatRepository extends JpaRepository<Float, Integer> {

	// FR 8.2 - FR 10.1
	@Query("select f from Float f where f.brotherhood.id=?1")
	Collection<Float> findFloatsByBrotherhood(int id);

	@Query("select p.floats from Parade p where p.brotherhood.id=?1 ")
	Collection<Float> findFloatsInParade(int id);

}
