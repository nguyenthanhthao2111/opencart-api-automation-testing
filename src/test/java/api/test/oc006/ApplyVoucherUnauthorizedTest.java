package api.test.oc006;

import api.Constants;
import api.endpoints.UserController;
import api.endpoints.VoucherController;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ApplyVoucherUnauthorizedTest {

    /**
     * NAME: [OC007] -  Apply voucher unauthorized
     */

    @SneakyThrows
    @Test
    public void applyVoucherUnauthorized() {
        //Set up data
        UserController.loginUser(Constants.OC_USERNAME_FPT, Constants.OC_API_KEY_FPT);

        //Act
        Response response = VoucherController.applyVoucher(Constants.VOUCHER_CODE);
        response.then().log().all();

        //Assert
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("error"), "Warning: You do not have permission to access the API!");
    }
}
