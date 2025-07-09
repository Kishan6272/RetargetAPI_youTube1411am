package RestAssuredQABocLetsTest;

import POJOS.Booking;
import POJOS.BookingDates;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import listner.RestAssuredListener;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.FileNameConstants;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class DataDrivenTestingUsingCsvFile {

    @Test(dataProvider = "CSV_testData")
    public void dataDrivwnTestingUSingCsv(Map<String,String>testData) throws JsonProcessingException {
        System.out.println(testData.get("firstname"));
        System.out.println(testData.get("lastname"));




//        ObjectMapper objectMapper=new ObjectMapper();
//        int totalprice=Integer.parseInt(testData.get("totalprice"));
//
//        BookingDates bookingdates=new BookingDates("2024-03-25","2024-03-30");
//        Booking booking=new Booking(testData.get("firstname"), testData.get("lastname"), "breakfas11t",totalprice, true, bookingdates);
//
//        String requestbody=objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(booking);  //holding the json object
//        // System.out.println(requestbody);
//
//
//        // System.out.println();
//        Response response=
//                RestAssured
//                        .given().filter(new RestAssuredListener())
//                        .contentType(ContentType.JSON)
//                        .body(requestbody)
//                        .baseUri("https://restful-booker.herokuapp.com/booking")
//                        .when()
//                        .post()
//                        .then()
//                        .assertThat().log().all()
//                        .statusCode(200)
//                        .extract()
//                        .response();

    }



    @DataProvider(name="CSV_testData")
    public Object[][] getTestData() throws IOException, CsvValidationException {
        Object [][] objArray= null;

        Map<String ,String> map=null;

        List<Map<String,String >> testDataList=null;

        CSVReader csvReader=new CSVReader(new FileReader(FileNameConstants.TEST_DATA_CSV));

        testDataList=new ArrayList<Map<String,String >>();

       String[] line=null;

       int count=0;

       while ((line=csvReader.readNext())!=null){

           if(count==0)
           {
               count++;
               continue;
           }


          map=new TreeMap<String,String>(String.CASE_INSENSITIVE_ORDER);

          map.put("firstname",line[0]);
          map.put("lastname",line[1]);
          map.put("totalprice",line[2]);

           testDataList.add(map);
       }

       objArray=new Object[testDataList.size()][1];

       for(int i=0;i<testDataList.size();i++)
       {
          objArray[i][0]= testDataList.get(i);
       }

        return  objArray;
    }

}
