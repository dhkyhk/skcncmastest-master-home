package skcnc.msa3.framework.utils;

import java.math.BigDecimal;
import java.util.Map;

public class MapUtil {

    public static String getString(Map<String,Object> map, Object key) {
        String rtn = (String) map.get(key);
        return rtn;
    }
    public static Integer getInteger(Map<String,Object> map, Object key) {
        Number rtn = (Number) map.get(key);
        if(rtn == null) return null;
        return rtn.intValue();
    }
    public static Long getLong(Map<String,Object> map, Object key) {
        Number rtn = (Number) map.get(key);
        if(rtn == null) return null;
        return rtn.longValue();
    }
    public static Double getDouble(Map<String,Object> map, Object key) {
        Number rtn = (Number) map.get(key);
        if(rtn == null) return null;
        return rtn.doubleValue();
    }
    public static BigDecimal getBigDecimal(Map<String,Object> map, Object key) {
        Object rtn = map.get(key);
        if(rtn instanceof BigDecimal) {
            return (BigDecimal) rtn;
        }else {
            return new BigDecimal(rtn.toString());
        }
    }
}
