package skcnc.msa3.framework.database;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MetaHashMap extends HashMap<String,Object> {
    private static final long serialVersionUID = 3984917240167871205L;

    @Override
    public String toString() {
        return "MetaHashMap [@" + hashCode() + ", entrySet=" + super.entrySet().toString() + "]";
    }

    @Override
    public Object get(Object key) {
        return super.get(toLower(key));
    }

    @Override
    public Object put(String key, Object value) {
        return super.put(toLower(key), value);
    }

    @Override
    public Object putIfAbsent(String key, Object value) {
        return super.putIfAbsent(toLower(key), value);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void putAll(Map<? extends String, ? extends Object> map) {
        if(map == null) return;
        Set set = map.entrySet();
        Iterator it = set.iterator();
        while(it.hasNext()) {
            Entry<String,Object> entry = (Entry<String,Object>) it.next();
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public boolean containsKey(Object key) {
        return super.containsKey(toLower(key));
    }

    private String toLower(Object key) {
        return key.toString().toLowerCase();
    }
    private String toLower(String key) {
        return key.toLowerCase();
    }
}
