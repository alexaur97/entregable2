
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AreaRepository;
import domain.Area;

@Service
@Transactional
public class AreaService {

	//Repositorio

	@Autowired
	private AreaRepository			areaRepository;

	//Servicios
	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private BrotherhoodService		brotherhoodService;


	//Metodos CRUD

	public Collection<Area> findAll() {
		final Collection<Area> result = this.areaRepository.findAll();
		return result;
	}

	public Area findOne(final int AreaId) {
		final Area area = this.areaRepository.findOne(AreaId);
		return area;
	}

	public Area save(final Area area) {
		final Area result;

		Assert.notNull(area);
		this.administratorService.findByPrincipal();
		result = this.areaRepository.save(area);
		return result;

	}
<<<<<<< HEAD
	//	public void delete(final Area area) {
	//		Assert.notNull(area);
	//		this.administratorService.findByPrincipal();
	//		Assert.isTrue(!this.hasSettle(area));
	//		this.areaRepository.delete(area.getId());
	//
	//	}
=======

	public void delete(final Area area) {
		Assert.notNull(area);
		this.administratorService.findByPrincipal();
		Assert.isTrue(!this.hasSettle(area));
		this.areaRepository.delete(area.getId());

	}
>>>>>>> 17dcc14c894328afb66f087b2898449f288e474c

	public Area create() {
		this.administratorService.findByPrincipal();
		return new Area();
	}

<<<<<<< HEAD
	//	public Boolean hasSettle(final Area area){
	//		Boolean res = true;
	//		Collection<Brotherhood> b  =this.brotherhoodService.findBrotherhoodByArea(area.getId()) ;
	//		if(b == null){
	//			res= false;
	//		}
	//		return res;
	//		
	//	}
=======
	public Boolean hasSettle(final Area area) {
		Boolean res = true;
		final Collection<Brotherhood> b = this.brotherhoodService.findBrotherhoodByArea(area.getId());

		if (b.isEmpty())
			res = false;
		return res;

	}
>>>>>>> 17dcc14c894328afb66f087b2898449f288e474c
}
