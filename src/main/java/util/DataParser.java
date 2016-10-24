package util;

/*
 * A set of methods for converting data types in different format
 * 
 */

public class DataParser {
	
	public static String IntegerToHexaString(int value) {
		String str = Integer.toHexString(value);
		return reverseEndianHex(str);
	}

	public static String FloatToHexaString(float value) {
		String str = "" + Integer.toHexString(Float.floatToIntBits(value));
		return reverseEndianHex(str);
	}

	public static String LongToHexaString(long value) {
		String str = Long.toHexString(value);
		return reverseEndianHex(str);
	}
	
	 public static long hexaStringToLong(String input){
		input = reverseEndianHex(input);
		return Long.parseLong(input, 16);
    }
	
	public static int hexaStringToInteger(String input){
		input = reverseEndianHex(input);
		return Integer.parseInt(input, 16);
    }
	
	public static float hexaStringToFloat(String input){
		input = reverseEndianHex(input);
		Long str = Long.parseLong(input,16);
		return Float.intBitsToFloat(str.intValue());
	}
	
	public static String reverseEndianHex(String originalHex) {
		 if(originalHex.length() % 2 != 0){
			 originalHex = "0" + originalHex;
		 }
		 if(originalHex.length() < 2) return originalHex;
			int lengthInBytes = originalHex.length() / 2;
		    char[] chars = new char[lengthInBytes * 2];
		    for (int index = 0; index < lengthInBytes; index++) {
		        int reversedIndex = lengthInBytes - 1 - index;
		        chars[reversedIndex * 2] = originalHex.charAt(index * 2);
		        chars[reversedIndex * 2 + 1] = originalHex.charAt(index * 2 + 1);
		    }
		    return new String(chars);
	}

	
}
