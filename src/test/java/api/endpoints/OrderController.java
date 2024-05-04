package api.endpoints;

import api.Constants;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderController {
    public static Response addNewOrder() {
        Response response = given()
                .queryParam("api_token", Constants.AUTH_TOKEN)

                .when()
                .post(Routes.addNewOrder_url);

        return response;
    }
}
