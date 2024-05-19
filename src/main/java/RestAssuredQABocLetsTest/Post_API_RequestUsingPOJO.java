package RestAssuredQABocLetsTest;

import POJOS.Booking;
import POJOS.BookingDates;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.apache.commons.io.FileUtils;
import org.testng.annotations.Test;
import utils.BaseTest;
import utils.FileNameConstants;

import java.io.File;
import java.io.IOException;

public class Post_API_RequestUsingPOJO extends BaseTest {


    @Test
    public void postRequest()
    {

//        BookingDates bookingdates=new BookingDates("2024-03-25","2024-03-30");
//        Booking booking=new Booking("apitesting11", "tutorials11", true, bookingdates, "breakfas11t", 109);

        ObjectMapper objectMapper=new ObjectMapper();
        try {
            String jsonSchema= FileUtils.readFileToString(new File(FileNameConstants.JSON_SCHEMA),"UTF-8");
            BookingDates bookingdates=new BookingDates("2024-03-25","2024-03-30");
            Booking booking=new Booking("apitesting11", "tutorials11", 109, true, bookingdates, "breakfas11t");

            String requestbody=objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(booking);  //holding the json object
           // System.out.println(requestbody);

            // de Serialization the java Object
           Booking bookingDetails=objectMapper.readValue(requestbody,Booking.class);
            System.out.println(bookingDetails.getFirstname());
            System.out.println(bookingDetails.getTotalprice());
            System.out.println(bookingDetails.getBookingdates().getCheckin());
            System.out.println(bookingDetails.getBookingdates().getCheckout());

              // System.out.println();
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

            System.out.println(response.asString());

              int bookingId= response.path("bookingid");
            System.out.println(jsonSchema);

              RestAssured
                      .given()
                            .contentType(ContentType.JSON)
                           .baseUri("https://restful-booker.herokuapp.com/booking")
                      .when()
                         .get("/{bookingId}",bookingId)
                      .then()
                          .statusCode(200)
                      .body(JsonSchemaValidator.matchesJsonSchema(jsonSchema));   // validating json schema


        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
