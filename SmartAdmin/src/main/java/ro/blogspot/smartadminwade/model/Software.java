package ro.blogspot.smartadminwade.model;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Ariel
 *
 */
@XmlRootElement
public class Software {
	private ResourceType type;
	private String name, version, os, osVersion, osArchitecture, license, userFriendlyName;
	private Set<Software> dependsOn;

	public Software() {
	}

	public Software(ResourceType type, String name, String version, String os, String osVersion, String osArchitecture,
			Set<Software> dependsOn) {
		this.type = type;
		this.name = name;
		this.version = version;
		this.os = os;
		this.osVersion = osVersion;
		this.osArchitecture = osArchitecture;
		this.dependsOn = dependsOn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public ResourceType getType() {
		return type;
	}

	public void setType(ResourceType type) {
		this.type = type;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getOsVersion() {
		return osVersion;
	}

	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}

	public String getOsArchitecture() {
		return osArchitecture;
	}

	public void setOsArchitecture(String osArchitecture) {
		this.osArchitecture = osArchitecture;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getUserFriendlyName() {
		return userFriendlyName;
	}

	public void setUserFriendlyName(String userFriendlyName) {
		this.userFriendlyName = userFriendlyName;
	}

	public Set<Software> getDependsOn() {
		return dependsOn;
	}

	public void setDependsOn(Set<Software> dependsOn) {
		this.dependsOn = dependsOn;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dependsOn == null) ? 0 : dependsOn.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((os == null) ? 0 : os.hashCode());
		result = prime * result + ((osArchitecture == null) ? 0 : osArchitecture.hashCode());
		result = prime * result + ((osVersion == null) ? 0 : osVersion.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Software other = (Software) obj;
		if (dependsOn == null) {
			if (other.dependsOn != null)
				return false;
		} else if (!dependsOn.equals(other.dependsOn))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (os == null) {
			if (other.os != null)
				return false;
		} else if (!os.equals(other.os))
			return false;
		if (osArchitecture == null) {
			if (other.osArchitecture != null)
				return false;
		} else if (!osArchitecture.equals(other.osArchitecture))
			return false;
		if (osVersion == null) {
			if (other.osVersion != null)
				return false;
		} else if (!osVersion.equals(other.osVersion))
			return false;
		if (type != other.type)
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Software [name=" + name + ", dependsOn=" + dependsOn + "]";
	}
	
}
