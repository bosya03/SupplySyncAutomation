@regressionCompanies
Feature: Create a new company in the system


  Background:
    Given the base URI is loaded from config "base_url_supplysync"
    And the endpoint path is loaded from config "create_company_endpoint"
    And the request content type is "application/json"
    And the request contains a valid token from config key "bearer_token2"


  Scenario: Successfully CREATE a company with valid data
    And the request body contains the following fields
      | logo        | Bosya Logo     |
      | name        | BOFAA          |
      | email       | bank@gmail.com |
      | address     | 200 N Clark St |
      | phoneNumber | 4125199409     |
    When the user sends a POST request
    Then the status code should be 200
    And the response should contain key "name" and value "BOFAA"
    And the response should contain key "email" and value "bank@gmail.com"


  Scenario: Successfully GET company by its ID
    And the endpoint path is loaded from config "getCompanyByID"
    And the request body contains the following fields

      | id          | 115            |
      | blocked     | false          |
      | logo        | Bosya Logo     |
      | name        | BOFAA          |
      | email       | bank@gmail.com |
      | address     | 200 N Clark St |
      | phoneNumber | 4125199409     |
      | status      | 0              |
    When the user sends a GET request
    Then the status code should be 200
    And the response should contain key "id" and value "115"
    And the response should contain key "name" and value "BOFAA"
        #    should I remove end point from config properties ? ask Bena



  Scenario: Successfully UPDATE company by providing its ID
    And the endpoint path is loaded from config "updateCompanyByID"
    And the request body contains the following fields
      | logo        | Insurance              |
      | name        | BOFAAAAA               |
      | email       | carinsurance@gmail.com |
      | address     | 125 SunnySide St       |
      | phoneNumber | 09090909090            |
    When the user sends a PUT request
    Then the status code should be 200
    And the response should contain key "logo" and value "Insurance"
    And the response should contain key "name" and value "BOFAAAAA"


  Scenario: Successfully DELETE created company by providing its ID
    And the endpoint path is loaded from config "deleteCompanyByID"
    When the user sends a DELETE request
    Then the status code should be 200
    And the company should no longer exist















