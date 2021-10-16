package com.API.sampleTests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import Files.Payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class IntegratingMultipleAPIsWithCommonJsonResponseValues {
	
	public static void main(String[] args) {

		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		System.err.println("Adding New Place");
		String response = given().queryParam("key", "qaclick123").header("Content-Type", "application/json").body(Payload.addPlace()).log().all()   		
		.when().post("maps/api/place/add/json") 																																		
		.then().extract().asString();   
		System.err.println(response);
		
		// parsing json response below
		
		JsonPath json= new JsonPath(response); 																// to convert the response string into json we use this
		
		
		String placeId = json.getString("place_id");
		System.err.println(placeId);
		
		// update Place API
		System.err.println("Updating Place with New Address");
		String newAddress="ABCD,1234,testAddress";
		given().queryParam("key", "qaclick123").header("Content-Type","application/json").body(Payload.updatePlace(placeId,newAddress)).log().all()
		.when().put("maps/api/place/update/json")
		.then().body("msg",equalTo("Address successfully updated")).log().all();
		
		
		// get newly Added Address
		
		System.err.println("Getting Place with Newly Added Address");
		String getAddressResponse = given().queryParam("key", "qaclick123")
		.queryParam("place_id", placeId).log().all()
		.when().get("maps/api/place/get/json")
		.then().log().all().assertThat().statusCode(200).extract().asString();
		System.err.println(getAddressResponse);
		JsonPath jsonresponse= new JsonPath(getAddressResponse);
		
		String actualAddressRetrieved = jsonresponse.getString("address");
		System.err.println(actualAddressRetrieved);
		Assert.assertEquals(actualAddressRetrieved, newAddress,"Validating Actual And Expected Address !");
	}

}


/* 
 * Add PlaceAPI----->Update Place API----->Get Place API
 * */
