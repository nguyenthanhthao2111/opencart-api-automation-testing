package api.endpoints;

import api.Constants;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class PaymentController {
    public static Response addPaymentAddress(String firstname, String lastname, String address, String city, String countryId, String zoneId) {
        Response response = given()
                .queryParam("api_token", Constants.AUTH_TOKEN)
                .multiPart("firstname", firstname)
                .multiPart("lastname", lastname)
                .multiPart("address_1", address)
                .multiPart("city", city)
                .multiPart("country_id", countryId)
                .multiPart("zone_id", zoneId)

                .when()
                .post(Routes.addPaymentAddress_url);

        return response;
    }

    public static Response getPaymentMethod() {
        Response response = given()
                .queryParam("api_token", Constants.AUTH_TOKEN)
                .when()
                .post(Routes.getPaymentMethod_url);

        return response;
    }

    public static Response addPaymentMethod(String paymentMethod) {
        Response response = given()
                .queryParam("api_token", Constants.AUTH_TOKEN)
                .multiPart("payment_method", paymentMethod)

                .when()
                .post(Routes.addPaymentMethod_url);

        return response;
    }



}
