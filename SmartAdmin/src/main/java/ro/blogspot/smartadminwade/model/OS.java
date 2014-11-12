package ro.blogspot.smartadminwade.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Ariel
 *
 */
@XmlRootElement
public class OS {
	public String name, version, architecture;

	public OS(String name, String version, String architecture) {
		super();
		this.name = name;
		this.version = version;
		this.architecture = architecture;
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

	public String getArchitecture() {
		return architecture;
	}

	public void setArchitecture(String architecture) {
		this.architecture = architecture;
	}
	
	
}
