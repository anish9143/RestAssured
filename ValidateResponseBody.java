package com.API.sampleTests;

import static io.restassured.RestAssured.given;

import io.restassured.RestAssured;
import static org.hamcrest.Matchers.*;

import Files.Payload;

public class ValidateResponseBody {

	public static void main(String[] args) {

		RestAssured.baseURI = "https://rahulshettyacademy.com";
		given().queryParam("key", "qaclick123").header("Content-Type", "application/json").body(Payload.addPlace()).log().all()   		// given Input details
		.when().post("maps/api/place/add/json") 																																		// when HTTP Method and resources are passed
		.then().assertThat().statusCode(200).body("scope", equalTo("APP")).header("Server", "Apache/2.4.1 (Ubuntu)").log().all();   // then validate
	}

}

/*

equalTo() method will come from import static org.hamcrest.Matchers.*;---> go and add this package manually only then this method will work

*/