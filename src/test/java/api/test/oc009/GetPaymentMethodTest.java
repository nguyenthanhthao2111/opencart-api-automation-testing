package api.test.oc009;

import api.Constants;
import api.endpoints.PaymentController;
import api.endpoints.UserController;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.Locale;

public class GetPaymentMethodTest {
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
        fake = new Faker(new Locale("en-us"));
        firstname = fake.name().firstName();
        lastname = fake.name().lastName();
        address = String.valueOf(fake.address());
        city = String.valueOf(fake.address().city());
        countryId = String.valueOf(fake.address().zipCode());
        zoneId = String.valueOf(fake.address().zipCodeByState("CA"));
    }

    /**
     * NAME: [OC009] - [TC00901] Get payment method without payment address
     */

    @SneakyThrows
    @Test(priority = 1)
    public void getPaymentMethodWithoutPaymentAddress() {
        //Act
        Response response = PaymentController.getPaymentMethod();
        response.then().log().all();

        //Assert
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("error"), "Warning: Payment address required!");
    }

    /**
     * NAME: [OC009] - [TC00902] Get payment method successfully
     */

    @SneakyThrows
    @Test(priority = 3)
    public void getPaymentMethodSuccessfully() {
        //Set up data
        //Add a payment address
        Response addPaymentAddressResponse = PaymentController.addPaymentAddress(firstname, lastname, address, city, countryId, zoneId);
        addPaymentAddressResponse.then().log().all();
        Assert.assertEquals(addPaymentAddressResponse.statusCode(), 200);

        //Act
        Response response = PaymentController.getPaymentMethod();
        response.then().log().all();

        //Assert
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("payment_methods.bank_transfer.code"), "bank_transfer");

    }
}
