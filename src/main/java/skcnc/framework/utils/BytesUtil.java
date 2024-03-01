package skcnc.framework.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BytesUtil {

	/**
	 * @Method Name : bytesToInt
	 * @description : 
	 * @date        : 2021. 2. 26.
	 * @author      : P21024
	 * @param bytes
	 * @return
	 */
	public static int bytesToInt(byte[] bytes) {
		if (bytes == null)
			return -1;
		int value = 0;
		for (int i = 0; i < bytes.length; i++)
			value |= (bytes[i] & 0xFF) << (bytes.length - i - 1) * 8;
		return value;
	}

	/**
	 * @Method Name : bytesToLong
	 * @description : 
	 * @date        : 2021. 3. 23.
	 * @author      : P21024
	 * @param bytes
	 * @return
	 */
	public static long bytesToLong(byte[] bytes) {
		if (bytes == null)
			return -1;
		long value = 0l;
		for (int i = 0; i < bytes.length; i++)
			value |= (long)(bytes[i] & 0xFF) << (bytes.length - i - 1) * 8;
		return value;
		//return ByteBuffer.wrap(bytes).getLong();
	}
	
	public static float bytesToFloa(byte[] bytes) {
		if (bytes == null)
			return -1;
		return ByteBuffer.wrap(bytes).getFloat();
	}
	
	public static double bytesToDouble(byte[] bytes) {
		if (bytes == null)
			return -1;
		return ByteBuffer.wrap(bytes).getDouble();
	}
	
	/**
	 * @Method Name : intToBytes
	 * @description : 
	 * @date        : 2021. 2. 26.
	 * @author      : P21024
	 * @param num
	 * @param length
	 * @return
	 */
	public static byte[] intToBytes(int num, int length) {
		if (num > Math.pow(2.0D, (length * 8)))
			return null;
		byte[] value = new byte[length];
		for (int i = 0; i < value.length; i++)
			value[i] = (byte) (num >> (length - i - 1) * 8 & 0xFF);
		return value;
	}
	
	public static byte[] longToBytes(long num, int length) {
		if (num > Math.pow(2.0D, (length * 8)))
			return null;
		byte[] value = new byte[length];
		for (int i = 0; i < value.length; i++)
			value[i] = (byte) (num >> (length - i - 1) * 8 & 0xFF);
		//ByteBuffer.wrap(value).putLong(num);
		return value;
	}
	
	public static byte[] floatToBytes(float num, int length) {
		byte[] value = new byte[length];
		ByteBuffer.wrap(value).putFloat(num);
		return value;
	}

	public static byte[] doubleToBytes(double num, int length) {
		byte[] value = new byte[length];
		ByteBuffer.wrap(value).putDouble(num);
		return value;
	}
	
	/**
	 * @Method Name : objectToBytes
	 * @description : 
	 * @date        : 2021. 2. 26.
	 * @author      : P21024
	 * @param obj
	 * @return
	 */
	public static byte[] objectToBytes(Object obj) throws Exception {
		ByteArrayOutputStream bos = null;
		ObjectOutputStream oos = null;
		byte[] bytes = null;
		try {
			bos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(bos);
			oos.writeObject(obj);
			oos.flush();
			bytes = bos.toByteArray();
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				if (oos != null)
					oos.close();
				if (bos != null)
					bos.close();
			} catch (IOException e) {
				//e.printStackTrace();
			}
		}
		return bytes;
	}

	/**
	 * @Method Name : bytesToObject
	 * @description : 
	 * @date        : 2021. 2. 26.
	 * @author      : P21024
	 * @param bytes
	 * @return
	 */
	public static Object bytesToObject(byte[] bytes) throws Exception {
		ByteArrayInputStream bis = null;
		ObjectInputStream ois = null;
		Object obj = null;
		try {
			bis = new ByteArrayInputStream(bytes);
			ois = new ObjectInputStream(bis);
			obj = ois.readObject();
		} catch (ClassNotFoundException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				if (ois != null)
					ois.close();
				if (bis != null)
					bis.close();
			} catch (IOException e) {
				//e.printStackTrace();
			}
		}
		return obj;
	}

	/**
	 * @Method Name : getFilledBytes
	 * @description : 
	 * @date        : 2021. 2. 26.
	 * @author      : P21024
	 * @param data
	 * @param length
	 * @return
	 */
	public static byte[] getFilledBytes(String data, int length) throws Exception {
		
		byte[] result = new byte[length];
		byte[] dataBytes = null;

		try {
			int len = HexUtil.getEucKrLen(data); 
			if (data == null) {
				dataBytes = new byte[0];
			} else if (len > length) {
				//dataBytes = data.substring(0, length).getBytes("euc-kr");
				dataBytes = data.getBytes("euc-kr");
			} else {
				dataBytes = data.getBytes("euc-kr");
			}
		} catch ( UnsupportedEncodingException e ) {
			throw e;
		} catch (IndexOutOfBoundsException e) {
			throw e;
		}
		System.arraycopy(dataBytes, 0, result, 0, (dataBytes.length < length) ? dataBytes.length : length);
		if (dataBytes.length < length)
			for (int i = dataBytes.length; i < length; i++)
				result[i] = 32;
		return result;
	}

	/**
	 * @Method Name : getFilledBytes
	 * @description : 
	 * @date        : 2021. 2. 26.
	 * @author      : P21024
	 * @param data
	 * @param length
	 * @return
	 */
	public static byte[] getFilledBytes(byte[] data, int length) {
		byte[] result = new byte[length];
		// byte[] dataBytes = null;
		if (data != null) {
			if (data.length > length) {
				System.arraycopy(data, 0, result, 0, length);
				return result;
			}
			System.arraycopy(data, 0, result, 0, data.length);
		}
		for (int i = result.length; i < length; i++)
			result[i] = 32;
		return result;
	}

	/**
	 * @Method Name : getFormattedDate
	 * @description : 
	 * @date        : 2021. 2. 26.
	 * @author      : P21024
	 * @param format
	 * @param day
	 * @return
	 */
	public static String getFormattedDate(String format, Date day) {
		if (day == null || format == null)
			return "";
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		String result = formatter.format(day);
		return result;
	}

	/**
	 * @Method Name : getHex
	 * @description : 
	 * @date        : 2021. 2. 26.
	 * @author      : P21024
	 * @param raw
	 * @return
	 */
	public static String getHex(byte[] raw) {
		if (raw == null)
			return null;
		StringBuffer hex = new StringBuffer(2 * raw.length);
		for (int x = 0; x < raw.length; x++)
			hex.append("0123456789ABCDEF".charAt((raw[x] & 0xF0) >> 4)).append("0123456789ABCDEF".charAt(raw[x] & 0xF));
		return hex.toString();
	}

	/**
	 * @Method Name : trimPath
	 * @description : 
	 * @date        : 2021. 2. 26.
	 * @author      : P21024
	 * @param path
	 * @return
	 */
	public static String trimPath(String path) {
		if (path != null)
			path = path.replace('\\', '/');
		return path;
	}

	/**
	 * @Method Name : getHostName
	 * @description : 
	 * @date        : 2021. 2. 26.
	 * @author      : P21024
	 * @return
	 */
	public static String getHostName() throws Exception {
		String hostName = "";
		try {
			hostName = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			throw e;
		}
		return hostName;
	}
	
	/**
	 * @Method Name : formatString
	 * @description : 
	 * @date        : 2021. 2. 26.
	 * @author      : P21024
	 * @param message
	 * @param length
	 * @param alignment
	 * @return
	 */
	public static String formatString(String message, int length, short alignment) {
		char blankChar = ' ';
		if (message == null)
			message = new String("");
		return new String(formatBytes(message.getBytes(), length, alignment, blankChar));
	}

	/**
	 * @Method Name : formatString
	 * @description : 
	 * @date        : 2021. 2. 26.
	 * @author      : P21024
	 * @param message
	 * @param length
	 * @param alignment
	 * @param blankChar
	 * @return
	 */
	public static String formatString(String message, int length, short alignment, char blankChar) {
		if (message == null)
			message = new String("");
		return new String(formatBytes(message.getBytes(), length, alignment, blankChar));
	}

	/**
	 * @Method Name : formatBytes
	 * @description : 
	 * @date        : 2021. 2. 26.
	 * @author      : P21024
	 * @param lenValue
	 * @param length
	 * @param alignment
	 * @param blankChar
	 * @return
	 */
	public static byte[] formatBytes(int lenValue, int length, short alignment, char blankChar) {
		return formatString("" + lenValue, length, alignment, blankChar).getBytes();
	}

	/**
	 * @Method Name : formatBytes
	 * @description : 
	 * @date        : 2021. 2. 26.
	 * @author      : P21024
	 * @param message
	 * @param length
	 * @param alignment
	 * @param blankChar
	 * @return
	 */
	public static byte[] formatBytes(byte[] message, int length, short alignment, char blankChar) {
		int inputLength = (message == null) ? 0 : message.length;
		int difference = length - inputLength;
		byte[] blankMessage = new byte[length];
		
		if ( message == null ) {
			for (int x = 0; x < length; x++) {
				blankMessage[x] = (byte) blankChar;
			}
		} else {
			for (int x = 0; x < length; x++) {
				if (alignment == 1) {
					if (x < inputLength) {
						blankMessage[x] = message[x];
					} else {
						blankMessage[x] = (byte) blankChar;
					}
				} else if (alignment == 2) {
					if (difference > 0 && x >= difference) {
						blankMessage[x] = message[x - difference];
					} else if (difference <= 0) {
						blankMessage[x] = message[x];
					} else {
						blankMessage[x] = (byte) blankChar;
					}
				}
			}
		}
		
		return blankMessage;
	}
	
	/**
	 * <pre>
	 * &#64;Method Name : hexToInt
	 * &#64;description : 
	 * &#64;date        : 2021. 2. 24.
	 * &#64;author      : P21024
	 * &#64;param hex
	 * &#64;return
	 * </pre>
	 */
	public static int hexToInt(String hex) {
		int intVal = Integer.parseInt(hex, 16);
		return intVal;
	}
	
	/**
	 * <pre>
	 * &#64;Method Name : hexToString
	 * &#64;description : 
	 * &#64;date        : 2021. 2. 24.
	 * &#64;author      : P21024
	 * &#64;param bytes
	 * &#64;return
	 * </pre>
	 */
	public static String hexToString(byte[] bytes) {
		char hexDigit[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			buf.append(hexDigit[(bytes[i] >> 4) & 0x0f]);
			buf.append(hexDigit[bytes[i] & 0x0f]);
		}
		return buf.toString();
	}
	
	/**
	 * <pre>
	 * &#64;Method Name : hexToInt
	 * &#64;description : 
	 * &#64;date        : 2021. 2. 24.
	 * &#64;author      : P21024
	 * &#64;param bytes
	 * &#64;return
	 * </pre>
	 */
	public static int hexToInt(byte[] bytes) {
		StringBuffer buf = new StringBuffer(bytes.length * 2);
		String hex;

		for (int i = 0; i < bytes.length; i++) {
			hex = "0" + Integer.toHexString(0xff & bytes[i]);
			buf.append(hex.substring(hex.length() - 2));
		}
		return Integer.parseInt(buf.toString(), 16);
	}
	
	/**
	 * @Method Name : intTohex
	 * @description : 
	 * @date        : 2021. 2. 26.
	 * @author      : P21024
	 * @param intVal
	 * @return
	 */
	public static String intTohex(int intVal) {
		String hex = Integer.toHexString(intVal);
		return hex;
	}
}
