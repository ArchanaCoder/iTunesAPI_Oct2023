package utilities;

import configs.TestEnvProperties;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

public class APICommonMethods {

    TestEnvProperties envProperties = new TestEnvProperties();

    public int APIStatusCode (String url){
        io.restassured.response.Response status = given().contentType(ContentType.JSON).get(url);
        Integer statusCode = status.getStatusCode();
        return statusCode;
    }

    public boolean secure(String url){
        Boolean secure = false;
        if(url.contains("https:")){
            secure = true;
        }
        return secure;
    }

    public long APIResponseTime (String url){
        io.restassured.response.Response res = given().contentType(ContentType.JSON).get(url);
        long res_time = res.getTime();
        return res_time;
    }

}
