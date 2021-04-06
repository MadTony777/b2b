package b2b;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Kibana extends BaseClass{

    public static String searchByCID(String environment, String cid) {

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        String result = "";
        String urlKibanaSearch = environmentURL( environment);
        try {
            Date today = Calendar.getInstance().getTime();
            String newstring = new SimpleDateFormat("yyyy.MM.dd").format(today);
            String url = "http://elog.vsk.ru:9200/" + urlKibanaSearch + "-" + newstring + "/_search?q=%20message:%20%22" + cid + "%22%20&size=500";
            log.info("URL is : " + url);
            HttpGet request = new HttpGet(url);
            request.addHeader("content-type", "application/json");
            request.addHeader("Accept", "application/json");
            HttpResponse response = httpClient.execute(request);
            String responseBody = EntityUtils.toString(response.getEntity());
            result = responseBody;
            log.info("Getting Kibana log...");
        } catch (Exception ex) {
            log.error("!!!!!!!!!!!!!!!!!!!!Something goes wrong!!!!!!!!!!!!!!!!!!!!");
        }
        return result;
    }

    public static String searchByParameters(String environment, String cid, String searchField1) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        String result = "";
        String urlKibanaSearch = environmentURL( environment);
        String searchField2=cid;
        try {
            Date today = Calendar.getInstance().getTime();
            String newstring = new SimpleDateFormat("yyyy.MM.dd").format(today);
            String url = "http://elog.vsk.ru:9200/" + urlKibanaSearch + "-" + newstring + "/_search?q=%20message:%20%22" + searchField1 + "%22%20AND%20%22" + searchField2 + "%22%20&size=500";
            URI myURI = new URI(url);
            log.info("URL is : " + myURI);
            HttpGet request = new HttpGet(myURI);
            request.addHeader("content-type", "application/json");
            request.addHeader("Accept", "application/json");
            HttpResponse response = httpClient.execute(request);
            String responseBody = EntityUtils.toString(response.getEntity());
            result = responseBody;
            log.info("Getting Kibana log...");
        } catch (Exception ex) {
            log.error("!!!!!!!!!!!!!!!!!!!!Something goes wrong!!!!!!!!!!!!!!!!!!!!");
        }
        return result;
    }
}
