
package service;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import services.ParadeService;
import services.PathService;
import services.SegmentService;
import utilities.AbstractTest;
import domain.Path;
import domain.Segment;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class SegmentServiceTest extends AbstractTest {

	@Autowired
	private PathService		pathService;

	@Autowired
	private ParadeService	paradeService;

	@Autowired
	private SegmentService	segmentService;


	// En este test se testea el requisito 3.3

	// Análisis del Sentence Coverage: 
	// 1. Logueo de usuario.
	// 2. Elección de un segment.
	// 3. Comprobar que el segment pertenece al usuario logueado.
	// 4. Modificar el segment.
	// 5. Guardar el segment.
	// 6. Comprobar que el Segment ha sido modificado correctamente.

	// Análisis del Data Coverage:
	// Se verifica que se ha modificado el Segment obteniendo el segment de la base de datos 
	// y comprobando que tiene la propiedad modificada con el valor correcto.
	@Test
	public void testSegmentEdit() {
		super.authenticate("brotherhood1");

		// SEGMENT ID
		final int id = this.getEntityId("segment1");

		final Segment segment = this.segmentService.findOne(id);
		final Path path = this.pathService.findPathBySegment(segment);
		Assert.isTrue(this.pathService.checkBrotherhoodPath(path));
		segment.setOriginX(30);
		this.segmentService.save(segment);
		final Segment result = this.segmentService.findOne(id);
		Assert.isTrue(result.getOriginX() == 30);
		super.unauthenticate();
	}

	// Para el caso negativo estamos intentando modificar un tramo sin estar logueado como una Hermandad, 
	// esto debe provocar un fallo en el sistema porque el actor que debe estar logueado debe ser una Hermandad.

	@Test(expected = IllegalArgumentException.class)
	public void testSegmentEditNegative() {
		super.authenticate(null);

		// SEGMENT ID
		final int id = this.getEntityId("segment1");

		final Segment segment = this.segmentService.findOne(id);
		final Path path = this.pathService.findPathBySegment(segment);
		Assert.isTrue(this.pathService.checkBrotherhoodPath(path));
		segment.setOriginX(30);
		this.segmentService.save(segment);
		final Segment result = this.segmentService.findOne(id);
		Assert.isTrue(result.getOriginX() == 30);
		super.unauthenticate();
	}

	// En este test se testea el requisito 3.3

	// Análisis del Sentence Coverage: 
	// 1. Logueo de usuario.
	// 2. Elección de un segment.
	// 3. Comprobar que el segment pertenece al usuario logueado.
	// 4. Eliminar el segment.
	// 5. Comprobar que el Segment ha sido eliminado correctamente.

	// Análisis del Data Coverage:
	// Se verifica que se ha eliminado el Segment obteniendo la lista de segments de la base de datos 
	// y comprobando que no contiene el segment eliminado.
	@Test
	public void testSegmentDelete() {
		super.authenticate("brotherhood1");

		// SEGMENT ID
		final int id = this.getEntityId("segment1");

		final Segment segment = this.segmentService.findOne(id);
		final Path path = this.pathService.findPathBySegment(segment);
		Assert.isTrue(this.pathService.checkBrotherhoodPath(path));
		this.segmentService.delete(segment);
		final Collection<Segment> result = this.segmentService.findSegmentsByPath(path.getId());
		Assert.isTrue(!result.contains(segment));
		super.unauthenticate();
	}

	// Para el caso negativo estamos intentando eliminar un tramo estando logueado como una Hermandad diferente a la que pernece el tramo, 
	// esto debe provocar un fallo en el sistema porque un tramo solo puede ser eliminado por su hermandad.
	@Test(expected = IllegalArgumentException.class)
	public void testSegmentDeleteNegative() {
		super.authenticate("brotherhood2");

		// SEGMENT ID
		final int id = this.getEntityId("segment1");

		final Segment segment = this.segmentService.findOne(id);
		final Path path = this.pathService.findPathBySegment(segment);
		Assert.isTrue(this.pathService.checkBrotherhoodPath(path));
		this.segmentService.delete(segment);
		final Collection<Segment> result = this.segmentService.findSegmentsByPath(path.getId());
		Assert.isTrue(!result.contains(segment));
		super.unauthenticate();
	}
}
