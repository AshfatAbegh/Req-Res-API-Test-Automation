package endpoints;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import payload.User;

import static io.restassured.RestAssured.given;

public class UserEndpoints {

    public static Response createUser(User payload) {

        Response response = given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post(Routes.post_url);

        return response;
    }

    public static Response listOfUsers(int pageNumber) {

        Response response = given()
                .queryParam("page", pageNumber)
                .when()
                .get(Routes.get_url);

        return response;
    }
}
