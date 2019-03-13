
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Embeddable
@Access(AccessType.PROPERTY)
public class Coordinate {

	private Integer	x;
	private Integer	y;


	@Min(-90)
	@Max(90)
	public Integer getX() {
		return this.x;
	}

	public void setX(final Integer x) {
		this.x = x;
	}

	@Min(-180)
	@Max(-180)
	public Integer getY() {
		return this.y;
	}

	public void setY(final Integer y) {
		this.y = y;
	}

}
