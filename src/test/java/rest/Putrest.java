package rest;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

public class Putrest {

	public static void main(String[] args) {

		RestAssured.baseURI = "https://rahulshettyacademy.com";

		String inputBody = """
								{
				"place_id":"d29d87108c405496a13921d5b8600e6d",
				"address":"70 Summer walk, USA",
				"key":"qaclick123"
				}
								""";

		given().log().all().queryParam("key", "qaclick123").queryParam("place_id", "10714c1c5e8981d7e4e9091d57f75e80")
				.header("Connection", "keep-alive").body(inputBody).when().put("/maps/api/place/get/json").then().log()
				.all().assertThat().statusCode(200);

	}

}
