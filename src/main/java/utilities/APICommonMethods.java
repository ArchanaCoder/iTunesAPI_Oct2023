package utilities;

import configs.TestEnvProperties;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;

import java.util.ArrayList;
import java.util.HashMap;

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

    public Boolean limit_check_byArtist (JsonPath res,String limit){
        ArrayList<HashMap> res_values = new ArrayList<>();
        HashMap<String, String> values = new HashMap<>();
        HashMap<String, Integer> limit_check = new HashMap<>();
        Boolean valid = false;
        //JsonPath response = new JsonPath(res.toString());
        res_values = res.getJsonObject("results");
        int n = res_values.size();
        int i =0;
        int count=1;
        while (i < n) {
            values = res_values.get(i);
            if(limit_check.containsKey(values.get("artistName"))){
                count++;
                limit_check.put(values.get("artistName"),count);
            }else {
                count =1;
                limit_check.put(values.get("artistName"), count);
            }
            i++;
        }
        for(Integer s: limit_check.values()){
            if(String.valueOf(s).equals(limit)){
                valid = true;
            }
            else  valid = false;
        }
        return valid;
    }

}
