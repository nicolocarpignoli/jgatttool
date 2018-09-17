package javamodel;

public class Property {
	private String name;
	private String value;
	private String type;
	private String utc_time;
	private String domain;

	public Property(String name, String dom, String value, String type, String utc_time) {
		super();
		this.domain = "";
		this.name = name;
		this.value = value;
		this.type = type;
		this.utc_time = utc_time;
		this.domain = dom;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUtc_time() {
		return utc_time;
	}

	public void setUtc_time(String utc_time) {
		this.utc_time = utc_time;
	}

	public String printMsg() {
		return this.getUtc_time() + "_" + this.getValue() + "_" + this.getType();
	}
}
