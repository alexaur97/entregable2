
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Access(AccessType.PROPERTY)
public class LegalRecord extends DomainEntity {

	private String				title;
	private String				description;
	private String				legalName;
	private Integer				vatNumber;
	private Collection<String>	laws;


	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@NotBlank
	public String getLegalName() {
		return this.legalName;
	}

	public void setLegalName(final String legalName) {
		this.legalName = legalName;
	}

	@Min(1)
	public Integer getVatNumber() {
		return this.vatNumber;
	}

	public void setVatNumber(final Integer vatNumber) {
		this.vatNumber = vatNumber;
	}

	@ElementCollection
	@NotEmpty
	public Collection<String> getLaws() {
		return this.laws;
	}

	public void setLaws(final Collection<String> laws) {
		this.laws = laws;
	}

}
