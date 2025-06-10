package rest;

import commonFunctions.CommonFunctions;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class Restclass {

    public static void main(String[] args) {

        RestAssured.baseURI = "https://rahulshettyacademy.com";
        //rest assured works on given, when and then

        //Add Place API
        String inputBody = """
                				{
                  "location": {
                    "lat": -38.383494,
                    "lng": 33.427362
                  },
                  "accuracy": 50,
                  "name": "Frontline house",
                  "phone_number": "(+91) 983 893 3937",
                  "address": "29, side layout, cohen 09",
                  "types": [
                    "shoe park",
                    "shop"
                  ],
                  "website": "http://google.com",
                  "language": "French-IN"
                }
                """;

        String response = given()
                .log().all().queryParam("key", "qaclick123")
                .header("Content-Type", "application/json")
                .header("Cache-Control", "no-cache").
                body(inputBody)
                .when().post("maps/api/place/add/json")
                .then().log().all().assertThat().statusCode(200)
                .body("status", equalTo("OK")).body("scope", equalTo("APP"))
                .header("Server", equalTo("Apache/2.4.52 (Ubuntu)"))
                .extract().asString();

        //Extracting the place_id value
        JsonPath js =CommonFunctions.stringToJson(response);
        String placeId = js.get("place_id");

        //Updating the address using put()
        String inputBody1 = String.format("""
                {
                           "place_id": "%s",
                           "address":"Nallurhalli, Whitefield",
                           "key":"qaclick123"
                         }""", placeId);

        given().log().all().queryParam("key", "qaclick123")
                .queryParam("place_id", placeId).
                header("Connection", "keep-alive").
                body(inputBody1).
                when().put("/maps/api/place/update/json").
                then().log().all().assertThat().statusCode(200);


        //Getting the update value
        String responseGet = given().queryParam("place_id", placeId)
                .queryParam("key", "qaclick123").
                header("Connection", "keep-alive").
                when().get("/maps/api/place/get/json").
                then().log().all().assertThat()
                .statusCode(200).extract().asString();

		JsonPath js1 = CommonFunctions.stringToJson(responseGet);
		String s = js1.getString("address");
		Assert.assertEquals(s,"Nallurhalli, Whitefield");
    }
};

//{
//		"status": "OK",
//		"place_id": "1119116e5443e2d93d158ee97dfd69b3",
//		"scope": "APP",
//		"reference": "90f1bbaffe0ff03e9cbf8e02d5602af890f1bbaffe0ff03e9cbf8e02d5602af8",
//		"id": "90f1bbaffe0ff03e9cbf8e02d5602af8"
//		}