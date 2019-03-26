
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Entity
@Access(AccessType.PROPERTY)
public class LegalRecord extends DomainEntity {

	private String				title;
	private String				description;
	private String				legalName;
	private Double				vatNumber;
	private Collection<String>	laws;


	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getLegalName() {
		return this.legalName;
	}

	public void setLegalName(final String legalName) {
		this.legalName = legalName;
	}

	@Min(0)
	@Digits(integer = 2, fraction = 2)
	@NotNull
	public Double getVatNumber() {
		return this.vatNumber;
	}

	public void setVatNumber(final Double vatNumber) {
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
