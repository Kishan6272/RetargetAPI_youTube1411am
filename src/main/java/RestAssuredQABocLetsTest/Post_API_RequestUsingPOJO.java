package RestAssuredQABocLetsTest;

import POJOS.Booking;
import POJOS.BookingDates;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.BaseTest;

public class Post_API_RequestUsingPOJO extends BaseTest {


    @Test
    public void postRequest()
    {
        BookingDates bookingDates=new BookingDates("2024-03-25","2024-03-30");
        Booking booking=new Booking("apitesting11",
                "tutorials11",
                109,
                true,
                 bookingDates,
                 "breakfas11t");

        ObjectMapper objectMapper=new ObjectMapper();
        try {
            String requestbody=objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(booking);  //holding the json object
           // System.out.println(requestbody);

            // de Serialization the java Object
           Booking bookingDetails=objectMapper.readValue(requestbody,Booking.class);
            System.out.println(bookingDetails.getFirstname());
            System.out.println(bookingDetails.getTotalprice());
            System.out.println(bookingDetails.getBookingDates().getCheckin());
            System.out.println(bookingDetails.getBookingDates().getCheckout());

          Response response=
            RestAssured
                    .given()
                       .contentType(ContentType.JSON)
                       .body(requestbody)
                       .baseUri("https://restful-booker.herokuapp.com/booking")
                    .when()
                       .post()
                    .then()
                      .assertThat()
                      .statusCode(200)
                    .extract()
                    .response();

              //System.out.println(response.asString());

//              int bookingId= response.path("bookingid");
//
//              RestAssured
//                      .given().contentType(ContentType.JSON)
//                      .baseUri("https://restful-booker.herokuapp.com/booking/{{b_Id}}")
//                      .when().get("/{bookingId}",bookingId)
//                      .then()
//                      .statusCode(200);


        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
