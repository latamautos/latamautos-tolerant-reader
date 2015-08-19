package com.latamautos.labs;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.latamautos.labs.annotation.PossibleValues;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.jayway.jsonpath.JsonPath.parse;

/**
 * Created by andreslopez on 8/6/15.
 */
public class TolerantReader {

    final static Logger logger = LoggerFactory.getLogger(TolerantReader.class);

    public static Map<String, Object> getSingleResultAsMap(String[] keys, String json) {
        Map<String, Object> data = new LinkedHashMap<String, Object>();
        List tmp;
        for (String i : keys) {
            tmp = parse(json).limit(1).read("$.." + i);
            data.put(i, tmp.size() > 0 ? tmp.get(0) : null);
        }
        return data;
    }

    public static <T> T getSingleResult(Class<T> clazz, String json) {
        Object val;
        JsonObject jsonObj = new JsonObject();
        Object ls = findValue(clazz.getSimpleName(), json, getFieldPossibleValues(clazz));
        if (ls != null && ls instanceof LinkedHashMap) {
            json = new Gson().toJson(ls);
        }
        for (Field field : clazz.getDeclaredFields()) {
            val = findValue(field.getName(), json, getFieldPossibleValues(field));
            if (val != null) {
                jsonObj.addProperty(field.getName(), String.valueOf(val));
            }
        }
        T res;
        try {
            res = new Gson().fromJson(jsonObj, clazz);
        } catch (RuntimeException e) {
            return null;
        }
        return res;
    }

    public static <T> List<T> getResultSet(Class<T> clazz, String json) {
        List<T> list = new ArrayList<T>();
        try {
            T res;
            List ls = searchInJsonByKey(clazz.getSimpleName(), json);
            if (ls.size() > 0 && ls.get(0) != null && ls.get(0) instanceof List) {
                ls = (List) ls.get(0);
            }
            Gson gson;
            for (Object lss : ls) {
                gson = new Gson();
                res = gson.fromJson(gson.toJson(lss), clazz);
                list.add(res);
            }
        } catch (RuntimeException e) {
            return null;
        }
        return list;
    }

    private static Object findValue(String key, String json, String[] possibleValues) {
        List found = searchInJsonByKey(key, json);
        if (found.size() == 0) {
            for (String s : possibleValues) {
                found = searchInJsonByKey(s, json);
                if (found.size() > 0 && found.get(0) != null) {
                    return found.get(0);
                }
            }
        }
        return found.isEmpty() ? null : found.get(0);
    }

    private static List searchInJsonByKey(String key, String json) {
        List res = parse(json).limit(1).read("$.." + key);
        boolean hasUppercase = !key.equals(key.toLowerCase());
        if (res.size() == 0 && hasUppercase) {
            res = parse(json).limit(1).read("$.." + key.toLowerCase());
        }
        return res;
    }

    private static String[] getFieldPossibleValues(AnnotatedElement element) {
        PossibleValues pv = element.getAnnotation(PossibleValues.class);
        return (pv != null) ? pv.also() : new String[]{};
    }

}
