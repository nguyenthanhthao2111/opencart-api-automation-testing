package api.test.oc002;

import api.Constants;
import api.endpoints.CartController;
import api.endpoints.UserController;
import api.payload.ErrorDetails;
import api.payload.GeneralResponse;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Random;

public class AddProductTest {
    Random random = new Random();
    private String productId;
    private String quantity;

    @BeforeMethod
    public void authToken() {
        UserController.loginUser(Constants.OC_USERNAME, Constants.OC_API_KEY);
    }

    @BeforeClass
    public void dataSetup() {
        productId = String.valueOf(random.nextInt(43 - 28 + 1) + 28);
        quantity = String.valueOf(random.nextInt(100 - 7 + 1) + 7);
    }

    /**
     * NAME: [OC002] - [TC00201] Add product to cart without required fields
     */

    @SneakyThrows
    @Test (priority = 1)
    public void addProductToCartWithoutRequiredFields() {
        //Act
        Response response = CartController.addProductToCart("", "");
        response.then().log().all();
        GeneralResponse errorResponse = response.as(GeneralResponse.class);
        ErrorDetails errorDetails = errorResponse.getError();

        //Assert
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(errorDetails.getStore(), "Product can not be bought from the store you have choosen!");
    }

    /**
     * NAME: [OC002] - [TC00202] Add product to cart with invalid quantity
     */

    @SneakyThrows
    @Test (priority = 2)
    public void addProductToCartWithInvalidQuantity() {
        //Set up data
        String invalidQuantity = RandomStringUtils.randomNumeric(10000);

        //Act
        Response response = CartController.addProductToCart(productId, invalidQuantity);
        response.then().log().all();
        GeneralResponse errorResponse = response.as(GeneralResponse.class);
        ErrorDetails errorDetails = errorResponse.getError();

        //Assert
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(errorDetails.getStore(), "Product can not be bought from the store you have choosen!");
    }

    /**
     * NAME: [OC002] - [TC00203] Add product to cart successfully
     */

    @SneakyThrows
    @Test (priority = 3)
    public void addProductToCartSuccessfully() {
        //Act
        Response response = CartController.addProductToCart(productId, quantity);
        response.then().log().all();

        //Assert
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("success"), "Success: You have modified your shopping cart!");

        //Clean data
        Response getResponse = CartController.getProductInCart();
        String cartId = getResponse.jsonPath().getString("products[0].cart_id");
        CartController.deleteProductInCart(cartId);
    }

}
