package rest;

import commonFunctions.CommonFunctions;
import io.restassured.path.json.JsonPath;

public class JsonPathExample {

    public static void main(String[] args) {

        String inputJson = """
                {
                  "dashboard": {
                    "purchaseAmount": 910,
                    "website": "rahulshettyacademy.com"
                  },
                  "courses": [
                    {
                      "title": "Selenium Python",
                      "price": 50,
                      "copies": 6
                    },
                    {
                      "title": "Cypress",
                      "price": 40,
                      "copies": 4
                    },
                    {
                      "title": "RPA",
                      "price": 45,
                      "copies": 10
                    }
                  ]
                }""";

        //       1. Print No of courses returned by API - 3
        JsonPath js = CommonFunctions.stringToJson(inputJson);
        int coursesCount = js.getInt("courses.size()");
        System.out.println(coursesCount);


        //        2.Print Purchase Amount
        int amount = js.getInt("dashboard.purchaseAmount");

        String website = js.getString("dashboard.website");

        System.out.println(amount+" "+website);

        //        3. Print Title of the first course
        String course1 = js.get("courses[0].title");
        System.out.println(course1);


        //        4. Print All course titles and their respective Prices
        for(int i=0;i<js.getInt("courses.size()");i++)
        {
            String title = js.get("courses["+i+"].title");
            int price = js.getInt("courses["+i+"].price");
            System.out.println(title+"="+price);
        }

        //        5. Print no of copies sold by RPA Course
        for(int i=0;i<js.getInt("courses.size()");i++)
        {
            String title = js.get("courses["+i+"].title");
            if(title.equalsIgnoreCase("Cypress"))
            {
                int priceRpa = js.getInt("courses["+i+"].copies");
                System.out.println(priceRpa);
            }
        }

        //        6. Verify if Sum of all Course prices matches with Purchase Amount
        int sum=0;
        for(int i=0;i<js.getInt("courses.size()");i++)
        {
            int price = js.getInt("courses["+i+"].price");
            int copies = js.getInt("courses["+i+"].copies"); //price x copies
            sum = price * copies+sum;
        }
        System.out.println(sum);
        if(sum==amount)
        {
            System.out.println("correct");
        }
    }
}
