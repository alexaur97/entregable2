
package forms;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import domain.Parade;

public class RequestForm {

	private Parade	parade;
	private int		row;
	private int		column;
	private int		requestId;


	// Constructor
	public RequestForm() {
		super();
	}

	public Parade getParade() {
		return this.parade;
	}

	public void setParade(final Parade parade) {
		this.parade = parade;
	}

	@NotNull
	public int getRequestId() {
		return this.requestId;
	}

	public void setRequestId(final int requestId) {
		this.requestId = requestId;
	}

	@Min(0)
	public Integer getRow() {
		return this.row;
	}

	public void setRow(final Integer row) {
		this.row = row;
	}

	@Min(0)
	public Integer getColumn() {
		return this.column;
	}

	public void setColumn(final Integer column) {
		this.column = column;
	}

}
