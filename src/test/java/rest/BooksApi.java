package rest;

import commonFunctions.CommonFunctions;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;
import payLoads.LibraryPayloads;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class BooksApi {

    String id = "";

    @Test
    public void addBook() {
        RestAssured.baseURI = "http://216.10.245.166";

        String response = given().header("Connection", "keep-alive").body(LibraryPayloads.addBook("amma stories", "123477", "997890988", "Shambavi")).
                when().post("/Library/Addbook.php").
                then().log().all().assertThat().statusCode(200)
                .header("Transfer-Encoding", equalTo("chunked")).extract().asString();

        JsonPath js = CommonFunctions.stringToJson(response);
        id = js.get("ID");
        String name = js.getString("Msg");
        Assert.assertEquals(name, "successfully added");
    }

    @Test
    public void getBook() {
        RestAssured.baseURI = "http://216.10.245.166";
        String responseGet = given().header("Connection", "keep-alive").
                when().get("/Library/GetBook.php?AuthorName=shambavi").
                then().log().all().assertThat().statusCode(200)
                .extract().asString();
    }

    @Test
    public void deleteBook() {
        RestAssured.baseURI = "http://216.10.245.166";

        String responsePost = given().header("Connection", "keep-alive").
                body(LibraryPayloads.deleteBook(id)).when().post(":/Library/DeleteBook.php").
                then().extract().asString();

        System.out.println(responsePost);
    }
}
