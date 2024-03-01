package skcnc.framework.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    // 입력된 자리수를 넘을때 채워질 문자
    static final char OVERFLOW_CHAR = '#';
    // 콤마문자 변경 시 추가할 prefix 문자
    static final char PLUS_CHAR = '+';
    static final char ASTERISK_CHAR = '*';
    // 콤마문자 변경 시 추가할 prefix flag.
    static final int CNV_NOFLAG = 0;
    static final int CNV_PLUSSIGN = 2; // '+'
    static final int CNV_ASTERISK = 4; // '*'
    /*
     * enum CNV_FLAG { CNV_NOFLAG, CNV_PLUSSIGN, CNV_ASTERISSK };
     */

    /**
     * 문자열(str)에 공백 문자(‘ ‘, ‘\t’, ‘\n’, ‘\r’)가 포함되어 있는지를 검사한다.
     *
     * @param checkStr 검사할 문장
     * @return 공백여부(true / false)
     */
    public static boolean isSpace(String checkStr) {

        boolean chkInt = false;

        if (checkStr == null)
            return false;

        if (checkStr.contains(" ") || checkStr.contains("\t") || checkStr.contains("\n")
                || checkStr.contains("\r")) {
            chkInt = true;
        }

        return chkInt;
    }

    /**
     * 특정 길이(length)만큼의 문자열(str)이 공백 문자(‘ ‘, ‘\t’, ‘\n’, ‘\r’)를 검사한다.
     *
     * @param checkStr 검사할 문장
     * @param length   검사할 길이
     * @return 검사결과 리턴
     */
    public static boolean isSpace(String checkStr, int length) {
        if (checkStr == null || length < 1)
            return false;
        if (checkStr.length() <= length) {
            return isSpace(checkStr);
        } else {
            return isSpace(checkStr.substring(0, length));
        }
    }

    /**
     * 문자열(str)이 공백문자(' ')로만 구성되어있는 확인
     *
     * @param checkStr 검사할 문장
     * @param nLen     검사할 길이
     * @return 공백여부(true / false)
     */
    public static boolean isAllSpace(String checkStr, int nLen) {
        String pattern = "^[ ]*$";
        String subStr = checkStr.substring(0, nLen);

        if (subStr.matches(pattern)) {
            return true;
        }

        return false;
    }

    /**
     * 전체 문자열(str)이 비었는지 검사한다.(null 포함)
     *
     * @param checkStr 검사할 문장
     * @return 공백여부 리턴
     */
    public static boolean isEmpty(String checkStr) {

        boolean chkInt = false;

        if (checkStr == null)
            return true;

        if (checkStr.isEmpty()) {
            chkInt = true;
        }

        return chkInt;
    }

    /**
     * 전체 문자열(str)이 숫자인지를 검사한다.
     *
     * @param checkStr 검사할 문장
     * @return 숫자여부 리턴
     */
    public static boolean isDigit(String checkStr) {

        boolean chkInt = false;

        if (checkStr == null)
            return false;

        char strToChar[] = checkStr.toCharArray();

        for (int i = 0; i < strToChar.length; i++) {
            if (!Character.isDigit(strToChar[i])) {
                return chkInt;
            }
        }

        chkInt = true;

        return chkInt;
    }

    /**
     * 특정 길이(length)만큼의 문자열(str)이 숫자인지를 검사한다.
     *
     * @param checkStr 검사할 문장
     * @param length   검사할 길이
     * @return 숫자여부 리턴
     */
    public static boolean isDigit(String checkStr, int length) {

        boolean chkInt = false;

        if (checkStr == null || length < 1)
            return false;

        char strToChar[] = checkStr.substring(0, length).toCharArray();

        for (int i = 0; i < strToChar.length; i++) {
            if (!Character.isDigit(strToChar[i])) {
                return chkInt;
            }
        }

        chkInt = true;

        return chkInt;
    }

    /**
     * 전체 문자열(str)에 숫자가 포함되어 있는지 검사한다
     *
     * @param checkStr 검사할 문장
     * @return 숫자포함여부 리턴
     */
    public static boolean hasDigit(String checkStr) {

        boolean chkInt = false;

        int chkCnt = 0;
        char strToChar[] = checkStr.toCharArray();

        for (int i = 0; i < strToChar.length; i++) {
            if (Character.isDigit(strToChar[i])) {
                chkCnt++;
            }
        }

        if (chkCnt > 0) {
            chkInt = true;
        }

        return chkInt;
    }

    /**
     * 특정 길이(length)만큼의 문자열(str)에 숫자가 포함되어 있는지를 검사한다
     *
     * @param checkStr 검사할 문장
     * @param length   검사할 길이
     * @return 검사결과 리턴
     */
    public static boolean hasDigit(String checkStr, int length) {

        boolean chkInt = false;

        if (checkStr == null || length < 1)
            return false;

        int chkCnt = 0;
        char strToChar[] = checkStr.substring(0, length).toCharArray();

        for (int i = 0; i < strToChar.length; i++) {
            if (Character.isDigit(strToChar[i])) {
                chkCnt++;
            }
        }

        if (chkCnt > 0) {
            chkInt = true;
        }

        return chkInt;
    }

    /**
     * 전체 문자열(str)이 알파벳인지를 검사한다.
     *
     * @param checkStr 검사할 문장
     * @return 알파벳여부 리턴
     */
    public static boolean isAlpha(String checkStr) {

        boolean chkInt = false;

        if (checkStr == null)
            return false;

        String pattern = "^[a-zA-Z]*$";

        if (checkStr.matches(pattern)) {
            chkInt = true;
        }

        return chkInt;
    }

    /**
     * 특정 길이(length)만큼의 문자열(str)이 알파벳인지를 검사한다
     *
     * @param checkStr 검사할 문장
     * @param length   검사할 길이
     * @return 알파벳 여부 리턴
     */
    public static boolean isAlpha(String checkStr, int length) {

        boolean chkInt = false;

        if (checkStr == null || length < 1)
            return false;

        String pattern = "^[a-zA-Z]*$";

        checkStr = checkStr.substring(0, length);

        if (checkStr.matches(pattern)) {
            chkInt = true;
        }

        return chkInt;

    }

    /**
     * 전체 문자열(str)이 알파벳 또는 숫자인지를 검사한다.
     *
     * @param checkStr 검사할 문장
     * @return 알파벳 또는 숫자 여부 리턴
     */
    public static boolean isDigitAlpha(String checkStr) {

        boolean chkInt = false;

        if (checkStr == null)
            return false;

        String pattern = "^[a-zA-Z0-9]*$";

        if (checkStr.matches(pattern)) {
            chkInt = true;
        }

        return chkInt;
    }

    /**
     * 특정 길이(length)만큼의 문자열(str)이 알파벳 또는 숫자인지를 검사한다.
     *
     * @param checkStr 검사할 문장
     * @param length   검사할 길이
     * @return 알파벳 또는 숫자여부 리턴
     */
    public static boolean isDigitAlpha(String checkStr, int length) {

        boolean chkInt = false;

        if (checkStr == null || length < 1)
            return false;

        String pattern = "^[a-zA-Z0-9]*$";
        checkStr = checkStr.substring(0, length);

        if (checkStr.matches(pattern)) {
            chkInt = true;
        }

        return chkInt;
    }

    /**
     * 전체 문자열(str)이 소문자인지 검사한다.
     *
     * @param checkStr 검사할 문장
     * @return 소문자 여부 리턴
     */
    public static boolean isLowerCase(String checkStr) {
        if (checkStr == null)
            return false;

        char strToChar[] = checkStr.toCharArray();
        for (int i = 0; i < strToChar.length; i++) {
            if (!Character.isLowerCase(strToChar[i])) {
                return false;
            }
        }

        return true;
    }

    /**
     * 특정 길이(length)만큼의 문자열(str)이 소문자인지 검사한다.
     *
     * @param checkStr 검사할 문장
     * @param length   검사할 길이
     * @return 소문자 포함 여부 리턴
     */
    public static boolean isLowerCase(String checkStr, int length) {
        if (checkStr == null || length < 1)
            return false;
        if (checkStr.length() <= length) {
            return isLowerCase(checkStr);
        } else {
            return isLowerCase(checkStr.substring(0, length));
        }
    }

    /**
     * 전체 문자열(str)이 대문자인지 검사한다.
     *
     * @param checkStr 검사할 문장
     * @return 대문자 포함 여부 리턴
     */
    public static boolean isUpperCase(String checkStr) {
        if (checkStr == null)
            return false;

        char strToChar[] = checkStr.toCharArray();
        for (int i = 0; i < strToChar.length; i++) {
            if (!Character.isUpperCase(strToChar[i])) {
                return false;
            }
        }

        return true;
    }

    /**
     * 특정 길이(length)만큼의 문자열(str)이 대문자인지 검사한다.
     *
     * @param checkStr 검사할 문장
     * @param length   검사할 길이
     * @return 대문자 포함여부 리턴
     */
    public static boolean isUpperCase(String checkStr, int length) {
        if (checkStr == null || length < 1)
            return false;

        if (checkStr.length() <= length) {
            return isUpperCase(checkStr);
        } else {
            return isUpperCase(checkStr.substring(0, length));
        }
    }

    /**
     * 입력 문자열이 '0'문자로만 구성되어있는 확인
     *
     * @param checkStr 검사할 문자열
     * @param nLen     검사할 길이
     * @return 입력받은 길이만큼 '0' 으로 구성되어있다면 true, 아니라면 false 리턴
     */
    public static boolean isAllZero(String checkStr, int nLen) {
        String pattern = "^[0]*$";
        String subStr = checkStr.substring(0, nLen);

        if (subStr.matches(pattern)) {
            return true;
        }

        return false;
    }

    /**
     * 입력 문자열이 16진수문자로만 구성되어있는 확인
     *
     * @param checkStr 검사할 문자열
     * @param nLen     검사할 길이
     * @return 입력받은 길이만큼 16진수에 사용하는 문자(0~9, A~F, a~f)일 때 true, 아니라면 false 리턴
     */
    public static boolean isHexNumStr(String checkStr, int nLen) {
        String pattern = "^[0-9a-fA-F]*$";
        String subStr = checkStr.substring(0, nLen);

        if (subStr.matches(pattern)) {
            return true;
        }

        return false;
    }

    /**
     * 변환 대상 문자열(src)의 대문자를 소문자로 변환하여 대상 문자열(dest)에 저장한다.
     *
     * @param inputStr 검사할 문장
     * @return 소문자 변환 리턴
     */
    public static String getLowerCase(String inputStr) {

        String dest = "";
        dest = inputStr.toLowerCase();

        return dest;
    }

    /**
     * 변환 대상 문자열(src)의 특정 길이(length)만큼 대문자를 소문자로 변환하여 대상 문자열(dest)에 저장한다.
     *
     * @param inputStr 변환할 문장
     * @param length   변환할 길이
     * @return 입력한 길이만큼 소문자로 변환 리턴
     */
    public static String getLowerCase(String inputStr, int length) {

        String dest = "";
        dest = inputStr.substring(0, length).toLowerCase();

        return dest;
    }

    /**
     * 변환 대상 문자열(src)의 소문자를 대문자로 변환하여 대상 문자열(dest)에 저장한다.
     *
     * @param inputStr 변환할 문장
     * @return 대문자로 변환 후 리턴
     */
    public static String getUpperCase(String inputStr) {

        String dest = "";
        dest = inputStr.toUpperCase();
        return dest;
    }

    /**
     * 변환 대상 문자열(src)의 특정 길이(length)만큼 소문자를 대문자로 변환하여 대상 문자열(dest)에 저장한다.
     *
     * @param inputStr 변환할 문장
     * @param length   변환할 길이
     * @return 입력한 길이만큰 대문자로 변환해 리턴
     */
    public static String getUpperCase(String inputStr, int length) {

        String dest = "";
        dest = inputStr.substring(0, length).toUpperCase();

        return dest;
    }

    /**
     * 문자열(src)의 앞뒤 공백을 제거하여 대상 문자열(dest)에 저장한다.
     *
     * @param inputStr 문장
     * @return 공백제거후 리턴
     */
    public static String trim(String inputStr) {

        String dest = "";
        dest = inputStr.trim();

        return dest;
    }

    /**
     * 문자열(src)의 앞 공백을 제거하여 대상 문자열(dest)에 저장한다.
     *
     * @param inputStr 문장
     * @return 왼쪽 공백 제거후 리턴
     */
    public static String trimLeft(String inputStr) {
        if (inputStr == null)
            return null;
        int startIndex = 0;

        for (int i = 0; i < inputStr.length(); i++) {
            if (inputStr.charAt(i) == ' ') {
                startIndex = i + 1;
            } else {
                return inputStr.substring(startIndex, inputStr.length());
            }
        }
        return "";
    }

    /**
     * 문자열(src)의 뒤 공백을 제거하여 대상 문자열(dest)에 저장한다.
     *
     * @param inputStr 문장
     * @return 오른쪽 공백 제거후 리턴
     */
    public static String trimRight(String inputStr) {
        if (inputStr == null)
            return null;
        int endIndex = inputStr.length();
        for (int i = inputStr.length() - 1; i >= 0; i--) {
            if (inputStr.charAt(i) == ' ') {
                endIndex = i;
            } else {
                return inputStr.substring(0, endIndex);
            }
        }
        return "";
    }

    /**
     * 숫자를 주어진 포맷에 맞게 문자열로 변환한다.
     *
     * @param num    숫자
     * @param format 변경할 포멧
     * @return 포멧 변경후 리턴
     */
    public static String formatString(double num, String format) {

        String output = "";
        Double douToStr = Double.valueOf(num);

        String input = douToStr.toString();
        output = String.format(format, new java.math.BigDecimal(input));

        return output;
    }

    /**
     * 문자열 비교
     *
     * @param s1 값1
     * @param s2 값2
     * @return 앞에값이 작으면 -1 같으면 0 크면 1
     */
    public static int compare(String s1, String s2) {

        int chkInt = 0;
        if (isEmpty(s1)) {
            if (isEmpty(s2)) {
                chkInt = 0;
            } else {
                chkInt = -1;
            }
        } else if (isEmpty(s2)) {
            if (isEmpty(s1)) {
                chkInt = 0;
            } else {
                chkInt = 1;
            }
        } else {
            chkInt = s1.compareTo(s2);
            if (chkInt == 0)
                chkInt = 0; // =
            else if (chkInt > 0)
                chkInt = 1; // >
            else if (chkInt < 0)
                chkInt = -1; // <
        }

        return chkInt;
    }

    /**
     * 문자열 비교
     *
     * @param s1  값1
     * @param s2  값2
     * @param num 체크할 자릿수
     * @return 앞에값이 작으면 -1 같으면 0 크면 1
     */
    public static int compare(String s1, String s2, int num) {

        int rtVal = s1.substring(0, num).compareTo(s2.substring(0, num));

        if (rtVal < 0)
            return -1;
        else if (rtVal == 0)
            return 0;
        else
            return 1;
    }

    /**
     * 문자열 비교
     *
     * @param s1              값1
     * @param s2              값2
     * @param num             체크할 자릿수
     * @param caseInsensitive 대소문자 구분 여부. true : 대소문자 비구분 / false : 대소문자 구분
     * @return 앞에값이 작으면 -1 같으면 0 크면 1
     */
    public static int compare(String s1, String s2, int num, boolean caseInsensitive) {
        // null check
        if (s1 == null && s2 == null) {
            return 0;
        }

        if (s1 == null) {
            return -1;
        }

        if (s2 == null) {
            return 1;
        }

        // string compare
        int rtVal = 0;
        String s1SubStr = s1.substring(0, num);
        String s2SubStr = s2.substring(0, num);
        if (caseInsensitive) {
            rtVal = s1SubStr.compareToIgnoreCase(s2SubStr);
        } else {
            rtVal = s1SubStr.compareTo(s2SubStr);
        }

        if (rtVal < 0) {
            return -1;
        } else if (rtVal == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    /**
     * 정해진 위치까지 문자열 붙이기
     *
     * @param s1  값1
     * @param s2  값2
     * @param num 붙일 길이
     * @return s2의 num 길이만큼 잘라서 s1에 붙여서 리턴
     */
    public static String concat(String s1, String s2, int num) {
        return s1.concat(s2.substring(0, num));
    }

    /**
     * 문자열 붙이기
     *
     * @param s1 값1
     * @param s2 값2
     * @return 두 문장을 붙여 리턴
     */
    public static String concat(String s1, String... s2) {
        StringBuilder sb = new StringBuilder();
        if (s1 != null)
            sb.append(s1);
        for (String s : s2) {
            if (s != null)
                sb.append(s);
        }
        return sb.toString();
    }

    /**
     * double을 string변환해서 리턴해주는 함수
     *
     * @param num 숫자
     * @return 문자로 변환 후 리턴
     */
    public static String doubleToString(double num) {

        String rtVal = "";
        BigDecimal Val = BigDecimal.valueOf(num);

        rtVal = Val.toString();

        return rtVal;
    }

    /**
     * int을 string변환해서 리턴해주는 함수
     *
     * @param num 숫자
     * @return 문자로 변환 리턴
     */
    public static String intToString(int num) {

        String rtVal = "";
        Integer Val = num;

        rtVal = Val.toString();

        return rtVal;
    }

    /**
     * long을 string변환해서 리턴해주는 함수
     *
     * @param num 숫자
     * @return 문자로 변환 리턴
     */
    public static String longToString(long num) {

        String rtVal = "";
        Long Val = num;

        rtVal = Val.toString();

        return rtVal;
    }

    /**
     * 문자열의 길이를 체크하는 함수
     *
     * @param str 문자
     * @return 길이를 리턴
     */
    public static int strLength(String str) {

        int rtVal = 0;

        rtVal = str.length();

        return rtVal;
    }

    /**
     * 문자열 비교
     *
     * @param s1 값1
     * @param s2 값2
     * @return 값1에 값2가 문장이 처음 존재하는 위치를 리턴
     */
    public static int searchIndex(String s1, String s2) {

        return s1.indexOf(s2);
    }

    /**
     * string이 empty or null이면 특정문자열을 반환
     *
     * @param str    공백체크 문장
     * @param defVal 공백이면 변경할 문장
     * @return 공백여부 체크해 공백이면 대체 값으로 변경 리턴
     */
    public static String nvl(String str, String defVal) {
        String rtVal = str;
        if (isEmpty(str))
            rtVal = defVal;
        return rtVal;
    }

    /**
     * srcStr 에 totLen 길이가 되도록 왼쪽에 padStr을 추가하여 반환 <br/>
     * padStr 이 2이상일 때 totLen 을 초과시 totLen 에 맞춰서 잘림 <br/>
     * ex) srcStr = ABC, totLen = 6, padStr = XY 일 때 YXYABC 반환됨
     *
     * @param srcStr : 원본문자
     * @param totLen : 패딩후 최종 문자 길이
     * @param padStr : 패딩할 문자(열)
     * @return
     */
    public static String padLeft(String srcStr, int totLen, String padStr) {
        String pad = (padStr == null || padStr.length() <= 0 ? " " : padStr);
        StringBuilder sb = new StringBuilder(nvl(srcStr, ""));

        while (sb.length() <= totLen) {
            sb.insert(0, pad);
        }

        if (sb.length() > totLen) {
            return sb.substring(sb.length() - totLen);
        } else {
            return sb.toString();
        }
    }

    /**
     * srcStr 에 totLen 길이가 되도록 오른쪽에 padStr을 추가하여 반환 <br/>
     * padStr 이 2이상일 때 totLen 을 초과시 totLen 에 맞춰서 잘림 <br/>
     * ex) srcStr = ABC, totLen = 6, padStr = XY 일 때 ABCXYX 반환됨
     *
     * @param srcStr : 원본문자
     * @param totLen : 패딩후 최종 문자 길이
     * @param padStr : 패딩할 문자(열)
     * @return
     */
    public static String padRight(String srcStr, int totLen, String padStr) {
        String pad = (padStr == null || padStr.length() <= 0 ? " " : padStr);
        StringBuilder sb = new StringBuilder(nvl(srcStr, ""));

        while (sb.length() <= totLen) {
            sb.append(pad);
        }

        if (sb.length() > totLen) {
            return sb.substring(0, totLen);
        } else {
            return sb.toString();
        }
    }

    /**
     * srcStr에서 wrd1 을 wrd2 로 변환하여 반환
     *
     * @param srcStr 변경전문장
     * @param wrd1   찾을 문자
     * @param wrd2   변경할 문자
     * @return 변경 후 리턴
     */
    public static String replace(String srcStr, char wrd1, char wrd2) {
        if (srcStr == null)
            return "";
        String rtVal = srcStr.replace(wrd1, wrd2);
        return rtVal;
    }

    /**
     * srcStr에서 wrd1 을 wrd2 로 변환하여 반환
     *
     * @param srcStr 변경전문장
     * @param wrd1   찾을 문자
     * @param wrd2   변경할 문자
     * @return 변경 후 리턴
     */
    public static String replace(String srcStr, String wrd1, String wrd2) {
        if (srcStr == null)
            return "";
        String rtVal = srcStr.replace(wrd1, wrd2);
        return rtVal;
    }

    /**
     * 문자열에서 특정 패턴을 찾아 모두 제거
     *
     * @param srcStr         변경전 문장
     * @param removeStrRegex 제거할 문자패턴
     * @return 제거 후 리턴
     */
    public static String removeAll(String srcStr, String removeStrRegex) {
        String rtVal = srcStr.replaceAll(removeStrRegex, "");
        return rtVal;
    }

    /**
     * 문자열을 int로 형변환하여 반환
     *
     * @param str 변환할 문장
     * @return 정수로 변환해 리턴
     */
    public static int toInt(String str) {
        int rtVal = 0;
        rtVal = Integer.parseInt(str);
        return rtVal;
    }

    /**
     * 문자열을 int로 형변환하여 반환
     *
     * @param str  변환할 문장
     * @param size 변환할 길이
     * @return 정수로 변환해 리턴
     */
    public static int toInt(String str, int size) {
        int rtVal = 0;
        rtVal = Integer.parseInt(str.substring(0, size));
        return rtVal;
    }

    /**
     * string을 long으로변환해서 리턴
     *
     * @param str  변환할 문장
     * @param size 변환할 길이
     * @return long타입으로 변환해 리턴
     */
    public static long toLong(String str, int size) {
        long rtVal = 0;
        rtVal = Long.parseLong(str.substring(0, size));
        return rtVal;
    }

    /**
     * string을 long으로변환해서 리턴
     *
     * @param str 변환할 문장
     * @return long타입으로 변환해 리턴
     */
    public static long toLong(String str) {
        long rtVal = 0;
        rtVal = Long.parseLong(str);
        return rtVal;
    }

    /**
     * string을 double변환해서 리턴
     *
     * @param str 변환할 문장
     * @return double타입으로 변환해 리턴
     */
    public static double toDouble(String str) {
        double rtVal = 0;
        rtVal = Double.parseDouble(str);
        return rtVal;
    }

//    /**
//     * 입력받은 String 을 마스킹 하는 함수
//     * @param str 마스킹할 내용
//     * @param types 1:주민번호 2:전화번호
//     * @return 마스킹 처리 후 리턴
//     */
//    public static  String masking(String str, String types) {
//        try {
//            String msk = str;
//
//            if ( str == null || str.isEmpty() ){
//                return msk;
//            }
//
//            int len = str.length();
//            if ( "1".equals( types ) ) {
//                //주민번호
//                if ( len == 13 ){
//                    msk = str.substring(0, 7) + "******";
//                }
//            } else if ( "2".equals( types ) ) {
//                //전화번호
//                if ( len >= 11 ) {
//                    //010-1111-2222
//                    msk = str.substring(0, 3) + "****" + str.substring(7);
//                } else if ( len == 10 ) {
//                    if ( "02".equals(str.substring(0, 2)) ){
//                        //02-1111-2222
//                        msk = str.substring(0, 2) + "****" + str.substring(6);
//                    } else {
//                        //031-111-2222
//                        msk = str.substring(0, 3) + "***" + str.substring(6);
//                    }
//                } else if ( len == 9 ) {
//                    //02-111-2222
//                    msk = str.substring(0, 2) + "***" + str.substring(5);
//                } else if ( len == 8 ) {
//                    //1111-2222
//                    msk = "****" + str.substring(4);
//                } else if ( len == 7 ) {
//                    //111-2222
//                    msk = "***" + str.substring(3);
//                } else {
//                    //확인필요
//                }
//            } else {
//                //18305 입력값을 확인하십시오.
//                throw CommonException.builder().errorCode(18305).build();
//            }
//
//            return msk;
//        } catch (CommonException ex) {
//            throw ex;
//        } catch (RuntimeException ex) {
//            throw CommonException.builder().errorCode(20338).cause(ex).build();
//        }
//    }

//    /**
//     * 입력받은 String 을 마스킹 하는 함수
//     * 중간에 - 까지 붙임.
//     * @param str 마스킹할 내용
//     * @param types 마스킹 타입 (1:주민번호 2:전화번호)
//     * @return 마스킹 처리 후 리턴
//     */
//    public static  String maskingView(String str, String types) {
//        try {
//            String msk = str;
//
//            if ( str == null || str.isEmpty() ){
//                return msk;
//            }
//
//            int len = str.length();
//            if ( "1".equals( types ) ) {
//                //주민번호
//                if ( len == 13 ){
//                    msk = str.substring(0, 6) + "-" + str.substring(6, 7) + "******";
//                } else if ( len == 10 ){
//                    msk = str.substring(0, 3) + "-" + str.substring(3, 5) + "-" + str.substring(5);
//                }
//            } else if ( "2".equals( types ) ) {
//                //전화번호
//                if ( len >= 11 ) {
//                    //010-1111-2222
//                    msk = str.substring(0, 3) + "-****-" + str.substring(7);
//                } else if ( len == 10 ) {
//                    if ( "02".equals(str.substring(0, 2)) ){
//                        //02-1111-2222
//                        msk = str.substring(0, 2) + "-****-" + str.substring(6);
//                    } else {
//                        //031-111-2222
//                        msk = str.substring(0, 3) + "-***-" + str.substring(6);
//                    }
//                } else if ( len == 9 ) {
//                    //02-111-2222
//                    msk = str.substring(0, 2) + "-***-" + str.substring(5);
//                } else if ( len == 8 ) {
//                    //1111-2222
//                    msk = "****-" + str.substring(4);
//                } else if ( len == 7 ) {
//                    //111-2222
//                    msk = "***-" + str.substring(3);
//                } else {
//                    //확인필요
//                }
//            } else {
//                //18305 입력값을 확인하십시오.
//                throw CommonException.builder().errorCode(18305).build();
//            }
//
//            return msk;
//        } catch (CommonException ex) {
//            throw ex;
//        } catch (RuntimeException ex) {
//            throw CommonException.builder().errorCode(20338).cause(ex).build();
//        }
//    }

//    /**
//     * 입력받은 String 을 마스킹 하는 함수
//     * <pre>
//     * 중간에 - 까지 붙임수 있게
//     * 무조건 해제 권한 체크 안함 마스킹 처리.
//     * </pre>
//     *
//     * @param str 마스킹할 문장
//     * @param types ( 1:주민번호 2:전화번호 3:계좌번호 4:고객명 5:이메일 6:상세주소 7:카드번호 8:타기관계좌번호 9:펀드계좌번호)
//     * @param viewTp ( 1:중간에 - 추가 ELSE:중간에 - 추가안함 )
//     * @return 마스킹 처리 후 리턴
//     */
//    public static  String automaskingView(String str, String types, String viewTp) {
//        try {
//            //마스킹 해제 권한 여부 설정.
//            boolean bMaskDel = false;
//            String msk = automaskingView(str, types, viewTp, bMaskDel);
//            return msk;
//        } catch (RuntimeException ex) {
//            throw CommonException.builder().errorCode(20338).cause(ex).build();
//        }
//    }

//    /**
//     * 입력받은 String 을 마스킹 하는 함수
//     * <pre>
//     * 중간에 - 까지 붙임수 있게
//     * </pre>
//     *
//     * @param str 마스킹할 문장
//     * @param types ( 1:주민번호 2:전화번호 3:계좌번호 4:고객명 5:이메일 6:상세주소 7:카드번호 8:타기관계좌번호 9:펀드계좌번호)
//     * @param viewTp ( 1:중간에 - 추가 ELSE:중간에 - 추가안함 )
//     * @param bMaskDel 마스킹 해제 권한 여부
//     * @return 마스킹 처리 후 리턴
//     */
//    public static  String automaskingView(String str, String types, String viewTp, boolean bMaskDel) {
//        try {
//
//            String msk = str;
//            if ( str == null || str.trim().isEmpty() ){
//                return "";
//            }
//            str = StringUtils.trim(str);
//            msk = str;
//
//            String mskStr = "**************************************************";
//            int msklen = str.length()/2;
//
//            int len = str.length();
//            if ( "1".equals( types ) ) {
//                //주민번호
//                if ( "1".equals(viewTp) ){
//                    //중간 바 추가
//                    if ( len == 13 ){
//                        if ( bMaskDel ){
//                            msk = str.substring(0, 6) + "-" + str.substring(6);
//                        } else{
//                            msk = str.substring(0, 6) + "-" + str.substring(6, 7) + "******";
//                        }
//                    } else if ( len == 10 ){
//                        msk = str.substring(0, 3) + "-" + str.substring(3, 5) + "-" + str.substring(5);
//                    }
//                } else {
//                    //중간 바 없음.
//                    if ( len == 13 ){
//                        if ( bMaskDel ){
//                            msk = str.substring(0, 6) + str.substring(6);
//                        } else{
//                            msk = str.substring(0, 6) + str.substring(6, 7) + "******";
//                        }
//                    }
//                }
//            } else if ( "2".equals( types ) ) {
//                //전화번호
//                if ( "1".equals(viewTp) ){
//                    //중간 바 추가
//                    if ( len >= 11 ) {
//                        //010-1111-2222
//                        if ( bMaskDel ){
//                            msk = str.substring(0, 3) + "-" + str.substring(3, 7) + "-" + str.substring(7);
//                        } else {
//                            msk = str.substring(0, 3) + "-****-" + str.substring(7);
//                        }
//                    } else if ( len == 10 ) {
//                        if ( "02".equals(str.substring(0, 2)) ){
//                            //02-1111-2222
//                            if ( bMaskDel ){
//                                msk = str.substring(0, 2) + "-" + str.substring(2, 6) + "-" + str.substring(6);
//                            } else {
//                                msk = str.substring(0, 2) + "-****-" + str.substring(6);
//                            }
//                        } else {
//                            //031-111-2222
//                            if ( bMaskDel ){
//                                msk = str.substring(0, 3) + "-" + str.substring(3, 6) + "-" + str.substring(6);
//                            } else {
//                                msk = str.substring(0, 3) + "-***-" + str.substring(6);
//                            }
//                        }
//                    } else if ( len == 9 ) {
//                        //02-111-2222
//                        if ( bMaskDel ){
//                            msk = str.substring(0, 2) + "-" + str.substring(2, 5) + "-" + str.substring(5);
//                        } else {
//                            msk = str.substring(0, 2) + "-***-" + str.substring(5);
//                        }
//                    } else if ( len == 8 ) {
//                        //1111-2222
//                        if ( bMaskDel ){
//                            msk = str.substring(0, 4) + "-" + str.substring(4);
//                        } else {
//                            msk = "****-" + str.substring(4);
//                        }
//                    } else if ( len == 7 ) {
//                        //111-2222
//                        if ( bMaskDel ){
//                            msk = str.substring(0, 3) + "-" + str.substring(3);
//                        } else {
//                            msk = "***-" + str.substring(3);
//                        }
//                    }
//                } else {
//                    //중간 바 없음
//                    if ( len >= 11 ) {
//                        //010-1111-2222
//                        if ( !bMaskDel ){
//                            msk = str.substring(0, 3) + "****" + str.substring(7);
//                        }
//                    } else if ( len == 10 ) {
//                        if ( "02".equals(str.substring(0, 2)) ){
//                            //02-1111-2222
//                            if ( !bMaskDel ){
//                                msk = str.substring(0, 2) + "****" + str.substring(6);
//                            }
//                        } else {
//                            //031-111-2222
//                            if ( !bMaskDel ){
//                                msk = str.substring(0, 3) + "***" + str.substring(6);
//                            }
//                        }
//                    } else if ( len == 9 ) {
//                        //02-111-2222
//                        if ( !bMaskDel ){
//                            msk = str.substring(0, 2) + "***" + str.substring(5);
//                        }
//                    } else if ( len == 8 ) {
//                        //1111-2222
//                        if ( !bMaskDel ){
//                            msk = "****" + str.substring(4);
//                        }
//                    } else if ( len == 7 ) {
//                        //111-2222
//                        if ( !bMaskDel ){
//                            msk = "***" + str.substring(3);
//                        }
//                    }
//                }
//            } else if ( "3".equals( types ) ) {
//                //3:계좌번호
//                if( str.length() != 11) {
//                    //18305 입력값을 확인하십시오.
//                    throw CommonException.builder().errorCode(18305).build();
//                }
//
//                if ( "1".equals(viewTp) ){
//                    if ( bMaskDel ){
//                        msk = str.substring(0, 3) + "-" + str.substring(3, 5) + "-" + str.substring(5);
//                    } else {
//                        msk = str.substring(0, 3) + "-" + str.substring(3, 4) + "*-***" + str.substring(8);
//                    }
//                } else {
//                    if ( bMaskDel ){
//                        msk = str;
//                    } else {
//                        msk = str.substring(0, 3) + str.substring(3, 4) + "****" + str.substring(8);
//                    }
//                }
//            } else if ( "9".equals( types ) ) {
//                //9:펀드계좌번호는 13자리  계좌번호 11 + 펀드 2 = 13
//                //or 14자리  계좌번호 11 + 펀드 3 = 14
//                if( str.length() != 13 && str.length() != 14 ) {
//                    //18305 입력값을 확인하십시오.
//                    throw CommonException.builder().errorCode(18305).build();
//                }
//
//                if ( "1".equals(viewTp) ){
//                    if ( bMaskDel ){
//                        msk = str.substring(0, 3) + "-" + str.substring(3, 5) + "-" + str.substring(5, 11) + "-" + str.substring(11) ;
//                    } else {
//                        msk = str.substring(0, 3) + "-" + str.substring(3, 4) + "*-***" + str.substring(8, 11) + "-" + str.substring(11) ;
//                    }
//                } else {
//                    if ( bMaskDel ){
//                        msk = str;
//                    } else {
//                        msk = str.substring(0, 3) + str.substring(3, 4) + "****" + str.substring(8);
//                    }
//                }
//            } else if ( "4".equals( types ) ) {
//                //4:고객명
//                if ( !bMaskDel ){
//                    if ( chkHangul( str ) ){
//                        if ( len <= 2 ){
//                            msk = str.substring(0, 1) + "*";
//                        } else if ( len >= 3 ){
//                            msk = str.substring(0, 1) + mskStr.substring(0, len-2) + str.substring(len-1);
//                        }
//                    } else {
//
//                        if ( msklen == 0 ){
//                            msklen = len;
//                            msk = mskStr.substring(0, len);
//                        } else {
//                            if ( msklen > 4 )
//                                msklen = 4;
//
//                            msk = str.substring(0, msklen) + mskStr.substring(0, len-msklen);
//                        }
//                    }
//                }
//
//            } else if ( "5".equals( types ) ) {
//                //5:이메일
//                if ( !bMaskDel ){
//                    int idx = str.indexOf( "@" );
//                    //int idx2 = idx-2;
//                    //int msklen = 0;
//
//                    int fondView = 0;
//                    //int endView  = 0;
//
//                    if ( idx < 0 ){
//                        idx = len;
//                    } else {
//                        msklen = idx/2;
//                    }
//                    if ( idx%2 > 0 ) msklen++;
//                    fondView = msklen/2;
//                    if ( fondView <= 0 )
//                        fondView = 1;
//
//                    msk = str.substring(0, fondView) + mskStr.substring(0, msklen) + str.substring(fondView+msklen) ;
//                }
//            } else if ( "6".equals( types ) ) {
//                //6:상세주소
//                if ( !bMaskDel ){
//
//                    String sTmp = str;
//                    //msk = "*******";
//                    for(int i=0; i<10;i++){
//                        sTmp = sTmp.replace( String.valueOf(i), "*");
//                    }
//                    msk = sTmp;
//                }
//            } else if ( "7".equals( types ) ) {
//                //7:카드번호
//                if ( !bMaskDel ){
//                    //7~12 자리 마스킹
//                    if ( str.length() > 12 ) {
//                        msk = str.substring(0, 6) + "******" + str.substring(12);
//                    } else {
//                        msk = str.substring(0, str.length()-msklen) + mskStr.substring(0,msklen);
//                    }
//                }
//            } else if ( "8".equals( types ) ) {
//                //8:타기관계좌번호
//                if ( !bMaskDel ){
//                    int mlen1 = str.length() / 3;
//                    int mlen2 = mlen1;
//                    if ( str.length() % 3 > 0 )
//                        mlen2++;
//
//                    int vieLen = str.length() - mlen2 - mlen1 ;
//                    msk = str.substring(0, vieLen) + mskStr.substring(0, mlen2);// + str.substring(str.length()-mlen1);
//
//                    if ( mlen1 > 0 ){
//                        msk += str.substring(str.length()-mlen1);
//                    }
//                }
//            } else {
//                // 18305:입력값을 확인하십시오.
//                throw CommonException.builder().errorCode(18305).build();
//            }
//
//            return msk;
//        } catch (CommonException ex) {
//            throw ex;
//        } catch (RuntimeException ex) {
//            throw CommonException.builder().errorCode(20338).cause(ex).build();
//        }
//    }

    /**
     * 한글여부 리턴
     *
     * @param word 체크할 문장
     * @return 한글포함여부
     */
    public static boolean chkHangul(String word) {
        return word.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*");
    }

    /**
     * utf-8 문자열을 euc-kr 인코딩 변환하아여 해당 문자열 byte를 반환
     *
     * <pre>
     * Utf8 문자열을 euc-kr로 변환 리턴
     * utf8이 기본으로 되어 있어 string 으로 리턴 불가 byte 배열로 리턴
     * </pre>
     *
     * @param utf8Str
     * @return
     * @throws UnsupportedEncodingException
     */
    public static byte[] toEuckr(String utf8Str) throws UnsupportedEncodingException {
        byte[] rv;

        rv = utf8Str.getBytes("euc-kr");
        return rv;
    }

    /**
     * euc-kr 문자열을 Utf8로 변환 리턴
     *
     * <pre>
     * euc-kr 문자열을 Utf8로 변환 리턴
     * utf8이 기본으로 되어 있어 string 으로 받을수 없고 byte로만 받을수 있음.
     * </pre>
     *
     * @param euckrBytes 변환할 문장
     * @return 변환후 리턴
     * @throws UnsupportedEncodingException
     */
    public static String ecdEucKrToUtf(byte[] euckrBytes) throws UnsupportedEncodingException {
        String rv = "";

        rv = new String(euckrBytes, "euc-kr");
        return rv;
    }

    /**
     * Byte 단위 Substring 처리용 함수
     *
     * <pre>
     * Byte 단위 Substring 처리용 함수
     * </pre>
     *
     * @param pStr  : 작업할 문자열
     * @param pSidx : 자를 시작위치
     * @param plen  : 길이
     * @return 원하는 길이만큼 잘라 리턴
     */
    public static byte[] subByte(byte[] pStr, int pSidx, int plen) {

        if (pStr == null || pStr.length < 1) {
            return null;
        }

        byte[] arry = new byte[plen];

//		if (pStr.length < pSidx + plen) {
//			throw AppCommonException.builder().errorCode(20338).build();
//		}

        for (int i = 0; i < plen; i++) {
            arry[i] = pStr[pSidx + i];
        }

        return arry;
    }

    /**
     * Byte 단위 Substring 처리용 함수
     *
     * <pre>
     * Byte 단위 Substring 처리용 함수
     * </pre>
     *
     * @param pStr  : 작업할 문자열
     * @param pSidx : 자를 시작 위치
     * @return 정해진 길이 이후 잘라서 리턴
     */
    public static byte[] subByte(byte[] pStr, int pSidx) {

        if (pStr == null || pStr.length < 1) {
            return null;
        }

        int len = pStr.length - pSidx;

//		if (len <= 0 || pSidx < 0 || pStr.length <= pSidx) {
//			throw AppCommonException.builder().errorCode(20338).build();
//		}

        byte[] arry = new byte[len];

        for (int i = 0; i < len; i++) {
            arry[i] = pStr[pSidx + i];
        }

        return arry;
    }

//    /**
//     * Byte 단위 Substring 처리용 함수
//     * <pre>
//     * Byte 단위 Substring 처리용 함수.
//     * 한글 등 유니 코드가 중간에서 잘릴 경우 문자가 깨지는 것을 방지
//     * </pre>
//     *
//     * @param zSrc : 작업할 문자열
//     * @param nLen : 길이. 0보다 작거나 같고/원래 문자열 길이보다 클 경우 원래 문자열 길이를 사용
//     * @return 원하는 길이만큼 잘라 리턴
//     */
//    public static byte[] unicodRvsn(byte[] zSrc, int nLen) {
//
//        int maxLen = zSrc.length;
//        if (nLen <= 0 || maxLen < nLen) {
//            nLen = maxLen;
//        }
//
//        try {
//            int i = 0;
//            boolean unicodeFlag = false;
//            byte[] arry = new byte[nLen];
//            for (i = 0; i < nLen; i++) {
//                arry[i] = zSrc[i];
//                byte chk = (byte) (zSrc[i] & 0x80);
//                if ((!unicodeFlag) && (chk != 0x00)) {
//                    unicodeFlag = true;
//                } else {
//            	    unicodeFlag = false;
//                }
//            }
//
//            if (unicodeFlag) {
//                zSrc[i - 1] = ' ';
//            }
//
//            return zSrc;
//        } catch (RuntimeException ex) {
//            throw CommonException.builder().errorCode(20338).cause(ex).build();
//        }
//    }

    /**
     * 정규표현식을 이용해서 천단위 콤마 찍기
     *
     * @param num 숫자형태의 문자열
     * @return 변환 후 리턴.
     */
    public static String setComma(String num) {

        // Null 체크
        if (num == null || num.trim().isEmpty())
            return "0";

        String num2 = num.trim();

        // 숫자형태가 아닌 문자열일경우 디폴트 0으로 반환
//		String numberExpr = "^[-+]?(0|[1-9][0-9]*)(\\.[0-9]+)?([eE][-+]?[0-9]+)?$";
//		boolean isNumber = num2.matches(numberExpr);
//		if (!isNumber) {
//			// 22718 ({0}) 확인하십시오.
//			throw AppCommonException.builder().errorCode(22718).args(new String[] { "숫자 여부를" }).build();
//		}

        String strResult = num2; // 출력할 결과를 저장할 변수
        Pattern p = Pattern.compile("(^[+-]?\\d+)(\\d{3})"); // 정규표현식
        Matcher regexMatcher = p.matcher(num2);

        while (regexMatcher.find()) {
            strResult = regexMatcher.replaceAll("$1,$2"); // 치환 : 그룹1 + "," + 그룹2
            // 치환된 문자열로 다시 matcher객체 얻기
            // regexMatcher = p.matcher(strResult);
            regexMatcher.reset(strResult);
        }
        return strResult;
    }

    /**
     * long 입력값을 정규표현식을 이용해서 천단위 콤마 찍기. 출력 폭에 맞춰 오른쪽 정렬해 리턴. 지정한 출력 폭보다 입력값이 길다면,
     * OVERFLOW_CHAR('#')으로 출력 폭만큼 채워서 반환함.
     *
     * @param lSrc  입력 숫자
     * @param nLen  출력 폭
     * @param nFlag 출력값 앞에 '+'(양수일 때, CNV_PLUSSIGN) 또는 '*'(CNV_ASTERISK) 문자 추가 둘 다
     *              추가도 가능(CNV_PLUSSIGN | CNV_ASTERISK)
     * @return 변환 후 리턴.
     */
    public static String longToComma(long lSrc, int nLen, int nFlag) {
        String numStr = Long.toString(lSrc);

        String resStr = null;

        // 할당한 자리에 표현할 수 없을 경우 '#'으로 채움
        int orgLen = numStr.length();
        if (orgLen > nLen) {
            char[] ch = new char[nLen];
            Arrays.fill(ch, OVERFLOW_CHAR);
            StringBuilder sb = new StringBuilder(nLen);
            sb.append(ch);
            return sb.toString();
        }

        // 더이상 문자 추가될 여지가 없을 경우, 그대로 리턴.
        if (orgLen == nLen) {
            return numStr;
        }

        // 정규표현식. 적은 자리수부터 3자리씩 끊어서 grouping
        Pattern p = Pattern.compile("(^[+-]?\\d+)(\\d{3})");
        Matcher regexMatcher = p.matcher(numStr);

        while (regexMatcher.find()) {
            // overflow check
            if (orgLen >= nLen) {
                break;
            }
            // 치환 : 그룹1 + "," + 그룹2
            resStr = regexMatcher.replaceAll("$1,$2");
            // 치환된 문자열로 다시 matcher객체 얻기
            regexMatcher.reset(resStr);
            orgLen++;
        }

        if ((nFlag & CNV_PLUSSIGN) != 0 && (lSrc > 0) && (nLen > orgLen)) {
            resStr = PLUS_CHAR + resStr;
            orgLen++;
        }

        if ((nFlag & CNV_ASTERISK) != 0 && (nLen > orgLen)) {
            resStr = ASTERISK_CHAR + resStr;
            orgLen++;
        }
        // 출력 폭에 맞춰 오른쪽 정렬
        resStr = String.format("%" + nLen + "s", resStr);

        return resStr;
    }

    /**
     * double 입력값을 정규표현식을 이용해서 천단위 콤마 찍기. 출력 폭에 맞춰 오른쪽 정렬해 리턴. 지정한 출력 폭보다 입력값이 길다면,
     * OVERFLOW_CHAR('#')으로 출력 폭만큼 채워서 반환함.
     *
     * @param dSrc   입력 숫자
     * @param nLen   출력 폭
     * @param nFract 소숫점 이하 정밀도 지정
     * @param nFlag  출력값 앞에 '+'(양수일 때, CNV_PLUSSIGN) 또는 '*'(CNV_ASTERISK) 문자 추가 둘 다
     *               추가도 가능(CNV_PLUSSIGN | CNV_ASTERISK)
     * @return 변환 후 리턴.
     */
    public static String doubleToComma(double dSrc, int nLen, int nFract, int nFlag) {
        String numStr = String.format("%." + nFract + "f", dSrc);

        // 할당한 자리에 표현할 수 없을 경우 '#'으로 채움
        StringBuilder sb = new StringBuilder(nLen);
        int orgLen = numStr.length();
        if (orgLen > nLen) {
            char[] ch = new char[nLen];
            Arrays.fill(ch, OVERFLOW_CHAR);
            sb.append(ch);
            return sb.toString();
        }

        // 정수부 소수부 분리
        String realStr[] = numStr.split("\\.");
        int decimalLen = 0;
        if (realStr.length > 1) {
            decimalLen = realStr[1].length() + 1;
        }

        // 정수부분 long 값으로 변환 후, longToComma() 이용
        long lSrc = Long.parseLong(realStr[0]);
        String resStr = longToComma(lSrc, nLen - decimalLen, nFlag);
        // 결과값 정수부와 실수부 결합
        sb.append(resStr);
        if (decimalLen > 0) {
            sb.append(".").append(realStr[1]);
        }

        return sb.toString();
    }

    /**
     * 출력용 날짜 포멧 변경
     *
     * <pre>
     * 'YYYYMMDD' 를 'YYYY년 MM년 DD일' 로 리턴
     * '01월' 은 '1월' 로 리턴
     * </pre>
     *
     * @param pdate 날짜입력(YYYYMMDD)
     * @return 출력용 날짜 리턴
     */
    public static String setDateFormat(String pdate) {

//		if (pdate == null || pdate.trim().isEmpty() || pdate.trim().length() != 8) {
//			// 22718 ({0}) 확인하십시오.
//			throw AppCommonException.create("날짜값 누락");
//		}
//
//		if (!isDigit(pdate)) {
//			// 22718 ({0}) 확인하십시오.
//			throw AppCommonException.create("날짜 형식 오류 ");
//		}

        String sTmp = pdate.trim();

        String sYYYY = sTmp.substring(0, 4);
        String sMM = sTmp.substring(4, 6);
        String sDD = sTmp.substring(6, 8);

        String sRv = sYYYY + "년 " + Integer.parseInt(sMM) + "월 " + Integer.parseInt(sDD) + "일";
        return sRv;
    }

    /**
     * @Method Name : getOracleCode
     * @description : 에러 메세지 중에서 오라클 에러 코드를 찾아 리턴 하는 함수
     * @param pdate
     * @return
     */
    public static String getOracleCode(String pdate) {

        String oracode= "";

        if ( pdate == null )
            return oracode;

        int idx = pdate.indexOf( "ORA-" );

        if ( idx >=0 ) {
            oracode = "ORA-";

            int loopCnt = 0;
            char tmp;

            for ( int i=idx+4; i< pdate.length(); i++ ) {
                if ( loopCnt > 5  )
                    break;

                tmp = pdate.charAt(i);

                if ( Character.isDigit(tmp) ) {
                    oracode += tmp;
                } else {
                    break;
                }

                loopCnt++;
            }
        } else {
            return pdate ;
        }

        return oracode;

    }
}