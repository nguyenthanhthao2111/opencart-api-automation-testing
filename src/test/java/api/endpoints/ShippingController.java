package api.endpoints;

import api.Constants;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ShippingController {

    public static Response addShippingAddress(String firstname, String lastname, String address, String city, String countryId, String zoneId) {
        Response response = given()
                .queryParam("api_token", Constants.AUTH_TOKEN)
                .multiPart("firstname", firstname)
                .multiPart("lastname", lastname)
                .multiPart("address_1", address)
                .multiPart("city", city)
                .multiPart("country_id", countryId)
                .multiPart("zone_id", zoneId)

                .when()
                .post(Routes.addShippingAddress_url);

        return response;
    }

    public static Response getShippingMethod() {
        Response response = given()
                .queryParam("api_token", Constants.AUTH_TOKEN)
                .when()
                .post(Routes.getShippingMethod_url);

        return response;
    }

    public static Response addShippingMethod(String shippingMethod) {
        Response response = given()
                .queryParam("api_token", Constants.AUTH_TOKEN)
                .multiPart("shipping_method", shippingMethod)
                .when()
                .post(Routes.addShippingMethod_url);

        return response;
    }
}
