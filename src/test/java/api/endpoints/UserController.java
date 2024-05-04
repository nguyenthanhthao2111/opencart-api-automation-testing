package api.endpoints;

import static io.restassured.RestAssured.given;

import api.Constants;
import io.restassured.response.Response;

public class UserController {
    private static String api_token;
    public static Response loginUser(String username, String apiKey) {
        Response response = given()
                .contentType("multipart/form-data")
                .multiPart("username", username)
                .multiPart("key", apiKey)

                .when()
                .post(Routes.login_url);

        api_token = response.jsonPath().getString("api_token");
        return response;
    }

    public static String apiToken() {
        return api_token;
    }

    public static Response addCustomerData(String firstname, String lastname, String email, String telephone) {
        Response response = given()
                .queryParam("api_token", Constants.AUTH_TOKEN)
                .contentType("multipart/form-data")
                .multiPart("firstname", firstname)
                .multiPart("lastname", lastname)
                .multiPart("email", email)
                .multiPart("telephone", telephone)

                .when()
                .post(Routes.addCustomerData_url);

        return response;
    }
}
