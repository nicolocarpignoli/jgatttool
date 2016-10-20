import static org.junit.Assert.*;

import org.junit.Test;

import model.BLECharacteristic;
import model.BLEService;
import util.GattParser;

public class GattParserTest {

	@Test
	public void parseServiceTest() {
		BLEService service = new BLEService("00001800-0000-1000-8000-00805f9b34fb", "0x0007");
		BLEService service2 = GattParser.parseService("attr handle = 0x0001, end grp handle = 0x0007 uuid: 00001800-0000-1000-8000-00805f9b34fb");
		assertEquals(service.getHnd(), service2.getHnd());
		assertEquals(service.getUUID(), service2.getUUID());
	}
	
	@Test
	public void parseCharacteristicTest(){
		BLECharacteristic chars = new BLECharacteristic(null, "00002a00-0000-1000-8000-00805f9b34fb", "0x0003",false,false,false);
		BLECharacteristic chars2 = GattParser.parseCharacteristic(("handle = 0x0002, char properties = 0x02, char value handle = 0x0003, "
						+ "uuid = 00002a00-0000-1000-8000-00805f9b34fb"));
		assertEquals(chars.getHnd(), chars2.getHnd());
		assertEquals(chars.getIsreadable(), chars2.getIsreadable());
		assertEquals(chars.getIswritable(), chars2.getIswritable());
		assertEquals(chars.getUUID(), chars2.getUUID());
		assertEquals(chars.getValue(), chars2.getValue());
	}
	
	@Test
	public void cleanStringTest(){
		String str = GattParser.cleanString("char : 00 90 00 00");
		assertEquals("00900000", str);
	}
	
	@Test
	public void gattForReadTest(){
		long n = 0;
		long s = (long)GattParser.convertGattForRead("long", "Characteristic value/descriptor: 00 00 00 00");
		assertEquals(n, s);
	}
	
	@Test
	public void gattForWriteTest(){
		String str = (long)0 + "";
		String str2 =  GattParser.convertGattForWrite("long",(long)0);
		assertEquals(str, str2);
	}

}
