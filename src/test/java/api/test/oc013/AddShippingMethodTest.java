package api.test.oc013;

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
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class AddShippingMethodTest {
    Faker fake;
    private String firstname;
    private String lastname;
    private String address;
    private String city;
    private String countryId;
    private String zoneId;
    private String productId;
    private String quantity;
    public static String shippingMethod;
    Random random = new Random();

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
     * NAME: [OC013] - Add shipping method with successfully
     */

    @SneakyThrows
    @Test
    public void addShippingMethodSuccessfully() {
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

        //Get shipping method
        Response getShippingMethodResponse = ShippingController.getShippingMethod();
        getShippingMethodResponse.then().log().all();
        shippingMethod = getShippingMethodResponse.jsonPath().getString("shipping_methods.flat.quote.flat.code");
        Assert.assertEquals(getShippingMethodResponse.getStatusCode(), 200);
        TimeUnit.SECONDS.sleep(3);

        //Act
        Response response = ShippingController.addShippingMethod(shippingMethod);
        response.then().log().all();

        //Assert
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("success"), "Success: Shipping method has been set!");
    }

    /**
     * NAME: [OC013] - Add shipping method with invalid shipping method
     */

    @DataProvider(name = "shippingMethodProvider")
    private static Object[][] shippingMethodProvider() { return new Object[][]{{""}, {RandomStringUtils.randomAlphabetic(500)} };}

    @SneakyThrows
    @Test (dataProvider = "shippingMethodProvider")
    public void addShippingMethodInvalidShippingMethod(String shippingMethod) {
        //Set up data
        //Add a product to cart
        Response addProductResponse = CartController.addProductToCart(productId, quantity);
        addProductResponse.then().log().all();
        Assert.assertEquals(addProductResponse.statusCode(), 200);
        TimeUnit.SECONDS.sleep(3);

        //Add shipping address
        Response addShippingAddressResponse = ShippingController.addShippingAddress(firstname, lastname, address, city, countryId, zoneId);
        TimeUnit.SECONDS.sleep(3);
        addShippingAddressResponse.then().log().all();
        Assert.assertEquals(addShippingAddressResponse.statusCode(), 200);
        TimeUnit.SECONDS.sleep(3);

        //Act
        Response response = ShippingController.addShippingMethod(shippingMethod);
        response.then().log().all();

        //Assert
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("error"), "Warning: No Shipping options are available!");
    }

    /**
     * NAME: [OC013] - Add shipping method without shipping address
     */

    @SneakyThrows
    @Test
    public void addShippingMethodWithoutShippingAddress() {
        //Set up data
        //Add a product to cart
        Response addProductResponse = CartController.addProductToCart(productId, quantity);
        addProductResponse.then().log().all();
        Assert.assertEquals(addProductResponse.statusCode(), 200);
        TimeUnit.SECONDS.sleep(3);

        //Act
        Response response = ShippingController.addShippingMethod(RandomStringUtils.randomAlphabetic(10));
        response.then().log().all();

        //Assert
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("error"), "Warning: No Shipping options are available!");
    }
}
