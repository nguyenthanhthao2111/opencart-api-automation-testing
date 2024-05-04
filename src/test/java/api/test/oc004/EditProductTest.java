package api.test.oc004;

import api.Constants;
import api.endpoints.CartController;
import api.endpoints.UserController;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Random;

public class EditProductTest {
    Random random = new Random();
    private String productId;
    private String quantity;

    @SneakyThrows
    @BeforeMethod
    public void setAuth() {
        UserController.loginUser(Constants.OC_USERNAME, Constants.OC_API_KEY);
    }

    @BeforeClass
    public void dataSetup() {
        productId = String.valueOf(random.nextInt(43 - 28 + 1) + 28);
        quantity = String.valueOf(random.nextInt(100 - 7 + 1) + 7);
    }

    /**
     * NAME: [OC004] - Edit Product in cart successfully
     */
    @SneakyThrows
    @Test
    public void editProductInCartSuccessfully() {
        //Set up data
        //Add a product to cart
        UserController.loginUser(Constants.OC_USERNAME, Constants.OC_API_KEY);
        CartController.addProductToCart(productId, quantity);
        //Get products
        Response getResponse = CartController.getProductInCart();
        String cartId = getResponse.jsonPath().getString("products[0].cart_id");

        //Act
        Response editResponse = CartController.updateProductInCart(cartId, quantity + 1);
        editResponse.then().log().all();

        //Assert
        Assert.assertEquals(editResponse.statusCode(), 200);
        Assert.assertEquals(editResponse.jsonPath().getString("success"), "Success: You have modified your shopping cart!");

        //Clean data
        CartController.deleteProductInCart(cartId);
    }

    /**
     * NAME: [OC004] - Edit Product in cart without required fields
     */
    @SneakyThrows
    @Test
    public void editProductInCartWithoutRequiredFields() {
        //Set up data
        //Add a product to cart
        UserController.loginUser(Constants.OC_USERNAME, Constants.OC_API_KEY);
        CartController.addProductToCart(productId, quantity);
        //Get products
        Response getResponse = CartController.getProductInCart();
        String cartId = getResponse.jsonPath().getString("products[0].cart_id");

        //Act
        Response editResponse = CartController.updateProductInCart("", "");
        editResponse.then().log().all();

        //Assert
        Assert.assertEquals(editResponse.statusCode(), 200);

        //Clean data
        CartController.deleteProductInCart(cartId);
    }
}
