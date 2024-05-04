package api.test.oc012;

import api.Constants;
import api.endpoints.ShippingController;
import api.endpoints.UserController;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GetShippingMethodWithoutItemInCartTest {
    /**
     * NAME: [OC012] - Get shipping method without add item in cart
     */

    @SneakyThrows
    @Test
    public void getShippingMethodWithoutItemInCart() {
        UserController.loginUser(Constants.OC_USERNAME, Constants.OC_API_KEY);
        //Act
        Response response = ShippingController.getShippingMethod();
        response.then().log().all();

        //Assert
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("shipping_methods"), "[]");
    }
}
