package api.test.oc012;

import api.Constants;
import api.endpoints.CartController;
import api.endpoints.ShippingController;
import api.endpoints.UserController;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class GetShippingMethodTest {
    Faker fake;
    private String firstname;
    private String lastname;
    private String address;
    private String city;
    private String countryId;
    private String zoneId;
    private String productId;
    private String quantity;

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
        productId = String.valueOf(Integer.parseInt(RandomStringUtils.randomNumeric(2)) % 16 + 28);
        quantity = String.valueOf(Integer.parseInt(RandomStringUtils.randomNumeric(2)) % 2 + 1);
    }

    /**
     * NAME: [OC012] - Get shipping method successfully
     */

    @SneakyThrows
    @Test
    public void getShippingMethodSuccessfully() {
        UserController.loginUser(Constants.OC_USERNAME, Constants.OC_API_KEY);
        //Set up data
        //Add a product to cart
        Response addProductResponse = CartController.addProductToCart(productId, quantity);
        addProductResponse.then().log().all();
        Assert.assertEquals(addProductResponse.statusCode(), 200);
        TimeUnit.SECONDS.sleep(3);

        //Add shipping address
        Response addShippingAddressResponse = ShippingController.addShippingAddress(firstname, lastname, address, city, countryId, zoneId);
        addShippingAddressResponse.then().log().all();
        Assert.assertEquals(addShippingAddressResponse.statusCode(), 200);
        TimeUnit.SECONDS.sleep(3);

        //Act
        Response response = ShippingController.getShippingMethod();
        response.then().log().all();

        //Assert
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("shipping_methods.flat.quote.flat.code"), "flat.flat");
    }

    /**
     * NAME: [OC012] - Get shipping method without shipping address
     */

    @SneakyThrows
    @Test
    public void getShippingMethodWithoutShippingAddress() {
        //Set up data
        //Add a product to cart
        Response addProductResponse = CartController.addProductToCart(productId, quantity);
        addProductResponse.then().log().all();
        Assert.assertEquals(addProductResponse.statusCode(), 200);
        TimeUnit.SECONDS.sleep(3);

        //Act
        Response response = ShippingController.getShippingMethod();
        response.then().log().all();

        //Assert
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("error"), "Warning: Shipping address required!");
    }
}
