package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AreaRepository;
import domain.Area;
import domain.Brotherhood;


@Service
@Transactional
public class AreaService {
	//Repositorio
	
	@Autowired
	private AreaRepository areaRepository;
	
	//Servicios
	@Autowired
	private AdministratorService administratorService;
	
	@Autowired
	private BrotherhoodService brotherhoodService;
	
	
	//Metodos CRUD
	
	public Collection<Area> findAll(){
		Collection <Area> result = this.areaRepository.findAll();
		return result;
	}
	
	public Area findOne(int AreaId){
		Area area = this.areaRepository.findOne(AreaId);
		return area;
	}
	
	public void delete(final Area area) {
		Assert.notNull(area);
		this.administratorService.findByPrincipal();
		Assert.isTrue(!this.hasSettle(area));
		this.areaRepository.delete(area.getId());
		
		
	}

	public Area save(final Area area) {
		final Area result;
	
		Assert.notNull(area);
		this.administratorService.findByPrincipal();
		result = this.areaRepository.save(area);
		return result;
	}

	public Area create() {
		this.administratorService.findByPrincipal();
		return new Area();
	}
	
	public Boolean hasSettle(final Area area){
		Boolean res = true;
		Collection<Brotherhood> b  =this.brotherhoodService.findBrotherhoodByArea(area.getId()) ;
		if(b == null){
			res= false;
		}
		return res;
		
	}
}
