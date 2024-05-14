package RestAssuredQABocLetsTest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GetApiRequest {


    @Test
    public void getAllBooking()
    {

        Response response=
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .baseUri("https://restful-booker.herokuapp.com/booking")
                .when().
                get().
                then().
                assertThat()
                .statusCode(200).header("Content-Type","application/json; charset=utf-8")
                .header("X-Powered-By","Express").extract().response();

       // Response response=RestAssured.get("https://restful-booker.herokuapp.com/booking");
        System.out.println(response.getBody().asString());
        Assert.assertTrue(response.getBody().asString().contains("bookingid"));
    }
}
