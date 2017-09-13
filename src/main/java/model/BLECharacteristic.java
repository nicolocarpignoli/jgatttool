package model;

public class BLECharacteristic<T>{
	private String UUID;
	private String hnd;
	private boolean isreadable;
	private boolean iswritable;
	private boolean isnotify;
	private Object value;
	private final Class<T> typeClass;
	private boolean isOn;	// if true dev is activated and value is realtime value. if false value is lkv
	
	public BLECharacteristic(Object val, Class<T> type, String uuid, String handle, 
		boolean isread, boolean iswrite, boolean isnotif){
		typeClass = type;
		UUID = uuid;
		hnd = handle;
		isreadable = isread;
		iswritable = iswrite;
		isnotify = isnotif;	
		value = val;
	}
	
	public boolean isOn() {
		return isOn;
	}
	public void setOn(boolean isOn) {
		this.isOn = isOn;
	}
	public Class<T> getTypeClass() {
		return typeClass;
	}
	public String getUUID() {
		return UUID;
	}
	public String getHnd() {
		return hnd;
	}
	public void setUUID(String u) {
		UUID = u;	
	}
	public void setHnd(String h) {
		hnd = h;	
	}
	public boolean getIsreadable() {
		return isreadable;
	}
	public void setIsreadable(boolean isreadable) {
		this.isreadable = isreadable;
	}
	public boolean getIswritable() {
		return iswritable;
	}
	public void setIswritable(boolean iswritable) {
		this.iswritable = iswritable;
	}
	public boolean getIsnotify() {
		return isnotify;
	}
	public void setIsnotify(boolean isnotify) {
		this.isnotify = isnotify;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object object) {
		this.value = object;
	}
}
