package api.test.oc013;

import api.endpoints.ShippingController;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AddShippingMethodUnauthorizedTest {

    /**
     * NAME: [OC013] - Add shipping method unauthorized
     */

    @SneakyThrows
    @Test
    public void addShippingMethodUnauthorized() {
        //Act
        Response response = ShippingController.getShippingMethod();
        response.then().log().all();

        //Assert
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("error"), "Warning: You do not have permission to access the API!");    }
    }
