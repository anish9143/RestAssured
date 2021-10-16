package com.API.sampleTests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import Files.Payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class ExtractJsonResponseFromBodyAsString {

	public static void main(String[] args) {
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().queryParam("key", "qaclick123").header("Content-Type", "application/json").body(Payload.addPlace()).log().all()   		
		.when().post("maps/api/place/add/json") 																																		
		.then().extract().response().asString();
		
		System.err.println("******Below is extracted Json Response from Body in String format******");
		System.err.println(response);
		
		// parsing json response below
		
		JsonPath json= new JsonPath(response); 																// to convert the response string into json we use this
		
		
		String placeId = json.getString("place_id");
		System.err.println(placeId);
	}


	}









/*

extract() method is used to retrieve json response // this will return only hashcode
asString() method is just to retrieve hashcode into string
extract().asString()  returns json response in string format --->here response variable

To Parse the json, we use JsonPath Class available in restassured library---> it accepts string as input and convert it to json format
*/
