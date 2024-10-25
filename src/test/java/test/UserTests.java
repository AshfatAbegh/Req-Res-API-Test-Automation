package test;

import endpoints.UserEndpoints;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import payload.User;

public class UserTests {

    User userPayload;

    @BeforeClass
    public void setupData() {
        userPayload = new User();
        userPayload.setName("Ashfat");
        userPayload.setJob("SQA Engineer");
    }

    @Test(priority = 1)
    public void createUser() {
        Response response = UserEndpoints.createUser(userPayload);
        String responseBody = response.asString();
        System.out.println(responseBody);

        Assert.assertEquals(response.statusCode(), 201);
    }

    @Test(priority = 2)
    public void listOfUsers() {
        Response response = UserEndpoints.listOfUsers(2);

        String responseBody = response.asPrettyString();
        System.out.println(responseBody);

        Assert.assertEquals(response.getStatusCode(), 200);

        // Validating total JSON response against schema
        response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("UserListJSONSchema.json"));

        JsonPath jsonResponse = response.jsonPath();

        // Extracting the user data for id = 7
        int userId = jsonResponse.getInt("data[0].id");
        String email = jsonResponse.getString("data[0].email");
        String firstName = jsonResponse.getString("data[0].first_name");
        String lastName = jsonResponse.getString("data[0].last_name");
        String avatar = jsonResponse.getString("data[0].avatar");

        // Validating the data for id = 7
        Assert.assertEquals(userId, 7, "User ID matches successfully!");
        Assert.assertEquals(email, "michael.lawson@reqres.in", "Email matches successfully!");
        Assert.assertEquals(firstName, "Michael", "First name matches successfully!");
        Assert.assertEquals(lastName, "Lawson", "Last name matches successfully!");
        Assert.assertEquals(avatar, "https://reqres.in/img/faces/7-image.jpg", "Avatar URL matches successfully!");
    }
}
