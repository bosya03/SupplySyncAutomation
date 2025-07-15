@regressionBranches
Feature: Create a new branch in the system

  Background:
    Given base URI is loaded from config "base_url_supplysync"
    And endpoint path is loaded from config "createBranch"
    And request content type is "application/json"
    And request contains a valid token from config key "bearer_token2"


  Scenario: Successfully CREATE a branch
    And request body contains the following fields
      | name        | Salesforce         |
      | email       | sales@gmail.com    |
      | address     | 832 Washington ave |
      | phoneNumber | 12345678910        |
      | regionId    | 2                  |
      | companyId   | 107                |
    When user sends a POST request
    Then status code should be 200
    And response should contain key "name" and value "Salesforce"
    And response should contain key "region.id" and value "2"


  Scenario: Successfully GET branch by providing its ID
    And endpoint path is loaded from config "getBranchByID"
    And request body contains the following fields
      | id          | 51                 |
      | name        | Salesforce         |
      | email       | sales@gmail.com    |
      | address     | 832 Washington ave |
      | phoneNumber | 12345678910        |
      | regionId    | 2                  |
      | companyId   | 107                |
    When user sends a GET request
    Then status code should be 200
    And response should contain key "id" and value "51"
    And response should contain key "company.blocked" and value "false"


  Scenario: Successfully UPDATE branch by providing its ID
    And endpoint path is loaded from config "updateBranchById"
    And request body contains the following fields
      | name        | Data Analyst       |
      | email       | data@gmail.com     |
      | address     | 800 Washington ave |
      | phoneNumber | 12345678910        |
      | regionId    | 38                 |
      | companyId   | 107                |
    When user sends a UPDATE request
    Then status code should be 200
    And response should contain key "name" and value "Data Analyst"
    And response should contain key "region.region" and value "Pennsylvania"



  Scenario: Successfully DELETE created branch by providing its ID
    And endpoint path is loaded from config "deleteBranchById"
    When user sends a DELETE request
    Then status code should be 200
    And verify branch should no longer exist