package com.API.sampleTests;

import static io.restassured.RestAssured.given;

import io.restassured.RestAssured;
import static org.hamcrest.Matchers.*;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HTTPInbound {

	public static void main(String[] args) {

		SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyyHHmmss");
		Date date = new Date();
		System.out.println(formatter.format(date));

		String fileName = formatter.toString();

		RestAssured.baseURI = "http://LIT-VAINJW-Q003.gxsonline.net:9084/msgsrv/http";
		given().header("Content-Type", "text/plain")
				.header("Content-Disposition", "form-data;filename=" + fileName + ".txt")
				.queryParam("from", "AS4Sender").queryParam("to", "AS4Receiver").log().all().when().post().then().log()
				.all();

	}

}
