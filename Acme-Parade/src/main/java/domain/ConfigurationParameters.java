
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class ConfigurationParameters extends DomainEntity {

	private String	name;
	private String	banner;
	private String	sysMessage;
	private String	sysMessageEs;
	private String	countryCode;
	private Integer	finderCachedHours;
	private Integer	finderMaxResults;


	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotBlank
	@URL
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getBanner() {
		return this.banner;
	}

	public void setBanner(final String banner) {
		this.banner = banner;
	}

	@NotBlank
	@Pattern(regexp = "^\\+\\d{1,3}$")
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getCountryCode() {
		return this.countryCode;
	}

	public void setCountryCode(final String countryCode) {
		this.countryCode = countryCode;
	}
	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getSysMessage() {
		return this.sysMessage;
	}

	public void setSysMessage(final String sysMessage) {
		this.sysMessage = sysMessage;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getSysMessageEs() {
		return this.sysMessageEs;
	}

	public void setSysMessageEs(final String sysMessageEs) {
		this.sysMessageEs = sysMessageEs;
	}

	@NotNull
	public Integer getFinderCachedHours() {
		return this.finderCachedHours;
	}

	public void setFinderCachedHours(final Integer finderCachedHours) {
		this.finderCachedHours = finderCachedHours;
	}

	@NotNull
	public Integer getFinderMaxResults() {
		return this.finderMaxResults;
	}

	public void setFinderMaxResults(final Integer finderMaxResults) {
		this.finderMaxResults = finderMaxResults;
	}
}
