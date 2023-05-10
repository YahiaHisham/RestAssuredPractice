package files;
import io.restassured.path.json.JsonPath;

public class PaseFunctions {
    public static JsonPath rawToJson(String response) {
        // this function is to convert the response body into JSON format so that I can extract any attribute from the response and get it's value

        JsonPath js = new JsonPath(response);
        return js;
    }
}
