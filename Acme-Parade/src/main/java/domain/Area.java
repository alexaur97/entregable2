
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Entity
@Access(AccessType.PROPERTY)
public class Area extends DomainEntity {

	//Atributos privados

	private String				name;
	private Collection<String>	photo;


	//Getters and Setters

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotEmpty
	@ElementCollection
	public Collection<String> getPhoto() {
		return this.photo;
	}

	public void setPhoto(final Collection<String> photo) {
		this.photo = photo;
	}

}
