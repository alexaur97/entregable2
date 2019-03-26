
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Parade extends DomainEntity {

	// Atributos Privados

	private String				title;
	private String				description;
	private Date				moment;
	private String				ticker;
	private String				mode;
	private String				status;

	private String				explanation;
	// Atributos Publicos

	public Brotherhood			brotherhood;
	public Collection<Float>	floats;
	public Collection<Path>		paths;


	// Getters y Setters

	@ManyToMany
	public Collection<Float> getFloats() {
		return this.floats;
	}

	public void setFloats(final Collection<Float> floats) {
		this.floats = floats;
	}

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

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@NotBlank
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String date) {

		this.ticker = date;

	}

	@NotBlank
	@Pattern(regexp = "^DRAFT|FINAL$")
	public String getMode() {
		return this.mode;
	}

	public void setMode(final String mode) {
		this.mode = mode;
	}

	@ManyToOne(optional = false)
	public Brotherhood getBrotherhood() {
		return this.brotherhood;
	}

	public void setBrotherhood(final Brotherhood brotherhood) {
		this.brotherhood = brotherhood;
	}

	@NotBlank
	@Pattern(regexp = "^SUBMITTED|ACCEPTED|REJECTED|CLEARED$")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	@OneToMany(cascade = CascadeType.ALL)
	public Collection<Path> getPaths() {
		return this.paths;
	}

	public void setPaths(final Collection<Path> paths) {
		this.paths = paths;
	}

	public String getExplanation() {
		return this.explanation;
	}

	public void setExplanation(final String explanation) {
		this.explanation = explanation;
	}

}
