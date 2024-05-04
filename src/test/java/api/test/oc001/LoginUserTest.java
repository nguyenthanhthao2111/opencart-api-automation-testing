package api.test.oc001;

import api.Constants;
import api.endpoints.UserController;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginUserTest {

    /**
     * NAME: [OC001] - [TC00101] Login with opencart user successfully
     */

    @SneakyThrows
    @Test
    public void loginUserSuccessfully() {
        Response loginResponse = UserController.loginUser(Constants.OC_USERNAME, Constants.OC_API_KEY);
        loginResponse.then().log().all();

        Assert.assertEquals(loginResponse.statusCode(), 200);
    }

    /**
     * NAME: [OC001] - [TC00102] Login with invalid user unsuccessfully
     */

    @SneakyThrows
    @Test
    public void loginWithInvalidUsername() {
        Response loginResponse = UserController.loginUser(RandomStringUtils.randomAlphanumeric(10), Constants.OC_API_KEY);
        loginResponse.then().log().all();

        Assert.assertEquals(loginResponse.getBody().asString(), "[]");
        Assert.assertEquals(loginResponse.statusCode(), 200);
    }

    /**
     * NAME: [OC001] - [TC00103] Login with blank username unsuccessfully
     */

    @SneakyThrows
    @Test
    public void loginWithBlankUsername() {
        Response loginResponse = UserController.loginUser("", Constants.OC_API_KEY);
        loginResponse.then().log().all();

        Assert.assertEquals(loginResponse.getBody().asString(), "[]");
        Assert.assertEquals(loginResponse.statusCode(), 200);
    }

    /**
     * NAME: [OC001] - [TC00104] Login with too long apikey unsuccessfully
     */

    @SneakyThrows
    @Test
    public void loginWithTooLongApiKey() {
        Response loginResponse = UserController.loginUser(Constants.OC_USERNAME, RandomStringUtils.randomAlphanumeric(100));
        loginResponse.then().log().all();

        Assert.assertEquals(loginResponse.getBody().asString(), "[]");
        Assert.assertEquals(loginResponse.statusCode(), 200);
    }
}
