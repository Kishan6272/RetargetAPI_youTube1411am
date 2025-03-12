package RestAssuredQABocLetsTest;

import POJOS.Booking;
import POJOS.BookingDates;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import listner.RestAssuredListener;
import net.minidev.json.JSONArray;
import org.apache.commons.io.FileUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.FileNameConstants;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;

public class DataDrivenTestingUsingJsonFile {


    @Test(dataProvider = "getTestData")
    public void dataDrivenTestingUsingJson(LinkedHashMap<String,String> testData) throws JsonProcessingException {
        ObjectMapper objectMapper=new ObjectMapper();

        BookingDates bookingdates=new BookingDates("2024-03-25","2024-03-30");
        Booking booking=new Booking(testData.get("firstname"), testData.get("lastname"), "breakfas11t",109, true, bookingdates);

        String requestbody=objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(booking);  //holding the json object
        // System.out.println(requestbody);


        // System.out.println();
        Response response=
                RestAssured
                        .given().filter(new RestAssuredListener())
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


    }

    @DataProvider(name="getTestData")
    public Object[] getTestDataUsingJson() throws IOException {
        Object [] obj=null;


        try {
            String jsonTestData=FileUtils.readFileToString(new File(FileNameConstants.JSON_TEST_DATA),"UTF-8");

          JSONArray jsonArray= JsonPath.read(jsonTestData,"$");


          obj=new Object[jsonArray.size()];


          for (int i=0;i<jsonArray.size();i++)
          {
            obj[i]=  jsonArray.get(i);
          }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return obj;
    }
}
