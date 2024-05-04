package api.test.oc008;

import api.endpoints.PaymentController;
import api.payload.ErrorDetails;
import api.payload.GeneralResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Locale;

public class AddPaymentAddressUnauthorizedTest {
    Faker fake;
    private ObjectMapper mapper = new ObjectMapper();
    private String firstname;
    private String lastname;
    private String address;
    private String city;
    private String countryId;
    private String zoneId;

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
     * NAME: [OC008] - Add payment address unauthorized
     */

    @SneakyThrows
    @Test
    public void addPaymentAddressUnauthorized() {
        //Act
        Response response = PaymentController.addPaymentAddress(firstname, lastname , address, city, countryId, zoneId);
        response.then().log().all();
        GeneralResponse errorResponse = response.as(GeneralResponse.class);
        ErrorDetails errorDetails = errorResponse.getError();

        //Assert
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(errorDetails.getWarning(), "Warning: You do not have permission to access the API!");
    }
}
