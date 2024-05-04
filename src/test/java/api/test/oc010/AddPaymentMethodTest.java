package api.test.oc010;

import api.Constants;
import api.endpoints.PaymentController;
import api.endpoints.UserController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class AddPaymentMethodTest {
    Faker fake;
    private ObjectMapper mapper = new ObjectMapper();
    private String firstname;
    private String lastname;
    private String address;
    private String city;
    private String countryId;
    private String zoneId;
    public static String paymentMethod;

    @BeforeClass
    public void dataSetup() {
        fake = new Faker(new Locale("en-us"));
        firstname = fake.name().firstName();
        lastname = fake.name().lastName();
        address = String.valueOf(fake.address());
        city = String.valueOf(fake.address().city());
        countryId = String.valueOf(fake.address().zipCode());
        zoneId = String.valueOf(fake.address().zipCodeByState("CA"));
    }

    /**
     * NAME: [OC010] - [TC01001] Add payment method successfully
     */

    @SneakyThrows
    @Test
    public void addPaymentMethodSuccessfully() {
        UserController.loginUser(Constants.OC_USERNAME, Constants.OC_API_KEY);
        //Set up data

        //Add a payment address
        Response addPaymentAddressResponse = PaymentController.addPaymentAddress(firstname, lastname, address, city, countryId, zoneId);
        addPaymentAddressResponse.then().log().all();
        Assert.assertEquals(addPaymentAddressResponse.statusCode(), 200);
        TimeUnit.SECONDS.sleep(3);

        //Get payment method
        Response getPaymentResponse = PaymentController.getPaymentMethod();
        getPaymentResponse.then().log().all();
        paymentMethod = getPaymentResponse.jsonPath().getString("payment_methods.bank_transfer.code");
        TimeUnit.SECONDS.sleep(3);

        //Act
        Response response = PaymentController.addPaymentMethod(paymentMethod);
        response.then().log().all();

        //Assert
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("success"), "Success: Payment method has been set!");
    }

    /**
     * NAME: [OC010] - [TC01002, TC01003] Add payment method with invalid payment method
     */

    @DataProvider(name = "paymentMethodProvider")
    private static Object[][] paymentMethodProvider() { return new Object[][]{{""}, {RandomStringUtils.randomAlphabetic(500)} };}

    @SneakyThrows
    @Test (dataProvider = "paymentMethodProvider")
    public void addPaymentMethodInvalidPaymentMethod(String paymentMethod) {
        UserController.loginUser(Constants.OC_USERNAME, Constants.OC_API_KEY);
        //Act
        Response response = PaymentController.addPaymentMethod(paymentMethod);
        response.then().log().all();

        //Assert
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("error"), "Warning: No Payment options are available!");
    }
}
