package main;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import model.BLEDevice;
import model.Device;
import model.Property;


public class GatewayCore {
	
	private ArrayList<BLEDevice> bledevices;
	private ZonedDateTime utc;
	private RegistryAdapter registry;	
	private String def_psw;
	
	public GatewayCore(RegistryAdapter adapter, String psw){
		def_psw = psw;
		registry = adapter;
		bledevices = new ArrayList<BLEDevice>();
		utc = ZonedDateTime.now(ZoneOffset.UTC);
	}
	
	
	public boolean checkDev_in_Registry(String UUID){
		return registry.checkIfEquals(UUID, def_psw);
	}

	//TODO write characteristics
	public void set_BLE_val_from_dev(BLEDevice d, String str){}
	
	public void get_BLE_val_from_dev(BLEDevice d) {
		for(Property p : d.getProp()){	
			Method m = checkRefl(p.getName());
			if(m != null){
				try {
					p.setValue("" + m.invoke(d.getAdapter()));
				} catch (IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					System.out.println("Error in BLECharacteristic reflection");
					e.printStackTrace();
				}
				utc = ZonedDateTime.now(ZoneOffset.UTC);
				p.setUtc_time(utc.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			}else{
				System.out.println("Error: unknown property requested");
			}
		}
	}
	
	public Method checkRefl(String prop){
		for(Method m : Genuino101Adapter.class.getDeclaredMethods()){
			if(m.getName().equalsIgnoreCase(("read" + prop))){
				return m;
			}
		}
		return null;
	}
	
	
	public void connect_to_BLE_dev(BLEDevice d) throws InterruptedException{		
		if(d != null){
			d.getAdapter().init(d.getMac());
			d.getAdapter().populateChars();
		}
	}
	
	public ArrayList<BLEDevice> getBLEDevices() {
		return bledevices;
	}

	public void setBLEDevices(ArrayList<BLEDevice> devices) {
		this.bledevices = devices;
	}
	
	public ArrayList<BLEDevice> getBledevices() {
		return bledevices;
	}


	public void setBledevices(ArrayList<BLEDevice> bledevices) {
		this.bledevices = bledevices;
	}


	public RegistryAdapter getRegistry() {
		return registry;
	}


	public void setRegistry(RegistryAdapter registry) {
		this.registry = registry;
	}


	

	

}
