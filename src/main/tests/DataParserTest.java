import static org.junit.Assert.*;

import org.junit.Test;

import util.DataParser;

public class DataParserTest {

	@Test
	public void testhexaStringToLongTest() {
		long val = DataParser.hexaStringToLong("00110000");
		assertEquals(4352, val );
	}
	
	@Test
	public void hexaStringToIntegerTest() {
		int val = DataParser.hexaStringToInteger("16000000");
		assertEquals(22, val );
	}
	
	@Test
	public void  hexaStringToFloatTest() {
		float val = DataParser.hexaStringToFloat("00d8563f");
		assertEquals(0.83,val , 0.1);
		val = DataParser.hexaStringToFloat("20f6dec2");
		assertEquals(-111.4, val, 0.8);
	}
	
	@Test
	public void reverseEndianHexTest(){
		String str = DataParser.reverseEndianHex("70000008");
		assertEquals("08000070", str);
	}
	
	@Test
	public void integerToHexaStringTest(){
		int value = 202;
		String str = DataParser.IntegerToHexaString(value);
		assertEquals("ca",str);
	}
	
	@Test
	public void floatToHexaStringTest(){
		float value = new Float(-113.45);
		String str = DataParser.FloatToHexaString(value);
		assertEquals("66e6e2c2", str);
	}
	
	@Test
	public void longToHexaStringTest(){
		long value = 15000;
		String str = DataParser.LongToHexaString(value);
		assertEquals("983a", str);
	}

}