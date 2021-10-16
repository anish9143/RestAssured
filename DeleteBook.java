package com.API.sampleTests;

import static io.restassured.RestAssured.given;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Files.Payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class DeleteBook {
	


	@Test(dataProvider = "BooksData") // this will go and scan in our files and look for dataprovider with name
										// "BooksData" and fetch the data
	// at each run only one array will be returned by dataprovider say array1 in
	// run1 which will give isbn and aisle data present in array 1

	public void deleteBook(String isbn, String aisle) {

		String id=isbn+aisle;
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().header("Content-Type", "application/json").body(Payload.deleteBook(id))
				.when().delete("Library/DeleteBook.php").then().extract().response().asString();

		JsonPath json = new JsonPath(response);
		String msg = json.getString("msg");
		System.err.println(msg);

	}

	@DataProvider(name = "BooksData") // we need to give name to our dataprovider to link it our test method
	public Object[][] getData() {
		// Multidimensional array --->{ {array1}, {array2},{array3}

		return new Object[][] { { "fyuugiu", "7998" }, { "ouojo", "97970" }, { "iyuhii", "6456" } };
	}



}
