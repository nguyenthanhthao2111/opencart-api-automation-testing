package api.test.oc011;

import api.Constants;
import api.endpoints.CartController;
import api.endpoints.ShippingController;
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
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class AddShippingAddressTest {
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
     * NAME: [OC010] - Add shipping address successfully
     */

    @SneakyThrows
    @Test (priority = 1)
    public void addShippingAddressSuccessfully() {
        //Set up data
        //Add a product to cart
        Response addProductResponse = CartController.addProductToCart(productId, quantity);
        addProductResponse.then().log().all();
        Assert.assertEquals(addProductResponse.statusCode(), 200);
        TimeUnit.SECONDS.sleep(3);

        //Act
        Response response = ShippingController.addShippingAddress(firstname, lastname , address, city, countryId, zoneId);
        response.then().log().all();

        //Assert
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("success"), "Success: Shipping address has been set!");
    }

    /**
     * NAME: [OC012] - Add shipping address with invalid address
     */

    @DataProvider(name = "addressProvider")
    private static Object[][] addressProvider() { return new Object[][]{{RandomStringUtils.randomAlphabetic(500)}, {""} };}

    @SneakyThrows
    @Test (dataProvider = "addressProvider", priority = 2)
    public void addShippingAddressInvalidAddress(String address) {
        //Set up data
        //Add a product to cart
        Response addProductResponse = CartController.addProductToCart(productId, quantity);
        addProductResponse.then().log().all();
        Assert.assertEquals(addProductResponse.statusCode(), 200);
        TimeUnit.SECONDS.sleep(3);

        //Act
        Response response = ShippingController.addShippingAddress(firstname, lastname , address, city, countryId, zoneId);
        response.then().log().all();
        GeneralResponse errorResponse = response.as(GeneralResponse.class);
        ErrorDetails errorDetails = errorResponse.getError();

        //Assert
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(errorDetails.getAddress_1(), "Address 1 must be between 3 and 128 characters!");
    }

    /**
     * NAME: [OC011] - Add shipping address with invalid city
     */

    @DataProvider(name = "cityProvider")
    private static Object[][] cityProvider() { return new Object[][]{{RandomStringUtils.randomAlphabetic(500)}, {""} };}

    @SneakyThrows
    @Test (dataProvider = "cityProvider", priority = 3)
    public void addShippingAddressInvalidCity(String city) {
        //Set up data
        //Add a product to cart
        Response addProductResponse = CartController.addProductToCart(productId, quantity);
        addProductResponse.then().log().all();
        Assert.assertEquals(addProductResponse.statusCode(), 200);
        TimeUnit.SECONDS.sleep(5);

        //Act
        Response response = ShippingController.addShippingAddress(firstname, lastname , address, city, countryId, zoneId);
        response.then().log().all();
        GeneralResponse errorResponse = response.as(GeneralResponse.class);
        ErrorDetails errorDetails = errorResponse.getError();

        //Assert
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(errorDetails.getCity(), "City must be between 3 and 128 characters!");
    }

}
