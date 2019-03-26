
package forms;

import java.util.Collection;

import javax.persistence.ElementCollection;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

public class HistoryCreateForm {

	private String				inceptionRecordTitle;
	private String				inceptionRecordDescription;
	private Collection<String>	inceptionRecordPictures;


	@NotBlank
	public String getInceptionRecordTitle() {
		return this.inceptionRecordTitle;
	}

	public void setInceptionRecordTitle(final String inceptionRecordTitle) {
		this.inceptionRecordTitle = inceptionRecordTitle;
	}

	@NotBlank
	public String getInceptionRecordDescription() {
		return this.inceptionRecordDescription;
	}

	public void setInceptionRecordDescription(final String inceptionRecordDescription) {
		this.inceptionRecordDescription = inceptionRecordDescription;
	}

	@NotEmpty
	@ElementCollection
	public Collection<String> getInceptionRecordPictures() {
		return this.inceptionRecordPictures;
	}

	public void setInceptionRecordPictures(final Collection<String> inceptionRecordPictures) {
		this.inceptionRecordPictures = inceptionRecordPictures;
	}

}
