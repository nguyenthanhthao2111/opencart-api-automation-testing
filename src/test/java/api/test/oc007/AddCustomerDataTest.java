package api.test.oc007;

import api.Constants;
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

public class AddCustomerDataTest {
    Faker fake;
    private String firstname;
    private String lastname;
    private String email;
    private String telephone;

    @BeforeMethod
    public void authToken() {
        UserController.loginUser(Constants.OC_USERNAME, Constants.OC_API_KEY);
    }

    @BeforeClass
    public void dataSetup() {
        fake = new Faker();
        firstname = fake.name().firstName();
        lastname = fake.name().lastName();
        email = fake.internet().emailAddress();
        telephone = String.valueOf(fake.phoneNumber().cellPhone());
    }

    /**
     * NAME: [OC007] - Add customer data successfully
     */

    @SneakyThrows
    @Test
    public void addCustomerDataSuccessfully() {
        //Act
        Response response = UserController.addCustomerData(firstname, lastname, email, telephone);
        response.then().log().all();

        //Assert
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("success"), "You have successfully modified customers");
    }

    /**
     * NAME: [OC007] - Add customer data with invalid email
     */

    @SneakyThrows
    @Test
    public void addCustomerDataInvalidEmail() {
        //Act
        String invalidEmail = "abc";
        Response response = UserController.addCustomerData(firstname, lastname, invalidEmail, telephone);
        response.then().log().all();
        GeneralResponse errorResponse = response.as(GeneralResponse.class);
        ErrorDetails errorDetails = errorResponse.getError();

        //Assert
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(errorDetails.getEmail(), "E-Mail Address does not appear to be valid!");
    }

    /**
     * NAME: [OC007] - Add customer data with invalid name
     */

    @DataProvider(name = "nameProvider")
    private static Object[][] nameProvider() { return new Object[][]{{RandomStringUtils.randomAlphabetic(500), RandomStringUtils.randomAlphabetic(500)}, {"", ""} };}

    @SneakyThrows
    @Test (dataProvider = "nameProvider")
    public void addCustomerDataInvalidName(String firstname, String lastname) {
        //Act
        Response response = UserController.addCustomerData(firstname, lastname, email, telephone);
        response.then().log().all();
        GeneralResponse errorResponse = response.as(GeneralResponse.class);
        ErrorDetails errorDetails = errorResponse.getError();

        //Assert
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(errorDetails.getFirstname(), "First Name must be between 1 and 32 characters!");
        Assert.assertEquals(errorDetails.getLastname(), "Last Name must be between 1 and 32 characters!");
    }

    /**
     * NAME: [OC007] - Add customer data with invalid telephone
     */

    @DataProvider(name = "telephoneProvider")
    private static Object[][] telephoneProvider() { return new Object[][]{{RandomStringUtils.randomAlphabetic(500)}, {""} };}

    @SneakyThrows
    @Test (dataProvider = "telephoneProvider")
    public void addCustomerDataInvalidTelephone(String telephone) {
        //Act
        Response response = UserController.addCustomerData(firstname, lastname, email, telephone);
        response.then().log().all();
        GeneralResponse errorResponse = response.as(GeneralResponse.class);
        ErrorDetails errorDetails = errorResponse.getError();

        //Assert
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(errorDetails.getTelephone(), "Telephone must be between 3 and 32 characters!");
    }

    /**
     * NAME: [OC007] - Add customer data with invalid telephone with special characters
     */

    @SneakyThrows
    @Test
    public void addCustomerDataSpecialCharTelephone() {
        //Act
        String invalidTelephone = "@@$ks000";
        Response response = UserController.addCustomerData(firstname, lastname, email, invalidTelephone);
        response.then().log().all();
        GeneralResponse errorResponse = response.as(GeneralResponse.class);
        ErrorDetails errorDetails = errorResponse.getError();

        //Assert
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(errorDetails.getTelephone(), "Telephone does not appear to be valid!");
    }
}
