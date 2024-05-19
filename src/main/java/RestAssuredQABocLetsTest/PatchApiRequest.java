package RestAssuredQABocLetsTest;

import com.jayway.jsonpath.JsonPath;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.minidev.json.JSONArray;
import org.apache.commons.io.FileUtils;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.FileNameConstants;

import java.io.File;
import java.io.IOException;

public class PatchApiRequest {
    @Test
    public void patchApiRequest() {
        try {
            String postApiRequestBody = FileUtils.readFileToString(new File(FileNameConstants.post_API_Request_Body), "UTF-8");
            String tokenApiRequestBody = FileUtils.readFileToString(new File(FileNameConstants.Token_API_Request_Body), "UTF-8");
            String putApiRequestBody = FileUtils.readFileToString(new File(FileNameConstants.PUT_API_Request_Body), "UTF-8");
            String patchApiRequestBody = FileUtils.readFileToString(new File(FileNameConstants.PATCH_API_Request_Body), "UTF-8");
            // post api call
            Response response =
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

            JSONArray jsonArray = JsonPath.read(response.body().asString(), "$.booking..firstname");
            String firstName = (String) jsonArray.get(0);

            Assert.assertEquals(firstName, "apitesting");


            JSONArray jsonArrayLastName = JsonPath.read(response.body().asString(), "$.booking..lastname");
            String lastName = (String) jsonArrayLastName.get(0);

            Assert.assertEquals(lastName, "tutorials");


            JSONArray jsonArrayCheckIn = JsonPath.read(response.body().asString(), "$.booking.bookingdates..checkin");
            String checkIn = (String) jsonArrayCheckIn.get(0);

            Assert.assertEquals(checkIn, "2018-01-01");

            int bookingId = JsonPath.read(response.body().asString(), "$.bookingid");


            // get api call
            RestAssured
                    .given()
                    .contentType(ContentType.JSON)
                    .baseUri("https://restful-booker.herokuapp.com/booking")
                    .when()
                    .get("/{bookingId}", bookingId)
                    .then()
                    .statusCode(200);


            // getting the token'
            Response tokenApiResponse =
                    RestAssured
                            .given()
                            .contentType(ContentType.JSON).body(tokenApiRequestBody)
                            .baseUri("https://restful-booker.herokuapp.com/auth")
                            .when()
                            .post()
                            .then()
                            .assertThat().statusCode(200).extract().response();

            String tokenId = JsonPath.read(tokenApiResponse.body().asString(), "$.token");


            RestAssured
                    .given()
                    .contentType(ContentType.JSON)
                    .body(putApiRequestBody)
                    .header("Cookie", "token =" + tokenId)
                    .baseUri("https://restful-booker.herokuapp.com/booking")
                    .when()
                    .put("/{bookingId}", bookingId)
                    .then()
                    .assertThat()
                    .statusCode(200).body("firstname", Matchers.equalTo("APItesting"));



            // patch api call

            RestAssured
                    .given()
                       .contentType(ContentType.JSON).body(patchApiRequestBody)
                    .header("Cookie", "token =" + tokenId)
                    .baseUri("https://restful-booker.herokuapp.com/booking")
                    .when()
                    .patch("/{bookingId}", bookingId)
                    .then()
                    .assertThat()
                    .statusCode(200)
                    .body("firstname",Matchers.equalTo("kishan"));



        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
