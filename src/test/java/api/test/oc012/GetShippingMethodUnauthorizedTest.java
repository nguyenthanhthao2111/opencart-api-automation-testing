package api.test.oc012;

import api.endpoints.ShippingController;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GetShippingMethodUnauthorizedTest {
    /**
     * NAME: [OC012] - Get shipping method unauthorized
     */

    @SneakyThrows
    @Test
    public void getShippingMethodUnauthorized() {
        //Act
        Response response = ShippingController.getShippingMethod();
        response.then().log().all();

        //Assert
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("error"), "Warning: You do not have permission to access the API!");    }
    }
