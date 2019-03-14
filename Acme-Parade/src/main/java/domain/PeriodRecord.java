
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class PeriodRecord extends DomainEntity {

	// Atributos Privados

	private String				title;
	private String				description;
	private Integer				startYear;
	private Integer				endYear;
	private Collection<String>	pictures;


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

	@Min(1500)
	public Integer getStartYear() {
		return this.startYear;
	}

	public void setStartYear(final Integer startYear) {
		this.startYear = startYear;
	}

	public Integer getEndYear() {
		return this.endYear;
	}

	public void setEndYear(final Integer endYear) {
		this.endYear = endYear;
	}

	@ElementCollection
	public Collection<String> getPictures() {
		return this.pictures;
	}

	public void setPictures(final Collection<String> pictures) {
		this.pictures = pictures;
	}

}
