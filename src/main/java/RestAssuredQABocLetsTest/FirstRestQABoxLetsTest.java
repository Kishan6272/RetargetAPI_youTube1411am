package RestAssuredQABocLetsTest;


import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

public class FirstRestQABoxLetsTest {

    // it is capable validating http response from the server
    // the response is json or xml form which is easy to parse and  validate
    // it can be easily integrated with java libraries such as tesNG and Junit and Maven Build_Tool
    //  It has ggod support for various api integration
   // https://www.youtube.com/watch?v=o9KJhGHl49M&list=PLUeDIlio4THEVGJLuxKyKiQHGzEfq09nR&index=5    followed this


    @Test
    public void firstTest01()
    {
      //https://reqres.in/api/users/2
       Response res= RestAssured.get("https://reqres.in/api/users/2");
        System.out.println(res.asString());
        System.out.println( res.getStatusCode());

        //RequestSpecification requestSpecification
    }
    @Test
    public void firstTest02()
    {
        //https://reqres.in/api/users?page=2
        Response res= RestAssured.get("https://reqres.in/api/users?page=2");
        //System.out.println(res.asString());
       // System.out.println( res.getStatusCode());
        System.out.println(res.getBody().asString());
        System.out.println(res.getHeader("Content-Type"));

        //RequestSpecification requestSpecification
    }
}
