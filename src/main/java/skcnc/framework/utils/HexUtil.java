package skcnc.framework.utils;

import java.io.UnsupportedEncodingException;

public class HexUtil {
    /**
     * @Method Name : byteToHexString
     * @description : byte를 hext 문자열로 변환 16진수 사용으로 16미만인 경우 앞에 '0'를 붙이자.
     * @param b
     * @return
     */
    public static String byteToHexString(byte b) {
        int n = b & 0xFF;
        String result = ((n < 16) ? "0" : "") + Integer.toHexString(n);
        return result.toUpperCase();
    }

    /**
     * @Method Name : shortToHexString
     * @description :
     * @param s
     * @return
     */
    public static String shortToHexString(short s) {
        int n = s & 0xFFFF;
        String result = ((n < 4096) ? "0" : "") + ((n < 256) ? "0" : "") + ((n < 16) ? "0" : "")
                + Integer.toHexString(s);
        return result.toUpperCase();
    }

    /**
     * @Method Name : intToHexString
     * @description :
     * @param n
     * @return
     */
    public static String intToHexString(int n) {
        String result = ((n < 268435456) ? "0" : "") + ((n < 16777216) ? "0" : "") + ((n < 1048576) ? "0" : "")
                + ((n < 65536) ? "0" : "") + ((n < 4096) ? "0" : "") + ((n < 256) ? "0" : "") + ((n < 16) ? "0" : "")
                + Integer.toHexString(n);
        return result.toUpperCase();
    }

    /**
     * @Method Name : bytesToHexString
     * @description :
     * @param text
     * @return
     */
    public static String bytesToHexString(byte[] text) {
        return bytesToHexString(text, 0, text.length);
    }

    /**
     * @Method Name : toHexString
     * @description :
     * @param text
     * @return
     */
    public static String toHexString(byte[] text) {
        return bytesToHexString(text, 0, text.length);
    }

    /**
     * @Method Name : bytesToHexString
     * @description :
     * @param text
     * @param offset
     * @param length
     * @return
     */
    public static String bytesToHexString(byte[] text, int offset, int length) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < length; i++)
            buf.append(byteToHexString(text[offset + i]));
        return buf.toString();
    }

    /**
     * @Method Name : hexStringToByte
     * @description :
     * @param text
     * @return
     * @throws NumberFormatException
     */
    public static byte hexStringToByte(String text) throws NumberFormatException {
        byte[] bytes = hexStringToBytes(text);
        if (bytes == null || bytes.length != 1)
            throw new NumberFormatException();
        return bytes[0];
    }

    /**
     * @Method Name : hexStringToShort
     * @description :
     * @param text
     * @return
     * @throws NumberFormatException
     */
    public static short hexStringToShort(String text) throws NumberFormatException {
        byte[] bytes = hexStringToBytes(text);
        if (bytes == null || bytes.length != 2)
            throw new NumberFormatException();
        return (short) ((bytes[0] & 0xFF) << 8 | bytes[1] & 0xFF);
    }

    /**
     * @Method Name : hexStringToInt
     * @description :
     * @param text
     * @return
     * @throws NumberFormatException
     */
    public static int hexStringToInt(String text) throws NumberFormatException {
        byte[] bytes = hexStringToBytes(text);
        if (bytes == null || bytes.length != 4)
            throw new NumberFormatException();
        return (bytes[0] & 0xFF) << 24 | (bytes[1] & 0xFF) << 16 | (bytes[2] & 0xFF) << 8 | bytes[3] & 0xFF;
    }

    /**
     * @Method Name : hexStringToBytes
     * @description :
     * @param text
     * @return
     * @throws NumberFormatException
     */
    public static byte[] hexStringToBytes(String text) throws NumberFormatException {
        if (text == null)
            return null;

        StringBuffer hexText = new StringBuffer();

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (!Character.isWhitespace(c)) {
                if ("0123456789abcdefABCDEF".indexOf(c) < 0)
                    throw new NumberFormatException();
                hexText.append(c);
            }
        }

        if (hexText.length() % 2 != 0)
            hexText.insert(0, "0");

        byte[] result = new byte[hexText.length() / 2];

        for (int j = 0; j < hexText.length(); j += 2) {
            int hi = hexDigitToInt(hexText.charAt(j));
            int lo = hexDigitToInt(hexText.charAt(j + 1));
            result[j / 2] = (byte) ((hi & 0xFF) << 4 | lo & 0xFF);
        }
        return result;
    }

    /**
     * @Method Name : hexStringToByteArray
     * @description : String 으로 되어 있는 Hex 문자를 Byte배열로 변환 리턴
     *                예) FF11 => { 0xFF, 0x11 }
     * @param text
     * @return
     */
    public static byte[] hexStringToByteArray(String text) {
        int len = text.length();
        byte[] data = new byte[len/2];
        for ( int i=0;i<len; i+=2) {
            data[i/2] = (byte)((Character.digit(text.charAt(i), 16) << 4) + Character.digit(text.charAt(+1), 16 ));
        }
        return data;
    }

    /**
     * @Method Name : byteArrayToHexString
     * @description : byte배열을 입력받아 String Hex로 변환 리턴
     *                예) { 0xFF, 0x11 } => FF11
     * @param bytes
     * @return
     */
    public static String byteArrayToHexString( byte[] bytes ) {
        StringBuilder sb = new StringBuilder();
        for( byte b:bytes) {
            sb.append( String.format("%02X", b&0xff) );
        }
        return sb.toString();
    }

    /**
     * @Method Name : hexDigitToInt
     * @description :
     * @param c
     * @return
     * @throws NumberFormatException
     */
    static int hexDigitToInt(char c) throws NumberFormatException {
        switch (c) {
            case '0':
                return 0;
            case '1':
                return 1;
            case '2':
                return 2;
            case '3':
                return 3;
            case '4':
                return 4;
            case '5':
                return 5;
            case '6':
                return 6;
            case '7':
                return 7;
            case '8':
                return 8;
            case '9':
                return 9;
            case 'A':
            case 'a':
                return 10;
            case 'B':
            case 'b':
                return 11;
            case 'C':
            case 'c':
                return 12;
            case 'D':
            case 'd':
                return 13;
            case 'E':
            case 'e':
                return 14;
            case 'F':
            case 'f':
                return 15;
        }
        throw new NumberFormatException();
    }

    /**
     * @Method Name : pad
     * @description :
     * @param txt
     * @param width
     * @param padChar
     * @param left
     * @return
     */
    private static String pad(String txt, int width, char padChar, boolean left) {

        StringBuffer result = new StringBuffer();
        int iLen = getEucKrLen(txt);

        //for (int i = 0; i < width - txt.length(); i++)
        for (int i = 0; i < width - iLen; i++)
            result.append(padChar);

        if (left) {
            result.append(txt);
        } else {
            result.insert(0, txt);
        }

        return result.toString();
    }


    /**
     * @Method Name : getEucKrLen
     * @description : utf8 로 입력된 String을 받아 euckr형태의 byte 길이를 리턴
     * @param txt
     * @return
     */
    public static int getEucKrLen(String txt) {

        int iLen = 0;
        String sTmp = "";

        if ( txt == null || txt.isEmpty() )
            return 0;

        try {
            byte[] tmp = txt.getBytes( "euc-kr" );
            iLen = tmp.length;
        } catch ( UnsupportedEncodingException e ) {
            for( int i=0; i<txt.length(); i++ ) {
                sTmp = txt.substring(i, i+1);

                if ( sTmp.getBytes().length > 1 )
                    iLen += 2; //특수문자 utf8 3byte euckr 2byte
                else
                    iLen ++;
            }
        }

        return iLen;
    }

    /**
     * @Method Name : bytesToSpacedHexString
     * @description : byte를 받아서 hex로 변환해 String으로 리턴
     * @param data
     * @return
     */
    public static String bytesToSpacedHexString(byte[] data) {
        String result = "";
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            buf.append(byteToHexString(data[i]));
            //다음 바이트 사이에 스페이스 추가. - 전문 데이타 확인용 같음.
            buf.append((i < data.length - 1) ? " " : "");
        }
        result = buf.toString().toUpperCase();
        return result;
    }

    /**
     * @Method Name : bytesToSpacedHexStrings
     * @description : log출력을 위한 함수
     *                byte를 받아 정해진 길이만큼 split 한 후  hex 값 형태로 string 변환 리턴
     * @param data     hex String 변환한 값
     * @param columns  split 형태로 자를 길이
     * @param padWidth 리턴할 배열의 값을 정해진 길이만큼 padding 할 길이.
     * @return
     */
    private static String[] bytesToSpacedHexStrings(byte[] data, int columns, int padWidth) {
        byte[][] src = split(data, columns);
        String[] result = new String[src.length];
        for (int j = 0; j < src.length; j++) {
            result[j] = bytesToSpacedHexString(src[j]);
            result[j] = pad(result[j], padWidth, ' ', false);
        }
        return result;
    }

    /**
     * @Method Name : bytesToASCIIString
     * @description : 로그 출력용 같음.
     * @param data
     * @return
     */
    public static String bytesToASCIIString(byte[] data) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            char c = (char) data[i];
            buf.append(Character.toString(
                    (" .,:;'`\"<>()[]{}?/\\!@#$%^&*_-=+|~0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
                            .indexOf(c) >= 0) ? c : 46)); //46은 대문자 F??
        }
        return buf.toString();
    }

    /**
     * @Method Name : bytesToASCIIStrings
     * @description :
     * @param data
     * @param columns
     * @param padWidth
     * @return
     */
    static String[] bytesToASCIIStrings(byte[] data, int columns, int padWidth) {
        byte[][] src = split(data, columns);
        String[] result = new String[src.length];
        for (int j = 0; j < src.length; j++)
            result[j] = bytesToASCIIString(src[j]);
        return result;
    }

    /**
     * @Method Name : split
     * @description : 입력받은 byte 배열을 width 만큼씩 나눠 배열로 리턴
     * @param src
     * @param width
     * @return
     */
    public static byte[][] split(byte[] src, int width) {

        int rows = src.length / width;
        int rest = src.length % width;

        byte[][] dest = new byte[rows + ((rest > 0) ? 1 : 0)][];
        int k = 0;

        for (int j = 0; j < rows; j++) {
            dest[j] = new byte[width];
            System.arraycopy(src, k, dest[j], 0, width);
            k += width;
        }

        if (rest > 0) {
            dest[rows] = new byte[rest];
            System.arraycopy(src, k, dest[rows], 0, rest);
        }

        return dest;
    }

    /**
     * @Method Name : bytesToPrettyString
     * @description :
     * @param data
     * @return
     */
    public static String bytesToPrettyString(byte[] data) {
        return bytesToPrettyString(data, 16, true, 4, null, true);
    }

    /**
     * @Method Name : bytesToPrettyString
     * @description : 로그 출력용 hex와 Asc 문자 비교해 표시.
     * @param data
     * @param columns
     * @param useIndex
     * @param indexPadWidth
     * @param altIndex
     * @param useASCII
     * @return
     */
    public static String bytesToPrettyString(byte[] data, int columns, boolean useIndex, int indexPadWidth,
                                             String altIndex, boolean useASCII) {

        String result = "";
        StringBuffer buf = new StringBuffer();
        String[] hexStrings = bytesToSpacedHexStrings(data, columns, 3 * columns);
        String[] asciiStrings = bytesToASCIIStrings(data, columns, columns);

        for (int j = 0; j < hexStrings.length; j++) {
            if (useIndex) {
                String prefix = Integer.toHexString(j * columns).toUpperCase();
                buf.append(pad(prefix, indexPadWidth, '0', true)).append(": ");
            } else {
                String prefix = (j == 0) ? altIndex : "";
                buf.append(pad(prefix, indexPadWidth, ' ', true)).append(" ");
            }
            buf.append(hexStrings[j]);
            if (useASCII)
                buf.append(" ").append(asciiStrings[j]);
            buf.append("\n");
        }
        result = buf.toString();
        return result;
    }

    /**
     * @Method Name : byteToBinarySting
     * @description :
     * @param n
     * @return
     */
    public static String byteToBinarySting(byte n) {
        StringBuilder sb = new StringBuilder( "00000000" );
        for ( int bit =0; bit<8; bit++ ) {
            if ((( n>> bit) & 1) > 0 ) {
                sb.setCharAt(7-bit, '1');
            }
        }
        return sb.toString();
    }
}
