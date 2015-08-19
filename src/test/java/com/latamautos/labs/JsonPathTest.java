package com.latamautos.labs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.jayway.jsonpath.Filter;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static com.jayway.jsonpath.Criteria.where;
import static com.jayway.jsonpath.Filter.filter;
import static com.jayway.jsonpath.JsonPath.parse;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Unit test for simple JsonPath usage.
 */
public class JsonPathTest {

    final Logger logger = LoggerFactory.getLogger(TolerantReader.class);

    String json;

    @Before
    public void loadJson() {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("data.json");
        json = convertStreamToString(is);
    }

    @Test
    public void testJsonIsLoaded() {
        assertNotNull(json);
    }

    @Test
    public void testBuscaPorId() {
        Long id = 13L;
        Filter filtro = filter(
                where("id").is(id)
                .and("id").type(Number.class)
        );
        List<Map<String, Object>> result = parse(json).read("$..[?]", filtro);

        //logger.info(prettyPrintJsonString(result.toString()));
        assertThat(result.isEmpty(), is(false));
        assertThat(result.get(0).get("id").toString(), equalTo(id.toString()));
    }

    @Test
    public void testBuscaAutorPorWildcard() {
        Filter filtro = filter(
                where("author").regex(Pattern.compile(".*wAU.*", Pattern.CASE_INSENSITIVE))
        );
        List<Map<String, Object>> result = parse(json).read("$..[?]", filtro);

        //logger.info(prettyPrintJsonString(data.toString()));
        assertThat(result.isEmpty(), is(false));
        assertThat(result.get(0).get("author").toString().toLowerCase(), containsString("wau"));
    }

    @Test
    public void testObtenerListaConElPrimerIdYElPrimerNombreQueEncuentreEnElJson() {
        List<String> list = parse(json).limit(1).read("$..id");
        List<String> nombreList = parse(json).limit(1).read("$..nombre");

        list.addAll(nombreList);
        //logger.info(prettyPrintJsonString(id.toString()));
        assertThat(list.isEmpty(), is(false));
        assertThat(list.size(), is(2));
    }

    @Test
    public void testObtenerObjetosConISBNYPrecio() {
        List<String> list = parse(json).read("$..[?(@.isbn) && ?(@.price)]");

        //logger.info(prettyPrintJsonString(id.toString()));
        assertThat(list.isEmpty(), is(false));
    }

    static String convertStreamToString(InputStream is) {
        if (is == null) {
            return null;
        }
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    static String prettyPrintJsonString(String str) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(str);
        return gson.toJson(je);
    }

}
