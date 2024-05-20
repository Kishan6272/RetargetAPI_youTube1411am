package RestAssuredQABocLetsTest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GetApiRequest {

    private static final Logger logger= LogManager.getLogger(GetApiRequest.class);

    @Test
    public void getAllBooking()
    {
        logger.info(" e2eApiRequest test execution started....");
        Response response=
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .baseUri("https://restful-booker.herokuapp.com/booking")
                .when().
                get().
                then().
                assertThat()
                .statusCode(210).header("Content-Type","application/json; charset=utf-8")
                .header("X-Powered-By","Express").extract().response();

       // Response response=RestAssured.get("https://restful-booker.herokuapp.com/booking");
        System.out.println(response.getBody().asString());
        Assert.assertTrue(response.getBody().asString().contains("bookingid"));

        logger.info(" e2eApiRequest test execution ended....");
    }
}
