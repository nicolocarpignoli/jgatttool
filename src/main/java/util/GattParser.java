package util;

import model.BLECharacteristic;
import model.BLEService;

/*
 * A set of methods for converting gatttool data
 *
 */

public class GattParser {

	public static BLEService parseService(String inputString) {
		String delims = " uuid: ";
		String[] tokens = inputString.split(delims);
		String uuid = tokens[1];
		delims = "end grp handle = ";
		String tok = tokens[0];
		tokens = tok.split(delims);
		return new BLEService(uuid, tokens[1]);
	}

	public static BLECharacteristic parseCharacteristic(String inputString) {
		String delims = ", uuid = ";
		String[] tokens = inputString.split(delims);
		String uuid = tokens[1];
		delims = "char value handle = ";
		String tok = tokens[0];
		tokens = tok.split(delims);
		String hnd = tokens[1];
		return new BLECharacteristic(0, null, uuid, hnd, false, false, false);
	}

	// give an hexa string as output from gatttool raw string
	public static String cleanString(String inputString) {
		String delims = ": ";
		String[] tokens = inputString.split(delims);
		String out = tokens[1];
		return out.replaceAll(" ", "");
	}

	public static Object convertGattForRead(String data_type, String value) {
		// TODO implement parsing of all types
		value = cleanString(value);
		if (data_type.equals("int"))
			return DataParser.hexaStringToInteger(value);
		if (data_type.equals("long"))
			return DataParser.hexaStringToLong(value);
		if (data_type.equals("float"))
			return DataParser.hexaStringToFloat(value);
		return value;
	}

	public static String convertGattForWrite(String data_type, Object value) {
		try {
			// TODO implement parsing of all types
			if (data_type.equals("int"))
				return DataParser.IntegerToHexaString((int) value);
			if (data_type.equals("long"))
				return DataParser.LongToHexaString((long) value);
			if (data_type.equals("float"))
				return DataParser.FloatToHexaString((float) value);
		} catch (ClassCastException e) {
			System.out.println("Error in conversion, check input data type");
		}
		return "";
	}

}
