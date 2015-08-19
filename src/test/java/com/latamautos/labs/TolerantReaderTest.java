package com.latamautos.labs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.latamautos.labs.dto.Book;
import com.latamautos.labs.dto.ChildDTO;
import com.latamautos.labs.dto.ParentDTO;
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

    @Test
    public void testGetSingleResultAsMap() {
        Map<String, Object> dataMap = TolerantReader.getSingleResultAsMap(needles_array, haystack_json);
         logger.info(prettyPrintJsonString(dataMap.toString()));
        assertThat(dataMap.get(needles_array[0]), notNullValue());
        assertThat(dataMap.size(), equalTo(needles_array.length));
    }

    @Test
    public void testGetSingleResultAsObject() {
        ParentDTO parent = TolerantReader.getSingleResult(ParentDTO.class, haystack_json);
        assertEquals(parent.getClass(), ParentDTO.class);
        assertEquals(parent.getLongPrimitive(), 111l);
        ChildDTO child = TolerantReader.getSingleResult(ChildDTO.class, haystack_json);
        assertEquals(child.isEsActivo(), true);
        assertThat(TolerantReader.getSingleResult(Long.class, haystack_json), equalTo(null));
    }

    @Test
    public void testGetResultSetAsListOfBooks() {
        List<Book> bookList = TolerantReader.getResultSet(Book.class, haystack_json);
        assertThat(bookList.size(), equalTo(4));
        assertThat(bookList.get(0).getId(), is(11L));
    }

    @Test
    public void testGetChildDTOFromJson() {
        ChildDTO child = TolerantReader.getSingleResult(ChildDTO.class, haystack_json);
        logger.info(child.toString());
        assertThat(child.getNombre(), notNullValue());
        assertThat(child.isEsActivo(), is(Boolean.TRUE));
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
