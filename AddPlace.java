package com.API.sampleTests;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

import Files.Payload;

public class AddPlace {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		RestAssured.baseURI = "https://rahulshettyacademy.com";
		given().header("Content-Type", "application/json").queryParam("key", "qaclick123")
				.body(Payload.addPlace()).log().all()
				.when().post("maps/api/place/add/json")
				.then().assertThat().statusCode(200).log().all();

	}

}

/*
 * given : To pass all input details when: To Submit the API request (like
 * resources and HTTP method details) then: Validations log().all() is used to
 * see the details of request constructed and response received in console
 * 
 * given/when/then keywords will come only after adding import static
 * io.restassured.RestAssured.*; manually
 * 
 */