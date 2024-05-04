package api.test.oc014;

import api.Constants;
import api.endpoints.*;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class AddNewOrderTest {
    Faker fake;
    private String firstname;
    private String lastname;
    private String address;
    private String city;
    private String countryId;
    private String zoneId;
    private String productId;
    private String quantity;
    private String email;
    private String telephone;
    public static String paymentMethod;
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
        email = fake.internet().emailAddress();
        telephone = String.valueOf(fake.phoneNumber().cellPhone());
        productId = String.valueOf(random.nextInt(43 - 28 + 1) + 28);
        quantity = String.valueOf(random.nextInt(100 - 7 + 1) + 7);
    }

    /**
     * NAME: [OC014] - [TC01401] Add new order without add shipping method
     */

    @SneakyThrows
    @Test (priority = 1)
    public void addNewOrderWithoutShippingMethod() {
        //Set up data
        //Add a product to cart
        Response addProductResponse = CartController.addProductToCart(productId, quantity);
        addProductResponse.then().log().all();
        Assert.assertEquals(addProductResponse.statusCode(), 200);
        TimeUnit.SECONDS.sleep(3);

        //Add customer data
        Response addCustomerDataResponse = UserController.addCustomerData(firstname, lastname, email, telephone);
        addCustomerDataResponse.then().log().all();
        Assert.assertEquals(addCustomerDataResponse.statusCode(), 200);
        TimeUnit.SECONDS.sleep(3);

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

        //Add payment method
        Response addPaymentMethodResponse = PaymentController.addPaymentMethod(paymentMethod);
        addPaymentMethodResponse.then().log().all();
        Assert.assertEquals(addPaymentMethodResponse.getStatusCode(), 200);
        TimeUnit.SECONDS.sleep(3);

        //Act
        Response response = OrderController.addNewOrder();
        response.then().log().all();

        //Assert
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("error"), "Warning: Shipping method required!");
    }

    /**
     * NAME: [OC014] - [TC01402] Add new order without add payment method
     */

    @SneakyThrows
    @Test (priority = 2)
    public void addNewOrderWithoutPaymentMethod() {
        //Set up data
        //Add a product to cart
        Response addProductResponse = CartController.addProductToCart(productId, quantity);
        addProductResponse.then().log().all();
        Assert.assertEquals(addProductResponse.statusCode(), 200);
        TimeUnit.SECONDS.sleep(3);

        //Add customer data
        Response addCustomerDataResponse = UserController.addCustomerData(firstname, lastname, email, telephone);
        addCustomerDataResponse.then().log().all();
        Assert.assertEquals(addCustomerDataResponse.statusCode(), 200);
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

        //Add shipping method
        Response addShippingMethodResponse = ShippingController.addShippingMethod(shippingMethod);
        addShippingMethodResponse.then().log().all();
        Assert.assertEquals(addShippingMethodResponse.statusCode(), 200);
        TimeUnit.SECONDS.sleep(3);

        //Act
        Response response = OrderController.addNewOrder();
        response.then().log().all();

        //Assert
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("error"), "Warning: Payment method required!");
    }

//    /**
//     * NAME: [OC014] - Add new order without add customer data
//     */
//
//    @SneakyThrows
//    @Test (priority = 3)
//    public void addNewOrderWithoutCustomerData() {
//        //Set up data
//        //Add a product to cart
//        Response addProductResponse = CartController.addProductToCart(productId, quantity);
//        addProductResponse.then().log().all();
//        Assert.assertEquals(addProductResponse.statusCode(), 200);
//
//        //Add a payment address
//        Response addPaymentAddressResponse = PaymentController.addPaymentAddress(firstname, lastname, address, city, countryId, zoneId);
//        addPaymentAddressResponse.then().log().all();
//        Assert.assertEquals(addPaymentAddressResponse.statusCode(), 200);
//
//        //Get payment method
//        Response getPaymentResponse = PaymentController.getPaymentMethod();
//        getPaymentResponse.then().log().all();
//        paymentMethod = getPaymentResponse.jsonPath().getString("payment_methods.bank_transfer.code");
//
//        //Add payment method
//        Response addPaymentMethodResponse = PaymentController.addPaymentMethod(paymentMethod);
//        addPaymentMethodResponse.then().log().all();
//        Assert.assertEquals(addPaymentMethodResponse.getStatusCode(), 200);
//
//        //Add shipping address
//        Response addShippingAddressResponse = ShippingController.addShippingAddress(firstname, lastname, address, city, countryId, zoneId);
//        addShippingAddressResponse.then().log().all();
//        Assert.assertEquals(addShippingAddressResponse.statusCode(), 200);
//
//        //Get shipping method
//        Response getShippingMethodResponse = ShippingController.getShippingMethod();
//        getShippingMethodResponse.then().log().all();
//        shippingMethod = getShippingMethodResponse.jsonPath().getString("shipping_methods.flat.quote.flat.code");
//        Assert.assertEquals(getShippingMethodResponse.getStatusCode(), 200);
//
//        //Add shipping method
//        Response addShippingMethodResponse = ShippingController.addShippingMethod(shippingMethod);
//        addShippingMethodResponse.then().log().all();
//        Assert.assertEquals(addShippingMethodResponse.statusCode(), 200);
//
//        //Act
//        Response response = OrderController.addNewOrder();
//        response.then().log().all();
//
//        //Assert
//        Assert.assertEquals(response.statusCode(), 200);
//        Assert.assertEquals(response.jsonPath().getString("error"), "Warning: Customer details needs to be set!");
//    }

    /**
     * NAME: [OC014] - [TC01403] Add new order successfully
     */

    @SneakyThrows
    @Test (priority = 3)
    public void addNewOrderSuccessfully() {
        //Set up data
        //Add a product to cart
        Response addProductResponse = CartController.addProductToCart(productId, quantity);
        addProductResponse.then().log().all();
        Assert.assertEquals(addProductResponse.statusCode(), 200);
        TimeUnit.SECONDS.sleep(3);

        //Add customer data
        Response addCustomerDataResponse = UserController.addCustomerData(firstname, lastname, email, telephone);
        addCustomerDataResponse.then().log().all();
        Assert.assertEquals(addCustomerDataResponse.statusCode(), 200);
        TimeUnit.SECONDS.sleep(3);

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

        //Add payment method
        Response addPaymentMethodResponse = PaymentController.addPaymentMethod(paymentMethod);
        addPaymentMethodResponse.then().log().all();
        Assert.assertEquals(addPaymentMethodResponse.getStatusCode(), 200);
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

        //Add shipping method
        Response addShippingMethodResponse = ShippingController.addShippingMethod(shippingMethod);
        addShippingMethodResponse.then().log().all();
        Assert.assertEquals(addShippingMethodResponse.statusCode(), 200);
        TimeUnit.SECONDS.sleep(3);

        //Act
        Response response = OrderController.addNewOrder();
        response.then().log().all();

        //Assert
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("success"), "Success: You have modified orders!");
    }

}
