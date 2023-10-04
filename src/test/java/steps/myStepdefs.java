package steps;

import configs.TestEnvProperties;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ResponseBody;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.testng.AssertJUnit;
import utilities.APICommonMethods;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.apache.logging.log4j.core.util.Constants.MILLIS_IN_SECONDS;

public class myStepdefs {

    TestEnvProperties env = new TestEnvProperties();

    APICommonMethods apiCommonMethods = new APICommonMethods();

    ResponseBody Response;
    Integer resultCount=0;

    @Given("user navigates to itunes")
    public void userNavigatesToItunes() {
        given().contentType(ContentType.JSON);
    }

    @When("user enters {string} for a search")
    public void userEntersForASearch(String term) {
        Response = RestAssured.when().get(env.getApiurl()+"/search?term="+term).getBody();
        resultCount= Response.jsonPath().get("resultCount");
        if(resultCount!=0){
            System.out.println(env.getApiurl()+"/search?term="+term);
        }
        Assert.assertTrue(!Response.jsonPath().get("resultCount").equals(0));
    }

    @When("user enters {string} for a search and validate schema")
    public void userEntersForASearchandValidateSchema(String term) {
        File schema = new File(System.getProperty("user.dir")+ File.separator +
                "src"+ File.separator + "main"+ File.separator + "resources"+ File.separator +"search_schema.json");

        given().get(env.getApiurl()+"/search?term="+term).
                then().
        body(matchesJsonSchema(schema));

    }

    @When("user enters {string} for a Lookup")
    public void userEntersForALookupSearch(String lookup) {
        Response = RestAssured.when().get(env.getApiurl()+"/lookup?"+lookup).getBody();
        resultCount= Response.jsonPath().get("resultCount");
        if(!(resultCount >0)){
            System.out.println(env.getApiurl()+"/lookup?"+lookup);
            System.out.println("resultCount: " +resultCount);
        }
        Assert.assertTrue(resultCount>0);
    }

    @Then("verify viewURLs are clickable")
    public void verifyViewURLsAreClickable() {

        ArrayList<HashMap> res_values = new ArrayList<>();
        HashMap <String,String> values = new HashMap<>();
        JsonPath res = new JsonPath(Response.asString());
        res_values = res.getJsonObject("results");
        String[] res_url = {"artistViewUrl","collectionViewUrl","trackViewUrl","previewUrl"};
        int n = res_values.size();
        int i =0;
        while(i<n){
            values = res_values.get(i);
            for(String s:res_url){
                if(values.containsKey(s)) {
                    if (values.get(s).length() > 0) {
                        AssertJUnit.assertTrue(apiCommonMethods.APIStatusCode(values.get(s)) == 200);
                    }
                }
            }
            i++;
        }
    }


    @Then("search results should not be empty")
    public void searchResultsShouldNotBeEmpty() {

        resultCount= Response.jsonPath().get("resultCount");
        //  Assert.assertTrue(Response.g);
        Assert.assertTrue(!Response.jsonPath().get("resultCount").equals(0));
    }


    @Given("user looks for searchUrl to navigates to itunes")
    public void userLooksForSearchUrlToNavigatesToItunes() {
        given().contentType(ContentType.JSON);
    }

    @Then("verify searchURL contains https and secure url to navigate")
    public void verifySearchURLContainsHttpsAndSecureUrlToNavigate() {

            AssertJUnit.assertTrue(apiCommonMethods.secure(env.getApiurl()));
        }

    @Then("verify response time for {string} search less than {int}seconds")
    public void verifyResponseTimeForSearchLessThanSeconds(String term, int sec) {
        String url = env.getApiurl()+"/search?term="+term;
        long millisecond = sec * MILLIS_IN_SECONDS;
        AssertJUnit.assertTrue(apiCommonMethods.APIResponseTime(url)<millisecond);
    }

    @And("search results contains text {string}")
    public void resultsContainsValues(String term) {

        term = StringUtils.substringBefore(term,"&");
        term = StringUtils.replace(term, "+"," ");

        ArrayList<HashMap> res_values = new ArrayList<>();
        HashMap <String,String> values = new HashMap<>();
        String check_values = "";
        Boolean found = false;
        JsonPath res = new JsonPath(Response.asString());
        res_values = res.getJsonObject("results");
        int n = res_values.size();
        int i =0;
        while(i<n){
            values = res_values.get(i);
            check_values = values.toString();
            if(check_values.toLowerCase().contains(term.toLowerCase())){
                found = true;
            }
            AssertJUnit.assertTrue(found);
            i++;
        }
    }

    @And("lookup results contains values {string}")
    public void lookupResultsContainsValues(String lookup) {

        ArrayList<HashMap> res_values = new ArrayList<>();
        HashMap <String,String> values = new HashMap<>();
        //String check_values = "";
        Boolean found = false;
        JsonPath res = new JsonPath(Response.asString());
        res_values = res.getJsonObject("results");

        int n = res_values.size();
        String fieldname= "";
        String fieldval= "";
        String check_values= "";
        int i =0;
        while(i<n){
            values = res_values.get(i);
           // int n = res_values.size();
            String[] fields = lookup.split("&");
            for(String s :fields){
                fieldname = StringUtils.substringBefore(s,"=");
                if(fieldname.equals("id")) {
                    fieldname =StringUtils.replace(fieldname, "id", "artistId");
                }
                fieldval = StringUtils.substringAfter(s,"=");
              //  check_values = values.get(fieldname).toString();
                if(values.get(fieldname).toLowerCase().equals(fieldval.toLowerCase())){
                    found = true;
                }
                AssertJUnit.assertTrue(found);

            }
            i++;
        }
    }
}
