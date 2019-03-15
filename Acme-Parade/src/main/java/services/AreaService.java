
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

	public Area create() {
		this.administratorService.findByPrincipal();
		return new Area();
	}

	//	public Boolean hasSettle(final Area area) {
	//		Bores = true;
	//		final Collection<Brotherhood> b = this.brotherhoodService.findBrotherhoodByArea(area.getId(){
	//				res= false;
	//			}final ll)
	//			res = false;
	//		return res;
	//
	//	}

	//HABRA QUE HACERLO BIEN PERO LO HE PUESTO PARA QUE NO DE ERROR
	public void delete(Area area) {
		this.areaRepository.delete(area);
	}
}
