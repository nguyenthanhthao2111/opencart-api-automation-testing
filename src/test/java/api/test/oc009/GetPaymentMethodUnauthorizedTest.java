package api.test.oc009;

import api.endpoints.PaymentController;
import api.endpoints.UserController;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GetPaymentMethodUnauthorizedTest {

    /**
     * NAME: [OC009] - Get payment method unauthorized
     */

    @SneakyThrows
    @Test(priority = 2)
    public void getPaymentMethodUnauthorized() {
        //Act
        Response response = PaymentController.getPaymentMethod();
        response.then().log().all();

        //Assert
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("error"), "Warning: You do not have permission to access the API!");
    }
}
