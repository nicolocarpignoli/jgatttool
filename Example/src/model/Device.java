package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Device {
	private String UUID;
	private String id;
	private String type;
	private ArrayList<Property> prop;

	public Device(String uUID, String id, String type) {
		super();
		UUID = uUID;
		this.id = id;
		this.type = type;
		this.prop = new ArrayList<Property>();

	}
	public String getUUID() {
		return UUID;
	}
	public void setUUID(String uUID) {
		UUID = uUID;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public ArrayList<Property> getProp() {
		return prop;
	}

	public void setProp(ArrayList<Property> prop) {
		this.prop = prop;
	}

	
}
