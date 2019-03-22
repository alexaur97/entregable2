
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Chapter;

@Repository
public interface ChapterRepository extends JpaRepository<domain.Chapter, Integer> {

	@Query("select c from Chapter c where c.userAccount.id=?1")
	Chapter findByUserId(int id);

	@Query("select c from Chapter c where c.area is NULL")
	Collection<Chapter> findChaptersWithoutArea();

	@Query("select sum(case when c.area is null then 0.0 else 1.0 end)from Chapter c")
	Integer findChapterWithArea();
}
