package commonFunctions;

import io.restassured.path.json.JsonPath;

public class CommonFunctions {

    public static JsonPath stringToJson(String response)
    {
        return new  JsonPath(response);
    }
}
