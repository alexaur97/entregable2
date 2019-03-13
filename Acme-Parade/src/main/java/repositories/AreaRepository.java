package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Area;
import domain.Position;

@Repository
public interface AreaRepository extends JpaRepository<Area, Integer>{
	
	
	

}
