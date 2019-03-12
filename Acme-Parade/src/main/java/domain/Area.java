
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Area extends DomainEntity {

	//Atributos privados

	private String				name;
	private Collection<String>	photo;


	//Getters and Setters

	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@ElementCollection
	public Collection<String> getPhoto() {
		return this.photo;
	}

	public void setPhoto(final Collection<String> photo) {
		this.photo = photo;
	}

}
