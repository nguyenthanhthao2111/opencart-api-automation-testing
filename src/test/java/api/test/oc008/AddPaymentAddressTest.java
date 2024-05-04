package api.test.oc008;

import api.Constants;
import api.endpoints.PaymentController;
import api.endpoints.UserController;
import api.payload.ErrorDetails;
import api.payload.GeneralResponse;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Locale;


public class AddPaymentAddressTest {

    Faker fake;
    private String firstname;
    private String lastname;
    private String address;
    private String city;
    private String countryId;
    private String zoneId;

    @BeforeMethod
    public void authToken() {
        UserController.loginUser(Constants.OC_USERNAME, Constants.OC_API_KEY);
    }

    @BeforeClass
    public void dataSetup() {
        UserController.loginUser(Constants.OC_USERNAME, Constants.OC_API_KEY);
        fake = new Faker(new Locale("en-us"));
        firstname = fake.name().firstName();
        lastname = fake.name().lastName();
        address = String.valueOf(fake.address());
        city = String.valueOf(fake.address().city());
        countryId = String.valueOf(fake.address().zipCode());
        zoneId = String.valueOf(fake.address().zipCodeByState("CA"));
    }

    /**
     * NAME: [OC008] - [TC00801] Add payment address successfully
     */

    @SneakyThrows
    @Test
    public void addPaymentAddressSuccessfully() {
        UserController.loginUser(Constants.OC_USERNAME, Constants.OC_API_KEY);
        //Act
        Response response = PaymentController.addPaymentAddress(firstname, lastname , address, city, countryId, zoneId);
        response.then().log().all();

        //Assert
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("success"), "Success: Payment address has been set!");
    }

    /**
     * NAME: [OC008] - [TC00802, TC00803] Add payment address with invalid address
     */

    @DataProvider(name = "addressProvider")
    private static Object[][] addressProvider() { return new Object[][]{{RandomStringUtils.randomAlphabetic(500)}, {""} };}

    @SneakyThrows
    @Test (dataProvider = "addressProvider")
    public void addPaymentAddressInvalidAddress(String address) {
        UserController.loginUser(Constants.OC_USERNAME, Constants.OC_API_KEY);
        //Act
        Response response = PaymentController.addPaymentAddress(firstname, lastname , address, city, countryId, zoneId);
        response.then().log().all();
        GeneralResponse errorResponse = response.as(GeneralResponse.class);
        ErrorDetails errorDetails = errorResponse.getError();

        //Assert
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(errorDetails.getAddress_1(), "Address 1 must be between 3 and 128 characters!");
    }

    /**
     * NAME: [OC008] - [TC00804, TC00805] Add payment address with invalid city
     */

    @DataProvider(name = "cityProvider")
    private static Object[][] cityProvider() { return new Object[][]{{RandomStringUtils.randomAlphabetic(500)}, {""} };}

    @SneakyThrows
    @Test (dataProvider = "cityProvider")
    public void addPaymentAddressInvalidCity(String city) {
        UserController.loginUser(Constants.OC_USERNAME, Constants.OC_API_KEY);
        //Act
        Response response = PaymentController.addPaymentAddress(firstname, lastname , address, city, countryId, zoneId);
        response.then().log().all();
        GeneralResponse errorResponse = response.as(GeneralResponse.class);
        ErrorDetails errorDetails = errorResponse.getError();

        //Assert
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(errorDetails.getCity(), "City must be between 3 and 128 characters!");
    }
}
