package RestAssuredQABocLetsTest;

//import com.google.gson.JsonObject;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import net.minidev.json.JSONObject;
import utils.BaseTest;

public class PostAPI_Request extends BaseTest {

    @Test
    public void createBooking()
    {
      //  RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

       // prepeare the request body using JSON object
        JSONObject booking =new JSONObject();
        JSONObject bookingDates =new JSONObject();

        booking.put("firstname","apitesting");
        booking.put("lastname","tutorial");
        booking.put("totalprice","1000");
        booking.put("depositpaid",true);
        booking.put("additionalneeds","Breakfast");
        booking.put("bookingdates",bookingDates);
       // booking.put("","apitesting");
        bookingDates.put("checkin","2018-01-01");
        bookingDates.put("checkout","2019-01-01");



        Response response=
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(booking.toString())
               // .body(bookingDates.toString())
                .baseUri("https://restful-booker.herokuapp.com/booking")
                //.log().all()
               // .log().headers()
                .when()
                .post().then()
//                .log().headers()
//                .log().body()

                .assertThat()
                //.log().ifValidationFails()

                .statusCode(200)
                .body("booking.firstname", Matchers.equalTo("apitesting"))
                .body("booking.totalprice", Matchers.equalTo(1000))
                .body("booking.bookingdates.checkin", Matchers.equalTo("2018-01-01"))
                .extract().response();//'  for  nested json objects


         int bookingId=  response.path("bookingid");
        System.out.println(bookingId);

         RestAssured.given()
                 .contentType(ContentType.JSON)
                 .pathParam("bookingId",bookingId)
                 .baseUri("https://restful-booker.herokuapp.com/booking/")
                 //.basePath()

                 .when()
                     .get("{bookingId}")
                 .then()
                     .log().all()
                      .assertThat().statusCode(200)
                 .body("firstname",Matchers.equalTo("apitesting"));






    }



}
