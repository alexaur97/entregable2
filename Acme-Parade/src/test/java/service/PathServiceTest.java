
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
import utilities.AbstractTest;
import domain.Parade;
import domain.Path;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class PathServiceTest extends AbstractTest {

	@Autowired
	private PathService		pathService;

	@Autowired
	private ParadeService	paradeService;


	// En este test se testea el requisito 3.3

	// Análisis del Sentence Coverage: 
	// 1. Logueo de usuario.
	// 2. Elección de una Parade.
	// 3. Llamada al método CREATE de Path (antes de ejecutarse comprueba que el usuario sea una Brotherhood y, además, comprueba
	//		que la Parade sea seleccionada pertenezca a sus Parades.
	// 4. Guardar el Path.
	// 5. Añadir el Path a la Parade.
	// 6. Comprobar que el Path ha sido creado y que se ha añadido a la Parade correctamente.

	// Análisis del Data Coverage:
	// Se verifica que se ha creado el Path obteniendo el conjunto de Paths de la Parade y comprobando que contiene el Path creado.

	@Test
	public void testPathCreate() {
		super.authenticate("brotherhood1");

		// Parade ID
		final int id = this.getEntityId("parade1");

		final Path path = this.pathService.create();
		final Parade parade = this.paradeService.findOne(id);
		final Path pa = this.pathService.save(path);
		final Parade par = this.paradeService.addPath(parade, pa);
		final Collection<Path> result = this.pathService.findPathsByParade(par.getId());
		Boolean bol = false;
		for (final Path p : result)
			if (p.getId() == pa.getId())
				bol = true;
		Assert.isTrue(bol);
		super.unauthenticate();
	}

	// Para el caso negativo estamos intentando crear un Recorrido sin estar logueado como una Hermandad, 
	// esto debe provocar un fallo en el sistema porque el actor que debe estar logueado debe ser una Hermandad.

	@Test(expected = IllegalArgumentException.class)
	public void testPathCreateNegative() {
		super.authenticate(null);

		// Parade ID
		final int id = this.getEntityId("parade1");

		final Path path = this.pathService.create();
		final Parade parade = this.paradeService.findOne(id);
		final Path pa = this.pathService.save(path);
		final Parade par = this.paradeService.addPath(parade, pa);
		final Collection<Path> result = this.pathService.findPathsByParade(par.getId());
		Boolean bol = false;
		for (final Path p : result)
			if (p.getId() == pa.getId())
				bol = true;
		Assert.isTrue(bol);
		super.unauthenticate();
	}

	// En este test se testea el requisito 3.3

	// Análisis del Sentence Coverage: 
	// 1. Logueo de usuario.
	// 2. Elección de una Parade.
	// 3. Elección de un Path.
	// 4. Comprobar que el Path y la Parade pertenecen al brotherhood logueado.
	// 5. Llamada al método Delete de Path.
	// 6. Comprobar que el Path ha sido eliminado.

	// Análisis del Data Coverage:
	// Se verifica que se ha eliminado el Path obteniendo el conjunto de Paths de la Parade y comprobando que no contiene el Path eliminado.

	@Test
	public void testPathDelete() {
		super.authenticate("brotherhood1");

		// PATH ID
		final int id = this.getEntityId("path1");

		// PARADE ID
		final int pid = this.getEntityId("parade1");

		final Path path = this.pathService.findOne(id);
		Assert.isTrue(this.pathService.checkBrotherhoodPath(path));
		this.pathService.delete(path);
		final Collection<Path> result = this.pathService.findPathsByParade(pid);
		Assert.isTrue(!result.contains(path));
		super.unauthenticate();
	}

	// Para el caso negativo estamos intentando eliminar un recorrido estando logueado como una Hermandad diferente a la que pernece el recorrido, 
	// esto debe provocar un fallo en el sistema porque un recorrido solo puede ser eliminado por su hermandad.

	@Test(expected = IllegalArgumentException.class)
	public void testPathDeleteNegative() {
		super.authenticate("brotherhood2");

		// PATH ID
		final int id = this.getEntityId("path1");

		// PARADE ID
		final int pid = this.getEntityId("parade1");

		final Path path = this.pathService.findOne(id);
		Assert.isTrue(this.pathService.checkBrotherhoodPath(path));
		this.pathService.delete(path);
		final Collection<Path> result = this.pathService.findPathsByParade(pid);
		Assert.isTrue(!result.contains(path));
		super.unauthenticate();
	}
}
