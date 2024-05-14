package RestAssuredQABocLetsTest;

import com.jayway.jsonpath.JsonPath;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.minidev.json.JSONArray;
import org.apache.commons.io.FileUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.BaseTest;
import utils.FileNameConstants;

import java.io.File;
import java.io.IOException;

public class PostApiRequestUsingFile extends BaseTest {


    @Test
    public void postApiRequest()
        {
            try {
                String postApiRequestBody=FileUtils.readFileToString(new File(FileNameConstants.post_API_Request_Body),"UTF-8");
                System.out.println(postApiRequestBody);

              Response response=
                RestAssured
                        .given()
                          .contentType(ContentType.JSON)
                           .body(postApiRequestBody)
                           .baseUri("https://restful-booker.herokuapp.com/booking")
                        .when()
                           .post()
                        .then()
                           .assertThat()
                           .statusCode(200)
                        .extract()
                        .response();

             JSONArray jsonArray= JsonPath.read( response.body().asString(), "$.booking..firstname");
             String firstName= (String) jsonArray.get(0);

                Assert.assertEquals(firstName,"apitesting");


                JSONArray jsonArrayLastName= JsonPath.read( response.body().asString(), "$.booking..lastname");
                String lastName= (String) jsonArrayLastName.get(0);

                Assert.assertEquals(lastName,"tutorials");


                JSONArray jsonArrayCheckIn= JsonPath.read( response.body().asString(), "$.booking.bookingdates..checkin");
                String checkIn= (String) jsonArrayCheckIn.get(0);

                Assert.assertEquals(checkIn,"2018-01-01");

                int bookingId= JsonPath.read( response.body().asString(), "$.bookingid");

                RestAssured
                        .given()
                          .contentType(ContentType.JSON)
                           .baseUri("https://restful-booker.herokuapp.com/booking")
                        .when()
                            .get("/{bookingId}",bookingId)
                        .then()
                          .statusCode(200);


            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

}
