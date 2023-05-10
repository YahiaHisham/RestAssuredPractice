import files.Payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class test {
    public static void main(String[] args) {
//        given: all input details
//        when: submit the API
//        then: validate the response
        RestAssured.baseURI = "https://rahulshettyacademy.com";

//        add place > update place > get updated place
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
        given().queryParam("key", "qaclick123")
                .queryParam("place_id", placeId)
                .when()
                .get("/maps/api/place/get/json")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(200)
                .body("address", equalTo(newAddress));
    }
}
