
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class History extends DomainEntity {

	// Atributos Públicos

	public Brotherhood						brotherhood;
	public InceptionRecord					inceptionRecord;
	public Collection<PeriodRecord>			periodRecord;
	public Collection<MiscellaneousRecord>	miscellaneousRecord;
	public Collection<LegalRecord>			legalRecord;
	public Collection<LinkRecord>			linkRecord;


	//Getters and Setters

	@OneToOne(optional = false)
	public Brotherhood getBrotherhood() {
		return this.brotherhood;
	}

	public void setBrotherhood(final Brotherhood brotherhood) {
		this.brotherhood = brotherhood;
	}

	@OneToOne(optional = false, cascade = CascadeType.ALL)
	public InceptionRecord getInceptionRecord() {
		return this.inceptionRecord;
	}

	public void setInceptionRecord(final InceptionRecord inceptionRecord) {
		this.inceptionRecord = inceptionRecord;
	}

	@NotNull
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<PeriodRecord> getPeriodRecord() {
		return this.periodRecord;
	}

	public void setPeriodRecord(final Collection<PeriodRecord> periodRecord) {
		this.periodRecord = periodRecord;
	}

	@NotNull
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<MiscellaneousRecord> getMiscellaneousRecord() {
		return this.miscellaneousRecord;
	}

	public void setMiscellaneousRecord(final Collection<MiscellaneousRecord> miscellaneousRecord) {
		this.miscellaneousRecord = miscellaneousRecord;
	}

	@NotNull
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<LegalRecord> getLegalRecord() {
		return this.legalRecord;
	}

	public void setLegalRecord(final Collection<LegalRecord> legalRecord) {
		this.legalRecord = legalRecord;
	}

	@NotNull
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<LinkRecord> getLinkRecord() {
		return this.linkRecord;
	}

	public void setLinkRecord(final Collection<LinkRecord> linkRecord) {
		this.linkRecord = linkRecord;
	}

}
