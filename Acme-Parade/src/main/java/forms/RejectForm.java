
package forms;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

public class RejectForm {

	private String	explanation;
	private int		requestId;


	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getExplanation() {
		return this.explanation;
	}

	public void setExplanation(final String explanation) {
		this.explanation = explanation;
	}

	@NotNull
	public int getRequestId() {
		return this.requestId;
	}

	public void setRequestId(final int requestId) {
		this.requestId = requestId;
	}

}
