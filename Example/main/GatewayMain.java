
package main;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import javamodel.BLEDevice;
import javamodel.Property;

import org.eclipse.paho.client.mqttv3.MqttException;

import model.BLECharacteristic;

public class GatewayMain {
	private final static String GATEWAY_UUID = "d2a36a22-acb8-11e6-80f5-56304dec7eb8";
	private final static String TOPIC_GW = "figs/campo1/" + GATEWAY_UUID + "/status";
	private final static String TOPIC = "figs/campo1/";
	private final static String DEF_PSW = "usr-psw";
	private static String SERVER_IP = "89.96.192.72";
	
	public static boolean populateProperties(BLEDevice dev){
		if(dev != null){
			ArrayList<BLECharacteristic> list = dev.getAdapter().getConn().getCharacteristics();
			for(BLECharacteristic chars : list){
				dev.getProp().add(new Property(chars.getName(), chars.getDomain(), "", chars.getTypeClass().getName(),""));
			}
			return true;
		}else{
			return false;
		}
	}    
	
	public static void main (String[] args) throws InterruptedException, MqttException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		System.out.println("----- Starting gateway initialization... -----");
    	if(args.length > 0 && args[0] != ""){
    		SERVER_IP = args[0];
    	}
    	System.out.println(SERVER_IP);
		int id_cont = 0;
		ArrayList<BLEDevice> allBLEdev = new ArrayList<BLEDevice>();
		//MqttPub publisher = new MqttPub("tcp://" + SERVER_IP + ":1883", TOPIC_GW, ""+(++id_cont));
		BLEDevice arduinodev = new BLEDevice("d2a36a22-acb8-11e6-80f5-76304dec7eb7", 
				"1","Genuino101", "98:4F:EE:0F:CF:6F");
		Genuino101Adapter adapter = new Genuino101Adapter();	
		//RegistryAdapter registry = new RegistryAdapter("localhost");
		GatewayCore core = new GatewayCore();
		arduinodev.setAdapter(adapter);
		//allBLEdev.add(arduinodev);
		for(BLEDevice d : allBLEdev){
			if(core.checkDev_in_Registry(d.getUUID())){
				core.connect_to_BLE_dev(d);
				System.out.println("Device " + d.getType()+ " with UUID: " + d.getUUID() + " allowed");
				core.getBLEDevices().add(d);
				if(!populateProperties(d)) System.out.println("Error: cannot bind properties to adapter's characteristics");
			}else{
				System.out.println("Error: cannot authenticate dev: " + d.getType() + " with UUID " + d.getUUID());
			}
		}
		//populateProperties(arduinodev);
		
		System.out.println("----- TRYING TO WRITE . -----");
		String s="";
		for(BLEDevice d : core.getBLEDevices()){
			core.set_BLE_val_from_dev(d, s);
		}
		/*while(true){
			
			
			for(BLEDevice d : core.getBLEDevices()){
				core.get_BLE_val_from_dev(d);
				for(Property p : d.getProp()){
					publisher.publish(TOPIC + d.getUUID() + "/" + p.getDomain() + "/" + p.getName(), 
							p.printMsg() + "_" + GATEWAY_UUID);
				}
				Thread.sleep(2000);
			}*/
		}
	}
		
	



