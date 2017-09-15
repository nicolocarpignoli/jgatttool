package main;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import org.eclipse.paho.client.mqttv3.MqttException;
import model.BLECharacteristic;
import model.BLEDevice;
import model.Property;

public class GatewayMain {
	private final static String GATEWAY_UUID = "yourGWId";
	private final static String TOPIC_GW = "corp/field/" + GATEWAY_UUID + "/status";
	private final static String TOPIC = "corp/field";
	private final static String DEF_PSW = "yourpsw";
	private static String SERVER_IP = "yourip";
	
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
		int id_cont = 0;
		ArrayList<BLEDevice> allBLEdev = new ArrayList<BLEDevice>();
		MqttPub publisher = new MqttPub("tcp://" + SERVER_IP + ":1883", TOPIC_GW, ""+(++id_cont));
		BLEDevice arduinodev = new BLEDevice("d2a36a22-acb8-11e6-80f5-76304dec7eb7", "1","Genuino101", "98:4F:EE:0F:CF:6F");
		Genuino101Adapter adapter = new Genuino101Adapter();	
		RegistryAdapter registry = new RegistryAdapter("localhost");
		GatewayCore core = new GatewayCore(registry, DEF_PSW);
		arduinodev.setAdapter(adapter);
		allBLEdev.add(arduinodev);
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
		
		System.out.println("----- Starting gateway loop... -----");
		while(true){
			for(BLEDevice d : core.getBLEDevices()){
				core.get_BLE_val_from_dev(d);
				for(Property p : d.getProp()){
					publisher.publish(TOPIC + d.getUUID() + "/" + p.getDomain() + "/" + p.getName(), 
							p.printMsg() + "_" + GATEWAY_UUID);
				}
				Thread.sleep(2000);
			}
		}
	}
		
	

}
