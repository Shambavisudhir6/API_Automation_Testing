package rest;

import commonFunctions.CommonFunctions;
import io.restassured.RestAssured;
import io.restassured.RestAssured.*;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class BooksApi {
    public static void main(String[] args) {

        RestAssured.baseURI = "http://216.10.245.166";
        String inputJson = """
                {
                "name":"kerala stories",
                "isbn":"bcd90",
                "aisle":"2279",
                "author":"shambavi"
                }
                """;

        String response = given().header("Connection","keep-alive").body(inputJson).
                when().post("/Library/Addbook.php").
                then().log().all().assertThat().statusCode(200)
                .header("Transfer-Encoding",equalTo("chunked")).extract().asString();

        JsonPath js = CommonFunctions.stringToJson(response);
        String name = js.get("Msg");
        System.out.println(name);

        String responseGet = given().header("Connection","keep-alive").
                when().get("/Library/GetBook.php?AuthorName=shambavi").
                then().log().all().assertThat().statusCode(200)
                .extract().asString();

        System.out.println(name);

    }
}
