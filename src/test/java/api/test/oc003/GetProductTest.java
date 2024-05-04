package api.test.oc003;

import api.Constants;
import api.endpoints.CartController;
import api.endpoints.UserController;
import api.payload.Product;
import api.payload.ProductDetails;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Random;

public class GetProductTest {
    Random random = new Random();

    @SneakyThrows
    @BeforeMethod
    public void setAuth() {
        UserController.loginUser(Constants.OC_USERNAME, Constants.OC_API_KEY);
    }

    /**
     * NAME: [OC003] - [TC00301] Get Product in cart successfully
     */
    @SneakyThrows
    @Test
    public void getProductInCartSuccessfully() {
        //Set up data: Add a product to cart
        String productId = String.valueOf(random.nextInt(43 - 28 + 1) + 28);
        String quantity = String.valueOf(random.nextInt(100 - 7 + 1) + 7);
        CartController.addProductToCart(productId, quantity);

        //Act
        Response response = CartController.getProductInCart();
        Product productResponse = response.as(Product.class);
        List<ProductDetails> productDetails = productResponse.getProducts();
        response.then().log().all();
        String cartId = response.jsonPath().getString("products[0].cart_id");

        //Assert
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(productDetails.get(0).getProduct_id(), productId);
        Assert.assertEquals(productDetails.get(0).getQuantity(), quantity);

        //Clean data
        CartController.deleteProductInCart(cartId);
    }
}
