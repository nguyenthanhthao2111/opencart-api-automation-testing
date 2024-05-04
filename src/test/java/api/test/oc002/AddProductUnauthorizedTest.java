package api.test.oc002;

import api.endpoints.CartController;
import api.payload.ErrorDetails;
import api.payload.GeneralResponse;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AddProductUnauthorizedTest {

    /**
     * NAME: [OC002] - Add product to cart unauthorized
     */

    @SneakyThrows
    @Test
    public void addProductUnauthorized() {
        //Act
        Response response = CartController.addProductToCart("", "");
        response.then().log().all();
        GeneralResponse errorResponse = response.as(GeneralResponse.class);
        ErrorDetails errorDetails = errorResponse.getError();

        //Assert
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(errorDetails.getWarning(), "Warning: You do not have permission to access the API!");
    }
}
