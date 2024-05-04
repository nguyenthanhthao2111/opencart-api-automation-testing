package api.test.oc013;

import api.Constants;
import api.endpoints.ShippingController;
import api.endpoints.UserController;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AddShippingMethodWithoutItemInCartTest {

    @BeforeMethod
    public void authToken() {
        UserController.loginUser(Constants.OC_USERNAME, Constants.OC_API_KEY);
    }

    /**
     * NAME: [OC013] - Add shipping method without add item in cart
     */

    @SneakyThrows
    @Test
    public void addShippingMethodWithoutItemInCart() {
        //Act
        Response response = ShippingController.getShippingMethod();
        response.then().log().all();

        //Assert
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("shipping_methods"), "[]");
    }
}
