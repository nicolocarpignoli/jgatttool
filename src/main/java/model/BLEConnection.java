package model;


import java.util.Scanner;

import util.GattParser;

import java.io.IOException;
import java.util.ArrayList;

public class BLEConnection {
	public final String initString = "gatttool -b ";
	private ArrayList<BLEService> services = new ArrayList<BLEService>();
	private ArrayList<BLECharacteristic> characteristics= new ArrayList<BLECharacteristic>();
	private String macaddr;

	
	public BLEConnection(String mac){
		macaddr = mac;
	}	
	
	
	public ArrayList<BLEService> getServices() {
		return services;
	}


	public void setServices(ArrayList<BLEService> services) {
		this.services = services;
	}


	public ArrayList<BLECharacteristic> getCharacteristics() {
		return characteristics;
	}


	public void setCharacteristics(ArrayList<BLECharacteristic> characteristics) {
		this.characteristics = characteristics;
	}


	public String getMacaddr() {
		return macaddr;
	}


	public void setMacaddr(String macaddr) {
		this.macaddr = macaddr;
	}


	public boolean reachedTimeout(long scad, long timeout){
		//TODO implement timeout management
		long now = System.currentTimeMillis() + timeout;
		return now >= scad;
	}
	

	public Process executeCmd(String cmd){
		Process process = null;
		try {
			process = Runtime.getRuntime().exec(cmd);
		} catch (IOException e) {
			System.out.println("Error, cannot execute prompt command");
		}
		return process;
		
	}	

	public void populateServices() throws InterruptedException{
		Process process = executeCmd(initString + macaddr + " --primary");
		Scanner scanner = new Scanner(process.getInputStream());
		while(scanner != null && scanner.hasNext()){
			services.add(GattParser.parseService(scanner.nextLine()));
		}
		process.waitFor();
		process.destroy();
		scanner.close();
	}
	
	public void populateCharacteristics() throws InterruptedException{
		Process process = executeCmd(initString + macaddr + " --characteristics");
		Scanner scanner = new Scanner(process.getInputStream());
		boolean found = false;
		while(scanner != null && scanner.hasNext()){
			BLECharacteristic chars = GattParser.parseCharacteristic(scanner.nextLine());
			for(BLECharacteristic c : characteristics){
				if(c.getUUID().equals(chars.getUUID())){
					c.setHnd(chars.getHnd());
					found = true;
				}
			}
			if (!found){
				// remove comment if wanted to discover unknown characteristics
				// characteristics.add(chars);
			}
		}
		process.waitFor();
		process.destroy();
		scanner.close();
	}
	
	public Object read_data(BLECharacteristic chars) throws InterruptedException{
		Process process = null;
		if(chars.getIsreadable()){
			Class type = chars.getTypeClass();
			process = executeCmd(initString + macaddr + " --char-read -a " + chars.getHnd());
			Scanner scanner = new Scanner(process.getInputStream());
			if(scanner != null  && scanner.hasNextLine()){						
				Object ret = GattParser.convertGattForRead(type.getName(),scanner.nextLine());
				process.waitFor();
				process.destroy();
				scanner.close();
				return ret;
			}
			System.out.println("Error in read, permission denied");
		}
		return null;
	}
	

	public void write_data(BLECharacteristic chars, Object value, boolean listening ) throws InterruptedException{
		//TODO handle notifications
		Process process = null;
		if(chars.getIswritable()){
			if(listening && !chars.getIsnotify()){
				System.out.println("Error in write, permission denied for notification on this characteristic");
				return;
			}
			if(chars.getTypeClass().getName().equals(value.getClass().getName())){
				System.out.println("Error in write. Wrong type");
				return;
			}	
			String type = chars.getTypeClass().getName();
			String val = GattParser.convertGattForWrite(type, value);
			process = executeCmd(initString + macaddr + " --char-write-req -a " + chars.getHnd() + " -n " + val);
			Scanner scanner = new Scanner(process.getInputStream());
			if(scanner != null){						
				process.waitFor();
				process.destroy();
				scanner.close();
				return;
			}
			System.out.println("Error in write, permission denied");
		}
	}

}
