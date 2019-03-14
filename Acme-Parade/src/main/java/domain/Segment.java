
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Segment extends DomainEntity {

	// Atributos Privados

	private Integer	originX;
	private Integer	originY;
	private Integer	destinationX;
	private Integer	destinationY;
	private Date	originTime;
	private Date	destinationTime;
	private Integer	sequence;


	// Getters y Setters

	@Min(-90)
	@Max(90)
	public Integer getOriginX() {
		return this.originX;
	}

	public void setOriginX(final Integer originX) {
		this.originX = originX;
	}

	@Min(-180)
	@Max(180)
	public Integer getOriginY() {
		return this.originY;
	}

	public void setOriginY(final Integer originY) {
		this.originY = originY;
	}

	@Min(-90)
	@Max(90)
	public Integer getDestinationX() {
		return this.destinationX;
	}

	public void setDestinationX(final Integer destinationX) {
		this.destinationX = destinationX;
	}

	@Min(-180)
	@Max(180)
	public Integer getDestinationY() {
		return this.destinationY;
	}

	public void setDestinationY(final Integer destinationY) {
		this.destinationY = destinationY;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getOriginTime() {
		return this.originTime;
	}

	public void setOriginTime(final Date originTime) {
		this.originTime = originTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getDestinationTime() {
		return this.destinationTime;
	}

	public void setDestinationTime(final Date destinationTime) {
		this.destinationTime = destinationTime;
	}

	@Min(1)
	public Integer getSequence() {
		return this.sequence;
	}

	public void setSequence(final Integer sequence) {
		this.sequence = sequence;
	}

}
