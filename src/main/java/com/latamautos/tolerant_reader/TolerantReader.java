package com.latamautos.tolerant_reader;

import com.google.gson.Gson;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.latamautos.tolerant_reader.annotation.PossibleValues;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import static com.jayway.jsonpath.JsonPath.parse;

/**
 * Created by andreslopez on 8/6/15.
 */
public class TolerantReader {

    final static Logger logger = LoggerFactory.getLogger(TolerantReader.class);


    public static <T> List getResultSetByClass(Class<T> clazz, String json) {


        return getResultSetByClass(clazz, json, new ArrayList<String>());
    }

    public static <T> List getResultSetByClass(Class<T> clazz, String json, List<String> defaultPossibleKeys) {

        List<String> possibleKeys = getPossibleKeysOfType(clazz);
        possibleKeys.addAll(defaultPossibleKeys);
        List<T> resultSet = new ArrayList<T>();

        for (String key : possibleKeys) {
            resultSet.addAll(getResultSetByKey(clazz, json, key));
        }


        return resultSet;
    }

    private static <T> List<T> getResultSetByKey(Class<T> clazz, String json, String key) {

        Gson gson = new Gson();
        List<T> resultSet = new ArrayList<T>();
        if (BeanUtils.isSimpleProperty(clazz)) {
            return JsonPath.read(json, "$.." + key + "[*]");

        } else {
            List<LinkedHashMap> res = JsonPath.read(json, "$.." + key + "[*]");
            for (LinkedHashMap objectAsMap : res) {
                resultSet.add(getSingleResultByClass(clazz, gson.toJson(objectAsMap)));

            }
        }
        return resultSet;

    }

    private static <T> List<String> getPossibleKeysOfType(Class<T> clazz) {
        List<String> possibleKeys = new ArrayList<String>();
        possibleKeys.add(clazz.getSimpleName());
        for (Annotation annotation : clazz.getAnnotations()) {
            if (annotation.annotationType().equals(PossibleValues.class)) {
                PossibleValues possibleValues = (PossibleValues) annotation;
                possibleKeys.addAll(Arrays.asList(possibleValues.also()));
            }
        }
        return possibleKeys;

    }

    public static Object toObject(Class clazz, String value) {
        String simpleClassName = clazz.getSimpleName();

        if (Boolean.class == clazz || simpleClassName.equals("boolean")) return Boolean.parseBoolean(value);
        if (Byte.class == clazz || simpleClassName.equals("byte")) return Byte.parseByte(value);
        if (Short.class == clazz || simpleClassName.equals("short")) return Short.parseShort(value);
        if (Integer.class == clazz || simpleClassName.equals("int")) return Integer.parseInt(value);
        if (Long.class == clazz || simpleClassName.equals("long")) return Long.parseLong(value);
        if (Float.class == clazz || simpleClassName.equals("float")) return Float.parseFloat(value);
        if (Double.class == clazz || simpleClassName.equals("double")) return Double.parseDouble(value);
        return value;
    }

    private static <T> List<String> getPossibleKeysOfField(Field field) {
        List<String> possibleKeys = new ArrayList<String>();
        possibleKeys.add(field.getName());
        PossibleValues pv = field.getAnnotation(PossibleValues.class);
        if (pv != null) {
            possibleKeys.addAll(Arrays.asList(pv.also()));
        }

        possibleKeys.addAll(getPossibleKeysOfType(field.getType()));
        return possibleKeys;

    }

    private static <T> T getSingleResultByClass(Class<T> clazz, String json) {
        T o = null;
        try {
            o = clazz.newInstance();
            for (Field field : clazz.getDeclaredFields()) {
                if (isPrimitiveField(field)) {
                    setPrimitiveField(o, json, field);

                } else {
                    if (isListField(field)) {
                        setListField(o, json, field);
                    } else {
                        setObjectField(json, o, field);
                    }

                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return o;
    }

    private static boolean isListField(Field field) {
        return field.getType().isAssignableFrom(List.class);
    }

    private static boolean isPrimitiveField(Field field) {
        return BeanUtils.isSimpleProperty(field.getType());
    }

    private static <T> void setObjectField(String json, T o, Field field) throws IllegalAccessException {
        field.setAccessible(true);
        field.set(o, getSingleResultByClass(field.getType(), json));
    }

    private static <T> void setListField(T o, String json, Field field) throws IllegalAccessException {
        ParameterizedType integerListType = (ParameterizedType) field.getGenericType();
        Class<?> genericType = (Class<?>) integerListType.getActualTypeArguments()[0];
        field.setAccessible(true);
        field.set(o, getResultSetByClass(genericType, json, getPossibleKeysOfField(field)));
    }

    private static <T> void setPrimitiveField(T o, String json, Field field) throws IllegalAccessException {
        List<String> possibleKeys = getPossibleKeysOfField(field);
        for (String str : possibleKeys) {
            Object fieldValue = searchByKey(str, json);
            if (fieldValue != null) {
                field.setAccessible(true);
                field.set(o, toObject(field.getType(), (String) fieldValue));
                System.out.println(fieldValue);
                break;
            }
        }
    }

    private static Object searchByKey(String key, String json) {
        List res = JsonPath.using(Configuration.builder().options(Option.SUPPRESS_EXCEPTIONS).build()).parse(json).limit(1).read("$.." + key);
        if (res != null && res.size() > 0) {
            return res.get(0);
        }
        return null;
    }

}
