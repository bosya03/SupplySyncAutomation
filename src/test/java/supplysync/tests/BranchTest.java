package supplysync.tests;

import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.junit.Assert;
import supplysync.utils.ConfigReader;

import java.util.Map;

public class BranchTest {

    RequestSpecification request;
    Response response;
    JSONObject requestBody = new JSONObject();
    String createdBranchID;


    @Given("base URI is loaded from config {string}")
    public void base_uri_is_loaded_from_config(String uri) {

        String url = ConfigReader.readProperty(uri);
        request = RestAssured.given().baseUri(url);

    }

    @Given("endpoint path is loaded from config {string}")
    public void endpoint_path_is_loaded_from_config(String endpoint) {

        String endPoint = ConfigReader.readProperty(endpoint);
        request = request.basePath(endPoint);

    }

    @Given("request content type is {string}")
    public void request_content_type_is(String contentType) {

        request = request.contentType(contentType);

    }

    @Given("request contains a valid token from config key {string}")
    public void request_contains_a_valid_token_from_config_key(String bearerToken) {

        String token = ConfigReader.readProperty(bearerToken);
        request = request.header("Authorization", "Bearer " + token);

    }

    @Given("request body contains the following fields")
    public void request_body_contains_the_following_fields(io.cucumber.datatable.DataTable dataTable) {

        Map<String, String> data = dataTable.asMap();
        for (String key : data.keySet()) {
            requestBody.put(key, data.get(key));
        }
        request = request.body(requestBody.toString());

    }

    @When("user sends a POST request")
    public void user_sends_a_post_request() {

        response = request.post();
        createdBranchID = response.jsonPath().getString("id"); // store the created ID

    }

    @When("user sends a GET request")
    public void user_sends_a_get_request() {

        response = request.get();
    }

    @When("user sends a UPDATE request")
    public void user_sends_a_update_request() {

        response = request.put();
    }

    @When("user sends a DELETE request")
    public void user_sends_a_delete_request() {

        response = request.delete();

    }

    @Then("status code should be {int}")
    public void status_code_should_be(Integer expectedStatusCode) {

        response.then().statusCode(expectedStatusCode);


    }

    @Then("response should contain key {string} and value {string}")
    public void response_should_contain_key_and_value(String key, String expectedValue) {

        Object actualValue = response.jsonPath().get(key);

        Assert.assertNotNull("Response should contain key: " + key, actualValue);
        Assert.assertEquals("Value for key " + key + " should match", expectedValue, actualValue.toString());


        System.out.println("=== RESPONSE START ===");
        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("Headers: " + response.getHeaders());
        System.out.println("Body:\n" + response.getBody().asPrettyString());
        System.out.println("=== RESPONSE END ===");

    }

    @Then("verify branch should no longer exist")
    public void verify_branch_should_no_longer_exist() {

        Response getResponse = RestAssured.given()
                .baseUri(ConfigReader.readProperty("base_url_supplysync"))
                .header("Authorization", "Bearer " + ConfigReader.readProperty("bearer_token2"))
                .contentType("application/json")
                .get("/api/v1/branches/" + createdBranchID);

        Assert.assertEquals("Branch not found with id -> " + createdBranchID, 200, getResponse.statusCode());

    }
}
