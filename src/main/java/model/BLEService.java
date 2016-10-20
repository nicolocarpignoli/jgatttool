package model;

public class BLEService {
	private String UUID;
	private String hnd;
	
	public BLEService(String uuid, String handle){
		UUID = uuid;
		hnd = handle;
	}

	public String getUUID() {
		return UUID;
	}

	public void setUUID(String uUID) {
		UUID = uUID;
	}

	public String getHnd() {
		return hnd;
	}

	public void setHnd(String hnd) {
		this.hnd = hnd;
	}
	
	
}
