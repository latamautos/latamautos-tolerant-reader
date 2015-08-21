package com.latamautos.tolerant_reader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.latamautos.tolerant_reader.dto.Book;
import com.latamautos.tolerant_reader.dto.ChildDTO;
import com.latamautos.tolerant_reader.dto.ParentDTO;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Unit test for TolerantReader class.
 */
public class TolerantReaderTest {

    final Logger logger = LoggerFactory.getLogger(TolerantReader.class);

    String haystack_json;
    String[] needles_array;

    @Before
    public void loadJsonAndSetNeedles() {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("data.json");
        haystack_json = convertStreamToString(is);
        needles_array = new String[]{"string", "integer", "double", "boolean", "dontExist", "expensive"};
    }



    static String convertStreamToString(java.io.InputStream is) {
        if (is == null) {
            return null;
        }
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    static String prettyPrintJsonString(String str) {
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(str);
        return gson.toJson(je);
    }

}
