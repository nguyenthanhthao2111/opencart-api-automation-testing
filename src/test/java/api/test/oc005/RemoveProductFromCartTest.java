package api.test.oc005;

import api.Constants;
import api.endpoints.CartController;
import api.endpoints.Routes;
import api.endpoints.UserController;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class RemoveProductFromCartTest {
    @BeforeMethod
    public void authToken() {
        UserController.loginUser(Constants.OC_USERNAME, Constants.OC_API_KEY);
    }

    /**
     * NAME: [OC005] - Remove product from cart successfully
     */
    @SneakyThrows
    @Test
    public void removeProductSuccessfully() {
        //Act
        CartController.addProductToCart("31", "2");
        Response getResponse = CartController.getProductInCart();
        String cartId = getResponse.jsonPath().getString("products[0].cart_id");

        Response responseDelete = CartController.deleteProductInCart(cartId);

        //Assert
        Assert.assertEquals(responseDelete.statusCode(), 200);
    }

    /**
     * NAME: [OC005] - Remove product from cart wrong http method
     */
    @SneakyThrows
    @Test
    public void removeProductWrongHttpMethod() {
        //Act
        CartController.addProductToCart("31", "2");
        Response getResponse = CartController.getProductInCart();
        String cartId = getResponse.jsonPath().getString("products[0].cart_id");

        Response deleteResponse = given()
                .multiPart("key", cartId)
                .when()
                .get(Routes.deleteProductInCart_url);

        deleteResponse.then().log().all();
        //Assert
        Assert.assertEquals(deleteResponse.statusCode(), 200);
    }
}
