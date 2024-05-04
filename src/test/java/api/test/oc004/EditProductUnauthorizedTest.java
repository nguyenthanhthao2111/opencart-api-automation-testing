package api.test.oc004;

import api.endpoints.CartController;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import org.testng.Assert;
import org.testng.annotations.Test;

public class EditProductUnauthorizedTest {

    /**
     * NAME: [OC004] - Edit Product in cart unauthorized
     */
    @SneakyThrows
    @Test
    public void editProductUnauthorized() {
        //Act
        Response response = CartController.updateProductInCart("", "");
        response.then().log().all();

        //Assert
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("error"), "Warning: You do not have permission to access the API!");

    }
}
