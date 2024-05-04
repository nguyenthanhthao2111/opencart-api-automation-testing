package api.endpoints;

import api.Constants;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class VoucherController {

    public static Response applyVoucher(String voucherCode) {
        Response response = given()
                .queryParam("api_token", Constants.AUTH_TOKEN)
                .multiPart("voucher", voucherCode)

                .when()
                .post(Routes.addVoucher_url);

        return response;
    }
}
