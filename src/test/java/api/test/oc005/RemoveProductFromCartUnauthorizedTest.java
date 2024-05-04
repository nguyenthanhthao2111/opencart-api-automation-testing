package api.test.oc005;

import api.endpoints.CartController;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RemoveProductFromCartUnauthorizedTest {
    /**
     * NAME: [OC005] - Remove product from cart unauthorized
     */
    @SneakyThrows
    @Test
    public void removeProductUnauthorized() {
        //Act
        Response responseDelete = CartController.deleteProductInCart("");

        //Assert
        Assert.assertEquals(responseDelete.statusCode(), 200);
        Assert.assertEquals(responseDelete.jsonPath().getString("error"), "Warning: You do not have permission to access the API!");
    }
}
