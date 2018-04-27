package demo.test.com.codingtestdemo;

import junit.framework.TestCase;

import java.util.ArrayList;

import demo.test.com.codingtestdemo.model.TestBean;

/**
 * Created by soundmax on 2018/4/26.
 */
public class DataStoreTest extends TestCase {

    public void testAnalyseJsonData() throws Exception {

        DataStore dataStore = new DataStore();

        String json = "{\"data\" : [{\"image\" : \"a\", \"title\" : \"demo\", \"description\" : \"demo\"} ]}";

        ArrayList<TestBean> testBeen = dataStore.analyseJsonData(json);


        assertEquals(testBeen.size(), 1);
        assertEquals(testBeen.get(0).getImage(), "a");
        assertEquals(testBeen.get(0).getTitle(), "demo");
        assertEquals(testBeen.get(0).getDescription(), "demo");
    }
}