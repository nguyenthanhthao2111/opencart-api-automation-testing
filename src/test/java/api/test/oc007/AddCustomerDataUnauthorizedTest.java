package api.test.oc007;

import api.endpoints.UserController;
import api.payload.ErrorDetails;
import api.payload.GeneralResponse;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class AddCustomerDataUnauthorizedTest {
    Faker fake;
    private String firstname;
    private String lastname;
    private String email;
    private String telephone;

    @BeforeClass
    public void dataSetup() {
        fake = new Faker();
        firstname = fake.name().firstName();
        lastname = fake.name().lastName();
        email = fake.internet().emailAddress();
        telephone = String.valueOf(fake.phoneNumber().cellPhone());
    }

    /**
     * NAME: [OC007] - Add customer data unauthorized
     */

    @SneakyThrows
    @Test
    public void addCustomerDataUnauthorized() {
        //Act
        Response response = UserController.addCustomerData(firstname, lastname, email, telephone);
        response.then().log().all();
        GeneralResponse errorResponse = response.as(GeneralResponse.class);
        ErrorDetails errorDetails = errorResponse.getError();

        //Assert
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(errorDetails.getWarning(), "Warning: You do not have permission to access the API!");
    }

}
