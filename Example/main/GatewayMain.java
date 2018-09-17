
package main;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import javamodel.BLEDevice;
import javamodel.Property;

import org.eclipse.paho.client.mqttv3.MqttException;

import model.BLECharacteristic;

public class GatewayMain {
	private final static String GATEWAY_UUID = "yourGWId";
	private final static String TOPIC_GW = "corp/field/" + GATEWAY_UUID + "/status";
	private final static String TOPIC = "corp/field/";
	private final static String DEF_PSW = "psw";
	private static String SERVER_IP = "yourIp";

	public static boolean populateProperties(BLEDevice dev) {
		if (dev != null) {
			ArrayList<BLECharacteristic> list = dev.getAdapter().getConn().getCharacteristics();
			for (BLECharacteristic chars : list) {
				dev.getProp()
						.add(new Property(chars.getName(), chars.getDomain(), "", chars.getTypeClass().getName(), ""));
			}
			return true;
		} else {
			return false;
		}
	}

	public static void main(String[] args) throws InterruptedException, MqttException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		System.out.println("----- Starting gateway initialization... -----");
		if (args.length > 0 && args[0] != "") {
			SERVER_IP = args[0];
		}
		System.out.println(SERVER_IP);
		int id_cont = 0;
		ArrayList<BLEDevice> allBLEdev = new ArrayList<BLEDevice>();
		BLEDevice arduinodev = new BLEDevice("yourArduinoUUID", "1", "Genuino101", "yourMacAddr");
		Genuino101Adapter adapter = new Genuino101Adapter();
		GatewayCore core = new GatewayCore();
		arduinodev.setAdapter(adapter);
		for (BLEDevice d : allBLEdev) {
			if (core.checkDev_in_Registry(d.getUUID())) {
				core.connect_to_BLE_dev(d);
				System.out.println("Device " + d.getType() + " with UUID: " + d.getUUID() + " allowed");
				core.getBLEDevices().add(d);
				if (!populateProperties(d))
					System.out.println("Error: cannot bind properties to adapter's characteristics");
			} else {
				System.out.println("Error: cannot authenticate dev: " + d.getType() + " with UUID " + d.getUUID());
			}
		}
		String s = "";
		for (BLEDevice d : core.getBLEDevices()) {
			core.set_BLE_val_from_dev(d, s);
		}
	}
}
