package skcnc.msa3.framework.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    /**
     * 현재 시각을 1/1000 초(밀리세컨드) 단위로 반환한다. ex> 20201117150609323
     * @return 밀리세컨드 단위 현재시각
     */
    public static long currentTimeMillis() {
        long timeInMillis = System.currentTimeMillis();

        return timeInMillis;
    }

    /**
     * 현재 날짜를 YYYYMMDD(8) 형태의 문자열로 변환한다.
     * @return 현재날짜
     */
    public static String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date timeInDate = new Date();
        String timeInFormat = sdf.format(timeInDate);

        return timeInFormat;
    }

    /**
     * 현재 년도를 문자열로 변환한다.
     * @return 현재년도
     */
    public static String getCurrentYear() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date timeInDate = new Date();
        String timeInFormat = sdf.format(timeInDate);

        return timeInFormat;
    }

    /**
     * 현재 달을 문자열로 변환한다.
     * @return 현재달
     */
    public static String getCurrentMonth() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        Date timeInDate = new Date();
        String timeInFormat = sdf.format(timeInDate);

        return timeInFormat;
    }

    /**
     * 현재 일을 문자열로 변환한다.
     * @return 현재일자
     */
    public static String getCurrentDay() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        Date timeInDate = new Date();
        String timeInFormat = sdf.format(timeInDate);

        return timeInFormat;
    }

    /**
     * 현재 날짜/시간을 YYYYMMDDhh24miss(14) 형태의 문자열로 변환한다.
     * @return 현재일시 YYYYMMDDhh24miss(14)
     */
    public static String getCurrentDatetime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date timeInDate = new Date();
        String timeInFormat = sdf.format(timeInDate);

        return timeInFormat;
    }

    /**
     * 현재 일시를 YYYYMMDDhh24miss(14)+microsecond(6) 형태의 문자열로 변환한다.
     * @return 현재 일시 YYYYMMDDhh24miss(14)+microsecond(6)
     */
    public static String getCurrentDatetimemicro() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date timeInDate = new Date();
        String timeInFormat = sdf.format(timeInDate);

        // nano second = 1,000,000,000분의 1초
        double nanoTime = (double) System.nanoTime() / 1000000000; // 1초 단위로 변환
        long milliTime = (long) ((nanoTime - (long) (nanoTime)) * 1000000);

        timeInFormat = timeInFormat + String.format("%06d", milliTime);

        return timeInFormat;
    }

    /**
     * 현재 일시를 YYYYMMDDhh24miss(14)+millisecond(3) 형태의 문자열로 변환한다
     * @return 현재 일시 YYYYMMDDhh24miss(14)+millisecond(3)
     */
    public static String getCurrentDatetimemilli() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        Date timeInDate = new Date();
        String timeInFormat = sdf.format(timeInDate);

        return timeInFormat;
    }

    /**
     * 현재 일시를 YYYY-MM-DD hh24:mi:ss,microsecond (23) 형태의 문자열로 변환한다.
     * @return 현재 일시 YYYY-MM-DD hh24:mi:ss,microsecond (23)
     */
    public static String getCurrentDatetimemicrok() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,");
        Date timeInDate = new Date();
        String timeInFormat = sdf.format(timeInDate);

        // nano second = 1,000,000,000분의 1초
        double nanoTime = (double) System.nanoTime() / 1000000000; // 1초 단위로 변환
        long milliTime = (long) ((nanoTime - (long) (nanoTime)) * 1000000);

        timeInFormat = timeInFormat + String.format("%06d", milliTime);

        return timeInFormat;
    }

    /**
     * 현재 시각을 HHMMSS(6) 형태의 문자열로 변환한다
     * @return 현재 시각 HHMMSS(6)
     */
    public static String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
        Date timeInDate = new Date();
        String timeInFormat = sdf.format(timeInDate);

        return timeInFormat;
    }

    /**
     * 현재 시각을 HHMM(4) 형태의 문자열로 변환한다
     * @return 현재 시각 HHMM(4)
     */
    public static String getCurrentHHmm() {
        SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
        Date timeInDate = new Date();
        String timeInFormat = sdf.format(timeInDate);

        return timeInFormat;
    }

    /**
     * 현재 시각을 HHmmssSSS(9) 형태의 문자열로 변환한다 {@link java.time.format.DateTimeFormatter}
     * @return 현재 시각 HHmmssSSS(9)
     */
    public static String getCurrentTimeMilli() {
        String timeInFormat = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HHmmssSSS"));
        return timeInFormat;
    }

    /**
     * 현재 시각을 지정한 format 형태의 문자열로 변환한다 {@link java.time.format.DateTimeFormatter}
     * @param format 변환할 format
     * @return 지정한 format 형태의 현재 시각
     */
    public static String getCurrentTimeByFormat(String format) {
        String timeInFormat = LocalDateTime.now().format(DateTimeFormatter.ofPattern(format));
        return timeInFormat;
    }

    /**
     * 현재 시각을 HHMMSS(6)+microsecond(6) 형태의 문자열로 변환한다.
     * @return 현재 시각 HHMMSS(6)+microsecond(6)
     */
    public static String getCurrentTimemicro() {
        SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
        Date timeInDate = new Date();
        String timeInFormat = sdf.format(timeInDate);

        // nano second = 1,000,000,000분의 1초
        double nanoTime = (double) System.nanoTime() / 1000000000; // 1초 단위로 변환
        long milliTime = (long) ((nanoTime - (long) (nanoTime)) * 1000000);

        timeInFormat = timeInFormat + String.format("%06d", milliTime);

        return timeInFormat;
    }

    /**
     * 현재 시각의 millisecond를 반환한다.
     * @return 현재 시각 millisecond
     */
    public static Long getCurrentSecmilli() {
        SimpleDateFormat sdf = new SimpleDateFormat("SSS");
        Date timeInDate = new Date();
        String timeInFormat = sdf.format(timeInDate);

        return Long.parseLong(timeInFormat);
    }

    /**
     * 현재 시각의 microsecond를 반환한다.
     * @return 현재 시각 microsecond
     */
    public static Long getCurrentSecmicro() {
        String timeInFormat = "";

        // nano second = 1,000,000,000분의 1초
        double nanoTime = (double) System.nanoTime() / 1000000000; // 1초 단위로 변환
        long milliTime = (long) ((nanoTime - (long) (nanoTime)) * 1000000);

        timeInFormat = timeInFormat + String.format("%06d", milliTime);

        return Long.parseLong(timeInFormat);
    }

    /**
     * 입력 년도가 4자리수의 적절한 연도인지 검사한다.
     * @param year 체크할 년도
     * @return ( true / false )
     */
    public static boolean isValidYear(String year) {
        boolean result = true;

        if (null == year) {
            return false;
        }

        if (4 != year.length()) {
            return false;
        }

        for (int i = 0; i < year.length(); i++) {
            if (!Character.isDigit(year.charAt(i))) {
                return false;
            }
        }

        Integer iyear = Integer.parseInt(year);

        if (1900 > iyear || iyear > 9999) {
            return false;
        }

        return result;
    }

    /**
     * 날짜(YYYYMMDD)의 정합성을 검사한다.
     * @param date 체크할 날짜
     * @return ( true / false )
     */
    public static boolean isValidDate(String date) {
        boolean result = true;

        if (null == date) {
            return false;
        }

        if (8 != date.length()) {
            return false;
        }

        for (int i = 0; i < date.length(); i++) {
            if (!Character.isDigit(date.charAt(i))) {
                return false;
            }
        }

        Integer iyear = Integer.parseInt(date.substring(0, 4));
        Integer imon = Integer.parseInt(date.substring(4, 6));
        Integer iday = Integer.parseInt(date.substring(6, 8));

        if (iyear < 1900 || imon < 1 || imon > 12 || iday < 1) {
            return false;
        }
        if ((iyear % 400 == 0 || (!(iyear % 100 == 0) && iyear % 4 == 0)) && imon == 2 && iday == 29) {
            return result;
        }

        int[] daysOfMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

        if (iday > daysOfMonth[imon - 1]) {
            return false;
        }

        return result;
    }

    /**
     * 시간(HHMMSS)의 정합성을 검사한다.
     * @param time 체크할 시간
     * @return ( true / false )
     */
    public static boolean isValidTime(String time) {
        boolean result = true;

        if (null == time) {
            return false;
        }

        if (6 != time.length()) {
            return false;
        }

        for (int i = 0; i < time.length(); i++) {
            if (!Character.isDigit(time.charAt(i))) {
                return false;
            }
        }

        Integer ihour = Integer.parseInt(time.substring(0, 2));
        Integer imin = Integer.parseInt(time.substring(2, 4));
        Integer isec = Integer.parseInt(time.substring(4, 6));

        if (imin < 0 || imin > 59 || isec < 0 || isec > 59 || ihour < 0 || ihour > 23) {
            return false;
        }

        return result;
    }

    /**
     * 두 일자의 선후 비교하여 반환하는 함수
     * @param d1 비교할 일자1
     * @param d2 비교할 일자2
     * @return ( 첫번째 일자가 작으면:-1 같으면:0 : 크면:1 )
     * @throws ParseException
     */
    public static int compareDate(String d1, String d2) throws ParseException {
        int result = 0;

//			if (!(DateUtil.isValidDate(d1) || DateUtil.isValidDate(d2))) {
//				throw AppCommonException.builder().errorCode(20338).build();
//			}

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date FirstDate = format.parse(d1);
        Date SecondDate = format.parse(d2);

        long calDate = FirstDate.getTime() - SecondDate.getTime();

        if (0 == calDate) {
            result = 0;
        } else if (0 < calDate) {
            result = 1;
        } else if (0 > calDate) {
            result = -1;
        }

        return result;
    }

    /**
     * 입력한 년도와 월에 해당하는 월의 일수(28~31)를 반환한다.
     * @param year  년도
     * @param month 월
     * @return 월의 일수
     */
    public static int getDaysInMonth(String year, String month) {
        int result = 0;

        // 입력값 체크
        // 연도 유효체크
//		if (!DateUtil.isValidYear(year)) {
//				throw AppCommonException.builder().errorCode(20338).build();
//		}

        // 월 유효체크
//		if (0 >= Integer.parseInt(month) && Integer.parseInt(month) > 12) {
////			throw AppCommonException.builder().errorCode(20338).build();
//		}

        Calendar cld = Calendar.getInstance();
        cld.set(Integer.parseInt(year), Integer.parseInt(month) - 1, 1);

        result = cld.getActualMaximum(Calendar.DAY_OF_MONTH);

        return result;
    }

    /**
     * 두 날짜 사이의 개월 수를 계산한다. (종료일자, 시작일자)
     * @param d1 시작일자
     * @param d2 종료일자
     * @return 두일자 사이 개월수
     */
    public static double getMonthsBetween(String d1, String d2) {
        // 파라미터 유효체크
//		if (null == d1 || null == d2) {
//			throw AppCommonException.builder().errorCode(20338).build();
//		} else if (!(DateUtil.isValidDate(d1) || DateUtil.isValidDate(d2))) {
//			throw AppCommonException.builder().errorCode(20338).build();
//		}

        // 변수 설정
        int iyear1 = Integer.parseInt(d1.substring(0, 4));
        int imonth1 = Integer.parseInt(d1.substring(4, 6));
        int iday1 = Integer.parseInt(d1.substring(6, 8));

        int iyear2 = Integer.parseInt(d2.substring(0, 4));
        int imonth2 = Integer.parseInt(d2.substring(4, 6));
        int iday2 = Integer.parseInt(d2.substring(6, 8));

        int idate1 = Integer.parseInt(d1);
        int idate2 = Integer.parseInt(d2);

        int date1_days = DateUtil.getDaysInMonth(d1.substring(0, 4), d1.substring(4, 6));
        int date2_days = DateUtil.getDaysInMonth(d2.substring(0, 4), d2.substring(4, 6));

        if ((iday1 == date1_days && iday2 == date2_days) || (iday1 == iday2)) {
            return (iyear1 - iyear2) * 12d + (imonth1 - imonth2);
        }

        double ddays = 0.0;
        String tmpDate = "";

        if (idate1 >= idate2) {
            ddays = iday1;

            tmpDate = String.format("%04d%02d%02d", iyear2, imonth2, iday1);

            if (DateUtil.isValidDate(tmpDate)) {
                ddays = date2_days;
            }

            ddays -= iday2;
        } else {
            ddays = iday2;

            tmpDate = String.format("%04d%02d%02d", iyear1, imonth1, iday2);

            if (DateUtil.isValidDate(tmpDate)) {
                ddays = date1_days;
            }
            ddays = iday1 - ddays;
        }
        return (ddays / 31.0) + (iyear1 - iyear2) * 12 + (imonth1 - imonth2);
    }

    /**
     * 두 날짜 사이의 일 수를 계산한다.
     * @param d1 시작일자
     * @param d2 종료일자
     * @return 두 일자 사이의 일수
     * @throws ParseException
     */
    public static int getDaysBetween(String d1, String d2) throws ParseException {
        long calDateDays = 0;
        long calDate = 0;
        int result = 0;

        // 날짜 유효체크
//			if (!DateUtil.isValidDate(d1)) {
//				throw AppCommonException.builder().errorCode(20338).build();
//			} else if (!DateUtil.isValidDate(d2)) {
//				throw AppCommonException.builder().errorCode(20338).build();
//			}

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date FirstDate = format.parse(d1);
        Date SecondDate = format.parse(d2);

        calDate = SecondDate.getTime() - FirstDate.getTime();
        calDateDays = calDate / (24 * 60 * 60 * 1000);

        result = (int) calDateDays;

        return result;
    }

    /**
     * 입력한 월이 몇 분기(1~4) 인지를 반환한다.
     *
     * @param month 입력월
     * @return 두 일자 사이의 분기수
     */
    public static int getQuarterYear(String month) {
        int imonth = Integer.parseInt(month);
        int result = 0;

        // 월 유효체크
//		if (0 >= Integer.parseInt(month) && Integer.parseInt(month) > 12) {
//			throw AppCommonException.builder().errorCode(20338).build();
//		}

        // 1 : 1~3
        if (0 < imonth && imonth <= 3) {
            result = 1;
        }
        // 2 : 4~6
        else if (3 < imonth && imonth <= 6) {
            result = 2;
        }
        // 3 : 7~9
        else if (6 < imonth && imonth <= 9) {
            result = 3;
        }
        // 4 : 10~12
        else if (9 < imonth && imonth <= 12) {
            result = 4;
        }

        return result;
    }

    /**
     * 입력한 월이 속한 분기(1~4)의 첫 번째 날 또는 마지막 날을 반환한다.
     * @param year  년
     * @param month 월
     * @param kind  (1:시작날짜 2:끝일자)
     * @return 입력한 월이 속한 분기(1~4)의 첫 번째 날 또는 마지막 날
     */
    public static String getQuarterStartEndDate(String year, String month, int kind) {
        String result = null;
        String mon = null;

        int iquater = getQuarterYear(month);

        if (1 == kind) {
            // 시작 날짜
            iquater = (iquater - 1) * 3 + 1; // 분기시작월

            mon = String.format("%02d", iquater);

            result = year + mon + "01";
        } else if (2 == kind) {
            // 끝 날짜
            iquater = iquater * 3; // 분기종료월

            mon = String.format("%02d", iquater);

            result = year + mon + String.valueOf(DateUtil.getDaysInMonth(year, mon));
        }

        return result;
    }

    /**
     * 입력한 일자가 몇번째 요일인지를 반환한다
     * @param year  년
     * @param month 월
     * @param day   일
     * @return (0: 일요일 … 6: 토요일)
     * @throws ParseException
     */
    public static int getWeekday(String year, String month, String day) throws ParseException {
        String date = year + month + day;
        int dayNum = 0;

//			// 연도 유효체크
//			if (!DateUtil.isValidYear(year)) {
//				throw AppCommonException.builder().errorCode(20338).build();
//			}
//
//			// 월 유효체크
//			if (0 >= Integer.parseInt(month) || Integer.parseInt(month) > 12) {
//				throw AppCommonException.builder().errorCode(20338).build();
//			}
//
//			// 일자 유효체크
//			if (0 >= Integer.parseInt(day) || Integer.parseInt(day) > DateUtil.getDaysInMonth(year, month)) {
//				throw AppCommonException.builder().errorCode(20338).build();
//			}

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date nDate = dateFormat.parse(date);

        Calendar cal = Calendar.getInstance();
        cal.setTime(nDate);

        dayNum = cal.get(Calendar.DAY_OF_WEEK) - 1;

        return dayNum;
    }

    /**
     * 입력한 일자가 몇번째 요일인지를 반환한다
     *
     * @param date 일자
     * @return (0: 일요일 … 6: 토요일)
     * @throws ParseException
     */
    public static int getWeekday(String date) throws ParseException {
        int dayNum = 0;

//			// 날짜 유효체크
//			if (!DateUtil.isValidDate(date)) {
//				throw AppCommonException.builder().errorCode(20338).build();
//			}

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date nDate = dateFormat.parse(date);

        Calendar cal = Calendar.getInstance();
        cal.setTime(nDate);

        dayNum = cal.get(Calendar.DAY_OF_WEEK) - 1;

        return dayNum;
    }

    /**
     * 입력한 일자(date)보다 몇 년(year) 후의 일자(dest)를 계산한다
     * @param date 일자
     * @param year 계산할 년수
     * @return 계산된 일자
     * @throws ParseException
     */
    public static String addYear(String date, String year) throws ParseException {
        String result = null;

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

        Calendar cal = Calendar.getInstance();
        Date ddate = format.parse(date);
        cal.setTime(ddate);

        cal.add(Calendar.YEAR, Integer.parseInt(year));

        result = format.format(cal.getTime());

        return result;
    }

    /**
     * 입력한 일자(date)보다 몇 개월(month) 후의 일자(dest)를 계산한다.
     * @param date  일자
     * @param month 계산할 월
     * @return 계산된 일자
     * @throws ParseException
     */
    public static String addMonth(String date, String month) throws ParseException {
        String result = null;

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

        Calendar cal = Calendar.getInstance();
        Date ddate = format.parse(date);
        cal.setTime(ddate);

        cal.add(Calendar.MONTH, Integer.parseInt(month));

        result = format.format(cal.getTime());

        return result;
    }

    /**
     * 입력한 일자(date)보다 몇 일(day) 후의 일자(dest)를 계산한다. 입력한 일자는 'yyyyMMdd' format 이여하 하고,
     * 결과값도 'yyyyMMdd' format 으로 리턴된다.
     * @param date 일자
     * @param day  계산 일수
     * @return 계산된 일자
     * @throws ParseException
     */
    public static String addDay(String date, int day) throws ParseException {
        String result = null;

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

        Calendar cal = Calendar.getInstance();
        Date ddate = format.parse(date);
        cal.setTime(ddate);

        cal.add(Calendar.DATE, day);

        result = format.format(cal.getTime());

        return result;
    }

    /**
     * 입력한 시간(time)보다 몇 시간(hour) 후의 시간(dest)을 계산한다.
     * @param time 시간
     * @param hour 계산할 시간
     * @return 계산된 시간
     * @throws ParseException
     */
    public static String addHour(String time, int hour) throws ParseException {
        String result = null;

        SimpleDateFormat format = new SimpleDateFormat("HHmmss");

        Calendar cal = Calendar.getInstance();
        Date ddate = format.parse(time);
        cal.setTime(ddate);

        cal.add(Calendar.HOUR, hour);

        result = format.format(cal.getTime());

        return result;
    }

    /**
     * 입력한 시간(time)보다 몇 분(minute) 후의 시간(dest)을 계산한다.
     * @param time 시간
     * @param min  계산할 분
     * @return 계산된 시각
     * @throws ParseException
     */
    public static String addMinute(String time, int min) throws ParseException {
        String result = null;

        SimpleDateFormat format = new SimpleDateFormat("HHmmss");

        Calendar cal = Calendar.getInstance();
        Date ddate = format.parse(time);
        cal.setTime(ddate);

        cal.add(Calendar.MINUTE, min);

        result = format.format(cal.getTime());

        return result;
    }

    /**
     * 입력한 시간(time)보다 몇 초(minute) 후의 시간(dest)을 계산한다.
     * @param time 시간
     * @param sec  계산할 초
     * @return 계산된 시각
     * @throws ParseException
     */
    public static String addSecond(String time, int sec) throws ParseException {
        String result = null;

        SimpleDateFormat format = new SimpleDateFormat("HHmmss");

        Calendar cal = Calendar.getInstance();
        Date ddate = format.parse(time);
        cal.setTime(ddate);

        cal.add(Calendar.SECOND, sec);

        result = format.format(cal.getTime());

        return result;
    }

    /**
     * 입력받은 time 에 원하는 시간을 가감하여 산출 {@link java.time.temporal.ChronoUnit} 시간 문자열 파싱을
     * 위해, 입력 time 값은 정해진 형식을 만족해야 함 ex> 2020-11-17T15:06:09
     * @param time        시간
     * @param amountToAdd 계산할 단위의 시간
     * @param unit        계산할 단위 지정
     * @param plus        true 시 plus 계산. false 시 minus 계산.
     * @return 계산된 시간
     */
    public static String addTime(String time, long amountToAdd, ChronoUnit unit, boolean plus) {
        String result = null;

        LocalDateTime localDateTime = LocalDateTime.parse(time);

        if (plus) {
            result = localDateTime.plus(amountToAdd, unit).toString();
        } else {
            result = localDateTime.minus(amountToAdd, unit).toString();
        }

        return result;
    }

    /**
     * 입력받은 두 시각의 차를 산출. 시간 문자열 파싱을 위해, 입력 time 값은 정해진 형식을 만족해야 함 ex>
     * @param time1 계산할 시간 1
     * @param time2 계산할 시간 2
     * @return 계산된 시간. milliseconds.
     */
    public static long diffTime(String time1, String time2) {
        long result = 0;

        LocalDateTime localDateTime1 = LocalDateTime.parse(time1);
        LocalDateTime localDateTime2 = LocalDateTime.parse(time2);

        ZonedDateTime zdt1 = ZonedDateTime.of(localDateTime1, ZoneId.systemDefault());
        ZonedDateTime zdt2 = ZonedDateTime.of(localDateTime2, ZoneId.systemDefault());
        // Instant 객체(타임스탬프) 이용
        result = zdt1.toInstant().toEpochMilli() - zdt2.toInstant().toEpochMilli();

        return result;
    }

    /**
     * 입력일자의 마지막 날을 반환한다.
     * @param date 일자
     * @return 마지막 날짜 리턴
     */
    public static String getLastDay(String date) {
        String result = null;
        String days = null;
        int day = 0;

        day = DateUtil.getDaysInMonth(date.substring(0, 4), date.substring(4, 6));
        days = String.format("%02d", day);
        result = date.substring(0, 6) + days;

        return result;
    }

    /**
     * 입력받은 시각을 지정한 format 형태의 문자열로 변환한다 {@link java.time.format.DateTimeFormatter}
     * 시간 문자열 파싱을 위해, 입력 time 값은 정해진 형식을 만족해야 함 ex> 2020-11-17T15:06:09
     * @param time   변환할 시각
     * @param format 변환할 format
     * @return 지정한 format 형태의 입력받은 시각
     */
    public static String getTimeByFormat(String time, String format) {
        String timeInFormat = null;

        LocalDateTime localDateTime = LocalDateTime.parse(time);
        timeInFormat = localDateTime.format(DateTimeFormatter.ofPattern(format));

        return timeInFormat;
    }

    /**
     * 지정한 format 형태의 시각을 1970-01-01T00:00:00Z 기점의 Unix-epoch milliseconds 로 변환한다.
     * @param time   변환할 시각
     * @param format 변환할 format
     * @return 변환된 시각. milliseconds.
     */
    public static long getEpochMilliFromFormatTime(String time, String format) {
        long milliTime = 0;

        LocalDateTime localDateTime = LocalDateTime.parse(time, DateTimeFormatter.ofPattern(format));
        ZonedDateTime zdt = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());
        // Instant 객체(타임스탬프) 이용
        milliTime = zdt.toInstant().toEpochMilli();

        return milliTime;
    }

    /**
     * 1970-01-01T00:00:00Z 기점의 Unix-epoch milliseconds 를 system time zone 의 시간 문자열로
     * 변환한다.
     * @param time 변환할 시각
     * @return 변환된 시각.
     */
    public static String getTimeByEpochMilli(long epochMilli) {
        String resStr = null;
        String timeFormat = "yyyy-MM-dd HH:mm:ss.SSS";

        Instant instant = Instant.ofEpochMilli(epochMilli);
        ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
        resStr = zdt.format(DateTimeFormatter.ofPattern(timeFormat)).toString();

        return resStr;
    }
}
