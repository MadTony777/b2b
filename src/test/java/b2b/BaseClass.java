package b2b;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BaseClass {
    private String arg = System.getProperty("arg", "stage");
    public String environment = arg;
    public static final String url_stg = "http://esbext-stage.vsk.ru:8501/cxf/partners/policy";
    public static final String url_tst = "http://esb-test01:8181/cxf/partners/policy";
    public static final String paths = "src/test/java/b2b/Examples/";
    public static Logger log = LoggerFactory.getLogger(UnitTests.class);
    public static void pauseMethod(int time) {
        try {
            Thread.sleep(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<String> getValuesForGivenKey(String jsonArrayStr, String key) {
        JSONArray jsonArray = new JSONArray(jsonArrayStr);
        return IntStream.range(0, jsonArray.length())
                .mapToObj(index -> ((JSONObject) jsonArray.get(index)).optString(key))
                .collect(Collectors.toList());
    }


    public static String environmentURL(String environment) {
        String urlKibanaSearch="";
        switch (environment) {
            case "stage":
                urlKibanaSearch = "esb-stage";
                break;
            case "test":
                urlKibanaSearch = "esb-test";
                break;
        }
        return urlKibanaSearch;
    }

    public static String requesttURL(String environment) {
        String URL="";
        switch (environment) {
            case "stage":
                URL = url_stg;
                break;
            case "test":
                URL = url_tst;
        }
        return URL;
    }
}
