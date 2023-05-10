import files.PaseFunctions;
import files.Payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class test {
    public static void main(String[] args) {
//        given: all input details
//        when: submit the API
//        then: validate the response
        RestAssured.baseURI = "https://rahulshettyacademy.com";

//        add place > update place > get updated place
//        Add Place
        String response = given().queryParam("key", "qaclick123")
                .header("Content-Type", "application/json")
                .body(Payload.addPlacePayload())
                .when()
                .post("/maps/api/place/add/json")
                .then()
                .assertThat()
                .statusCode(200)
                .body("scope", equalTo("APP"))
                .header("Server", "Apache/2.4.41 (Ubuntu)")
                .extract()
                .response()
                .asString();
        System.out.println(response);
        JsonPath js = new JsonPath(response);
        String placeId = js.getString("place_id");
        System.out.println("place id is : " + placeId);

//        Update Place
        String newAddress = "Nasr City, Cairo, Egypt";
        given().queryParam("key", "qaclick123")
                .header("Content-Type", "application/json")
                .body(Payload.updatePlacePayload(placeId, newAddress))
                .when()
                .put("/maps/api/place/update/json")
                .then()
                .assertThat()
                .statusCode(200)
                .body("msg", equalTo("Address successfully updated"));

//        Get Place
        String getPlaceResponse = given().queryParam("key", "qaclick123")
                .queryParam("place_id", placeId)
                .when()
                .get("/maps/api/place/get/json")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(200)
                .body("address", equalTo(newAddress))
                .extract()
                .response()
                .asString();

        JsonPath js2 = PaseFunctions.rawToJson(getPlaceResponse);
        String actualAddress = js2.getString("address");
        Assert.assertEquals(actualAddress, newAddress);
        System.out.println("get place only " + getPlaceResponse);
    }
}
