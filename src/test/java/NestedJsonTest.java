import files.PaseFunctions;
import files.Payload;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

public class NestedJsonTest {


    public static void main(String[] args) {
//        given: all input details
//        when: submit the API
//        then: validate the response
        JsonPath js = PaseFunctions.rawToJson(Payload.coursesPricesPayload());

//1. Print No of courses returned by API
        int numberOfCourses = js.getInt("courses.size()");
        System.out.println("Number of courses is : " + numberOfCourses);
//2. Print Purchase Amount
        int purchaseAmount = js.getInt("dashboard.purchaseAmount");
        System.out.println("Purchase amount is : " + purchaseAmount);
//3. Print Title of the first course
        String firstCourseTitle = js.getString("courses[0].title");
        System.out.println("First Course Title is : " + firstCourseTitle);
//4. Print All course titles and their respective Prices
        for (int i = 0; i < numberOfCourses; i++) {
            String courseTitle = js.getString("courses[" + i + "].title");
            int coursePrice = js.getInt("courses[" + i + "].price");
            System.out.println("course Title is : " + courseTitle + " and it's price : " + coursePrice);
        }
//5. Print no of copies sold by RPA Course
        for (int i = 0; i < numberOfCourses; i++) {
            String courseTitle = js.getString("courses[" + i + "].title");
            if (courseTitle.equalsIgnoreCase("RPA")) {
                int numberOfRpaCopies = js.getInt("courses[" + i + "].copies");
                System.out.println("number of RPA copies is : " + numberOfRpaCopies);
            }
        }
//6. Verify if Sum of all Course prices matches with Purchase Amount
        int sum = 0;
        for (int i = 0; i < numberOfCourses; i++) {
            int coursePrice =  js.getInt("courses[" + i + "].price");
            int courseCopies = js.getInt("courses[" + i + "].copies");
            sum = (coursePrice*courseCopies) + sum;
        }
        System.out.println("courses total price is : " + sum);
        Assert.assertEquals(sum,purchaseAmount);
    }
}
