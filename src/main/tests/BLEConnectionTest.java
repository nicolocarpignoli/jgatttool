

import static org.junit.Assert.*;

import java.util.Scanner;

import org.junit.Before;

import model.BLECharacteristic;
import model.BLEConnection;

import org.junit.Test;

public class BLEConnectionTest {
	BLEConnection conn;
	
	@Before
	public void setUp(){
		conn = new BLEConnection("");
	}

	@Test
	public void reachedTimeoutTest() {
		long scad = 0;
		long timeout = 1;
		boolean bool = conn.reachedTimeout(1,1);
		assertEquals(true,bool);
	}
	
	@Test
	public void executeCmdTest(){
		// test for italian lang. windows version only
		Process process = conn.executeCmd("ipconfig");
		Scanner scanner = new Scanner(process.getInputStream());
		String str = scanner.next();
		assertEquals("Configurazione",str);
	}
	

	@Test
	public void read_dataTest() throws InterruptedException{
		BLECharacteristic chars = new BLECharacteristic(int.class, "", "", true, false, false);
		chars.setValue(0);
		conn.read_data(chars);
		assertEquals(chars.getValue(), 0);
		chars.setIsreadable(false);
		assertEquals(chars.getValue(), 0);
	}
	
	@Test
	public void write_dataTest() throws InterruptedException{
		BLECharacteristic chars = new BLECharacteristic(int.class, "", "", false, true, false);
		chars.setValue(0);
		conn.write_data(chars, 1, false);
		assertNotEquals(1,chars.getValue());
	}
	

}
