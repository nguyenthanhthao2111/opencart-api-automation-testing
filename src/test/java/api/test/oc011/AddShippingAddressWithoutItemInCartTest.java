package api.test.oc011;

import api.Constants;
import api.endpoints.ShippingController;
import api.endpoints.UserController;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Locale;
import java.util.Random;

public class AddShippingAddressWithoutItemInCartTest {
    Random random = new Random();
    Faker fake;
    private String firstname;
    private String lastname;
    private String address;
    private String city;
    private String countryId;
    private String zoneId;
    private String productId;
    private String quantity;

    @SneakyThrows
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
        productId = String.valueOf(random.nextInt(43 - 28 + 1) + 28);
        quantity = String.valueOf(random.nextInt(100 - 7 + 1) + 7);
    }

    /**
     * NAME: [OC011] - Add shipping address without add item in cart
     */

    @SneakyThrows
    @Test
    public void addShippingAddressWithoutItemInCart() {
        //Act
        Response response = ShippingController.addShippingAddress(firstname, lastname , address, city, countryId, zoneId);
        response.then().log().all();

        //Assert
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.getBody().asString(), "[]");
    }
}
