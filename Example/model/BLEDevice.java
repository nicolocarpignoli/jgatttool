package javamodel;

import main.Genuino101Adapter;

public class BLEDevice extends Device {
	private String mac;
	private Genuino101Adapter adapter;
	
	public BLEDevice(String UUID, String id, String type, String mac) {
		super(UUID, id, type);
		this.mac = mac;
		this.adapter = new Genuino101Adapter();
	}
	
	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public Genuino101Adapter getAdapter() {
		return adapter;
	}

	public void setAdapter(Genuino101Adapter adapter) {
		this.adapter = adapter;
	}


}
