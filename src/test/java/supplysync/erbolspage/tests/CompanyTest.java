package supplysync.erbolspage.tests;

import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.junit.Assert;
import supplysync.erbolspage.utils.ConfigReader;

import java.util.Map;

public class CompanyTest {

    RequestSpecification request;
    Response response;
    JSONObject requestBody = new JSONObject();
    String createdCompanyId;


    @Given("the base URI is loaded from config {string}")
    public void the_base_uri_is_loaded_from_config(String uri) {

        String url = ConfigReader.readProperty(uri);
        request = RestAssured.given().baseUri(url);

    }

    @Given("the endpoint path is loaded from config {string}")
    public void the_endpoint_path_is_loaded_from_config(String endPoint) {

        String endpoint = ConfigReader.readProperty(endPoint);
        request = request.basePath(endpoint);

    }

    @Given("the request content type is {string}")
    public void the_request_content_type_is(String contentType) {

        request = request.contentType(contentType);

    }

    @Given("the request contains a valid token from config key {string}")
    public void the_request_contains_a_valid_token_from_config_key(String bearerToken) {

        String token = ConfigReader.readProperty(bearerToken);
        request = request.header("Authorization", "Bearer " + token);

    }

    @Given("the request body contains the following fields")
    public void the_request_body_contains_the_following_fields(io.cucumber.datatable.DataTable dataTable) {

        Map<String, String> data = dataTable.asMap();
        for (String key : data.keySet()) {
            requestBody.put(key, data.get(key));
        }
        request = request.body(requestBody.toString());

    }

    @When("the user sends a POST request")
    public void the_user_sends_a_post_request() {

        response = request.post();
        createdCompanyId = response.jsonPath().getString("id"); // store the created ID


    }


    @When("the user sends a GET request")
    public void the_user_sends_a_get_request() {

        response = request.get();
    }

    @When("the user sends a PUT request")
    public void the_user_sends_a_put_request() {

        response = request.put();

    }

    @When("the user sends a DELETE request")
    public void the_user_sends_a_delete_request() {

        response = request.delete();

    }

    @Then("the status code should be {int}")
    public void the_status_code_should_be(Integer expectedStatusCode) {

        response.then().statusCode(expectedStatusCode);
    }

    @Then("the response should contain key {string} and value {string}")
    public void the_response_should_contain_key_and_value(String key, String value) {

        String strResponse = response.asPrettyString();
        Assert.assertTrue("Response should contain key: " + key, strResponse.contains(key));
        Assert.assertTrue("Response should contain value: " + value, strResponse.contains(value));

        System.out.println("=== RESPONSE START ===");
        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("Headers: " + response.getHeaders());
        System.out.println("Body:\n" + response.getBody().asPrettyString());
        System.out.println("=== RESPONSE END ===");
    }

    @Given("the company should no longer exist")
    public void the_company_should_no_longer_exist() {

        Response getResponse = RestAssured.given()
                .baseUri(ConfigReader.readProperty("base_url_supplysync"))
                .header("Authorization", "Bearer " + ConfigReader.readProperty("bearer_token2"))
                .contentType("application/json")
                .get("/api/v1/company/" + createdCompanyId);

        Assert.assertEquals("Company with id -> " + createdCompanyId + " was not found", 200, getResponse.statusCode());

    }
}


