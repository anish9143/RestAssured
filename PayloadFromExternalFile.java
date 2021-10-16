package com.API.sampleTests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PayloadFromExternalFile {
	
	
	public static void main(String[] args) throws IOException {
		
		RestAssured.baseURI="https://rahulshettyacademy.com";
		String response = given().header("Content-Type","application/json").queryParam("key", "qaclick123").body(new String(Files.readAllBytes(Paths.get("TestData/addPlace.json"))))
		.log().all().when().post("maps/api/place/add/json").then().log().all().extract().response().asString();
		
		JsonPath json = new JsonPath(response);
		String placeID = json.getString("place_id");
		System.err.println(placeID);
		
	}

}
