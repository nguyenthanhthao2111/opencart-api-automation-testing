package api.endpoints;

import api.Constants;
import io.restassured.response.Response;

import java.util.List;

import static io.restassured.RestAssured.given;

public class CartController {

    public static Response addProductToCart(String productId, String quantity) {
        Response response = given()
                .queryParam("api_token", Constants.AUTH_TOKEN)
                .multiPart("product_id", productId)
                .multiPart("quantity", quantity)

                .when()
                .post(Routes.addProductToCart_url);

        return response;
    }

    public static Response getProductInCart() {
        Response response = given()
                .queryParam("api_token", Constants.AUTH_TOKEN)

                .when()
                .get(Routes.getProductInCart_url);

        return response;
    }

    public static Response updateProductInCart(String cartId, String quantity) {
        Response response = given()
                .queryParam("api_token", Constants.AUTH_TOKEN)
                .multiPart("key", cartId)
                .multiPart("quantity", quantity)

                .when()
                .post(Routes.updateProductInCart_url);

        return response;
    }

    public static Response deleteProductInCart(String cardId) {
        Response response = given()
                .queryParam("api_token", Constants.AUTH_TOKEN)
                .multiPart("key", cardId)

                .when()
                .delete(Routes.deleteProductInCart_url);

        return response;
    }

    public static void deleteProductInCart(List<String> cardIds) {
        Response response = given()
                .queryParam("api_token", Constants.AUTH_TOKEN)
                .multiPart("key", cardIds)

                .when()
                .delete(Routes.deleteProductInCart_url);

    }

    public static void delete() {
        Response response = given()
                .queryParam("api_token", Constants.AUTH_TOKEN)
                .when()
                .delete(Routes.deleteProductInCart_url);
    }
}
