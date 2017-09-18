package main;

import java.util.Arrays;

import model.BLECharacteristic;
import model.BLEConnection;



public class Genuino101Adapter {
	public final int CONN_TIMEOUT = 10;	// attempts n.
	//public final int NULL_TIMEOUT = 3;	// attempts n.
	//public final int CONN_READ = 3000;
	//public final int CONN_WRITE = 3000;
	private int tcont = 0;
	public BLEConnection conn;
	
	public void populateChars() throws InterruptedException{
		while(!conn.populateCharacteristics()){
			System.out.println("Number of attempts left to connect to BLE dev: " + (CONN_TIMEOUT - ++tcont));
			if(tcont == CONN_TIMEOUT){
				System.out.println("BLE dev not connected. Timeout reached.");
				// TODO handle failure - exit behaviour only in prototype
				System.exit(1);
			}
		}
		tcont = 0;
	}
	
	public void init(String mac) throws InterruptedException{
		conn = new BLEConnection(mac);
		conn.getCharacteristics().add(new BLECharacteristic<Long>("stepcount", "data",0,long.class,"19b10011-e8f2-537e-4f6c-d104768a1214", "", true, false, false));	// stepcount
		conn.getCharacteristics().add(new BLECharacteristic<Float>("ax", "data",0f,float.class,"19b10012-e8f2-537e-4f6c-d104768a1214", "", true, false, true)); // ax
		conn.getCharacteristics().add(new BLECharacteristic<Float>("ay", "data",0f,float.class,"19b10013-e8f2-537e-4f6c-d104768a1214", "", true, false, true)); // ay
		conn.getCharacteristics().add(new BLECharacteristic<Float>("az", "data",0f,float.class,"19b10014-e8f2-537e-4f6c-d104768a1214", "", true, false, true)); // az
		conn.getCharacteristics().add(new BLECharacteristic<Float>("gx", "data",0f,float.class,"19b10015-e8f2-537e-4f6c-d104768a1214", "", true, false, true)); // gx
		conn.getCharacteristics().add(new BLECharacteristic<Float>("gy", "data",0f,float.class,"19b10016-e8f2-537e-4f6c-d104768a1214", "", true, false, true)); // gy
		conn.getCharacteristics().add(new BLECharacteristic<Float>("gz", "data",0f,float.class,"19b10017-e8f2-537e-4f6c-d104768a1214", "", true, false, true)); // gz
		conn.getCharacteristics().add(new BLECharacteristic<Integer>("shock","data", 0,int.class,"19b10018-e8f2-537e-4f6c-d104768a1214", "", true, false, true)); // shock
		conn.getCharacteristics().add(new BLECharacteristic<Integer>("mode", "manage",0,int.class,"19b10019-e8f2-537e-4f6c-d104768a1214", "", true, true, false)); // mode
		conn.getCharacteristics().add(new BLECharacteristic<Integer> ("srange", "manage",0,int.class,"19b10020-e8f2-537e-4f6c-d104768a1214", "", true, true, false)); // sran
		conn.getCharacteristics().add(new BLECharacteristic<Integer> ("arange", "manage",0,int.class,"19b10021-e8f2-537e-4f6c-d104768a1214", "", true, true, false)); // arange
		conn.getCharacteristics().add(new BLECharacteristic<Integer> ("grange", "manage",0,int.class,"9b100122-e8f2-537e-4f6c-d104768a1214", "", true, true, false)); // gran
		//CalibCharacteristic deleted: authCharacteristics added
		conn.getCharacteristics().add(new BLECharacteristic<Long>("auth","data",0, long.class,"9B100124-E8F2-537E-4F6C-D104768A1214","",true,true,false));
		
	}
	
	public Object read_generic(int index) throws InterruptedException{
		Object newval = conn.read_data(conn.getCharacteristics().get(index));
		if(newval != null){
			conn.getCharacteristics().get(index).setValue(newval);
			conn.getCharacteristics().get(index).setOn(true);
			return newval;
		}else{
			System.out.println("Error in read. Output is last known value");
			conn.getCharacteristics().get(index).setOn(false);
			return conn.getCharacteristics().get(index).getValue();
		}
	}
	
	public void write_generic(int index, Object val) throws InterruptedException{
		conn.write_data(conn.getCharacteristics().get(index), val, false);
	}
	
	public long readStepcount() throws InterruptedException{
		return (long)read_generic(0);
	}
	public float readAx() throws InterruptedException{
		return (float)read_generic(1);
	}
	public float readAy() throws InterruptedException{
		return (float)read_generic(2);
	}
	public float readAz() throws InterruptedException{
		return (float)read_generic(3);
	}
	public float readGx() throws InterruptedException{
		return (float)read_generic(4);
	}
	public float readGy() throws InterruptedException{
		return (float)read_generic(5);
	}
	public float readGz() throws InterruptedException{
		return (float)read_generic(6);
	}
	public int readShock() throws InterruptedException{
		return (int)read_generic(7);
	}
	
	
	public long readAuth() throws InterruptedException{
		return (long)read_generic(12);
	}
	
	
	public void writeAuth(long myAuth) throws InterruptedException{
		write_generic(12,myAuth);
	}
	
	/*
	public int readMode() throws InterruptedException{
		return (int)read_generic(8);
	}
	public void writeMode(int mode) throws InterruptedException{
		write_generic(8,mode);
	}
	public int readSrange() throws InterruptedException{
		return (int)read_generic(9);
	}
	public void writeSrange(int srange) throws InterruptedException{
		write_generic(9,srange);
	}
	public int readArange() throws InterruptedException{
		return (int)read_generic(10);
	}
	public void writeArange(int arange) throws InterruptedException{
		write_generic(10,arange);
	}
	public int readGrange() throws InterruptedException{
		return (int)read_generic(11);
	}
	public void writeGrange(int grange) throws InterruptedException{
		write_generic(11,grange);
	}
	public int readCalib() throws InterruptedException{
		return (int)read_generic(12);
	}
	public void writeCalib(int calib) throws InterruptedException{
		write_generic(12,calib);
	}
	*/
	
	
	public BLEConnection getConn() {
		return conn;
	}

	public void setConn(BLEConnection conn) {
		this.conn = conn;
	}
	
	
}
