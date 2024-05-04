package api.test.oc010;

import api.Constants;
import api.endpoints.PaymentController;
import api.endpoints.UserController;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AddPaymentMethodUnauthorizedTest {

    /**
     * NAME: [OC010] -  Add payment method unauthorized
     */

    @SneakyThrows
    @Test
    public void addPaymentMethodUnauthorized() {
        //Act
        Response response = PaymentController.addPaymentMethod("bank_transfer");
        response.then().log().all();

        //Assert
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("error"), "Warning: You do not have permission to access the API!");
    }

}
