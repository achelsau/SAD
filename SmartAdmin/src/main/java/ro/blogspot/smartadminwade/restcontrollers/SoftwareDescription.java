package ro.blogspot.smartadminwade.restcontrollers;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Used as a DTO from backend to UI
 * 
 * @author Ariel
 *
 */
@XmlRootElement
public class SoftwareDescription {
	private Set<String> versions, operatingSystems, operatingSystemsVersions, architectures;
	private String license, release, link;

	public SoftwareDescription() {
		// TODO Auto-generated constructor stub
	}

	public SoftwareDescription(Set<String> versions, Set<String> operatingSystems,
			Set<String> operatingSystemsVersions, Set<String> architectures) {
		this.versions = versions;
		this.operatingSystems = operatingSystems;
		this.operatingSystemsVersions = operatingSystemsVersions;
		this.architectures = architectures;
	}

	public Set<String> getVersions() {
		return versions;
	}

	public void setVersions(Set<String> versions) {
		this.versions = versions;
	}

	public Set<String> getOperatingSystems() {
		return operatingSystems;
	}

	public void setOperatingSystems(Set<String> operatingSystems) {
		this.operatingSystems = operatingSystems;
	}

	public Set<String> getOperatingSystemsVersions() {
		return operatingSystemsVersions;
	}

	public void setOperatingSystemsVersions(Set<String> operatingSystemsVersions) {
		this.operatingSystemsVersions = operatingSystemsVersions;
	}

	public Set<String> getArchitectures() {
		return architectures;
	}

	public void setArchitectures(Set<String> architectures) {
		this.architectures = architectures;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getRelease() {
		return release;
	}

	public void setRelease(String release) {
		this.release = release;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
}
