package api.test.oc014;

import api.Constants;
import api.endpoints.OrderController;
import api.endpoints.UserController;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AddNewOrderWithoutItemTest {

    @BeforeMethod
    public void authToken() {
        UserController.loginUser(Constants.OC_USERNAME, Constants.OC_API_KEY);
    }

    /**
     * NAME: [OC014] - Add new order without add item in cart
     */

    @SneakyThrows
    @Test
    public void addNewOrderWithoutItem() {
        //Act
        Response response = OrderController.addNewOrder();
        response.then().log().all();

        //Assert
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("error"), "Warning: Products marked with *** are not available in the desired quantity or not in stock!");
    }

}
