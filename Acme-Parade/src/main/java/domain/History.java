
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

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

	@OneToOne
	public Brotherhood getBrotherhood() {
		return this.brotherhood;
	}

	public void setBrotherhood(final Brotherhood brotherhood) {
		this.brotherhood = brotherhood;
	}

	@OneToOne(cascade = CascadeType.ALL)
	public InceptionRecord getInceptionRecord() {
		return this.inceptionRecord;
	}

	public void setInceptionRecord(final InceptionRecord inceptionRecord) {
		this.inceptionRecord = inceptionRecord;
	}
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<PeriodRecord> getPeriodRecord() {
		return this.periodRecord;
	}

	public void setPeriodRecord(final Collection<PeriodRecord> periodRecord) {
		this.periodRecord = periodRecord;
	}

	@OneToMany(cascade = CascadeType.ALL)
	public Collection<MiscellaneousRecord> getMiscellaneousRecord() {
		return this.miscellaneousRecord;
	}

	public void setMiscellaneousRecord(final Collection<MiscellaneousRecord> miscellaneousRecord) {
		this.miscellaneousRecord = miscellaneousRecord;
	}

	@OneToMany(cascade = CascadeType.ALL)
	public Collection<LegalRecord> getLegalRecord() {
		return this.legalRecord;
	}

	public void setLegalRecord(final Collection<LegalRecord> legalRecord) {
		this.legalRecord = legalRecord;
	}

	@OneToMany(cascade = CascadeType.ALL)
	public Collection<LinkRecord> getLinkRecord() {
		return this.linkRecord;
	}

	public void setLinkRecord(final Collection<LinkRecord> linkRecord) {
		this.linkRecord = linkRecord;
	}

}
