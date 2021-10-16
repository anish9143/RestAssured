package com.API.sampleTests;

import org.testng.Assert;

import Files.Payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		JsonPath json = new JsonPath(Payload.SampleJson());

		// 1. Print No of courses returned by API

		System.out.println("***Scenario1****");
		int coursesCount = json.getInt("courses.size()"); // size() method as courses are in an array format in
															// json(0,1,2)
		System.err.println(coursesCount);

		// 2.Print Purchase Amount
		System.out.println("***Scenario2****");
		int purchaseAmount = json.getInt("dashboard.purchaseAmount");

		System.err.println(purchaseAmount);

		// 3. Print Title of the first course

		System.out.println("***Scenario3****");
		String firstTitle = json.get("courses[0].title");
		System.err.println(firstTitle);

		// 4. Print All course titles and their respective Prices
		System.out.println("***Print All course titles and their respective Prices****");

		for (int i = 0; i < coursesCount; i++) {
			String title = json.get("courses[" + i + "].title");
			System.err.println(title);
			int price = json.getInt("courses[" + i + "].price");
			System.err.println(price);
		}

		//5. Print no of copies sold by RPA Course
		System.out.println("***Print no of copies sold by RPA Course****");

		for (int i = 0; i < coursesCount; i++) {
			String title = json.get("courses[" + i + "].title");
			if (title.equalsIgnoreCase("RPA")) {
				System.err.println(json.get("courses[" + i + "].copies").toString());
				break; // break it once found no need to loop further for loop
			}

		}

		// 5. Verify if Sum of all Course prices matches with Purchase Amount
		System.out.println("*** Verify if Sum of all Course prices matches with Purchase Amount****");

		int sum = 0;
		for (int i = 0; i < coursesCount; i++) {

			int price = json.getInt("courses[" + i + "].price");
			int copies = json.getInt("courses[" + i + "].copies");
			int amount = price * copies;

			sum = sum + amount;

		}
		System.err.println(sum);
		Assert.assertEquals(sum, purchaseAmount);

	}

}
