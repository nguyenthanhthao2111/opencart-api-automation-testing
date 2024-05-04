package api.test.oc006;

import api.Constants;
import api.endpoints.CartController;
import api.endpoints.UserController;
import api.endpoints.VoucherController;
import api.payload.Cost;
import api.payload.Product;
import api.payload.ProductDetails;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.text.DecimalFormat;
import java.util.List;

public class ApplyVoucherTest {
    DecimalFormat decimalFormat = new DecimalFormat("$#,###.00");


    @BeforeMethod
    public void authToken() {
        UserController.loginUser(Constants.OC_USERNAME, Constants.OC_API_KEY);
    }

    @BeforeClass
    public void dataSetup() {
        UserController.loginUser(Constants.OC_USERNAME, Constants.OC_API_KEY);
    }

    /**
     * NAME: [OC007] -  [TC00701, TC00702] Apply invalid voucher
     */

    @DataProvider(name = "voucherProvider")
    private static Object[][] voucherProvider() { return new Object[][]{{RandomStringUtils.randomAlphabetic(10)}, {""} };}

    @SneakyThrows
    @Test (dataProvider = "voucherProvider")
    public void applyInvalidVoucher(String voucherCode) {
        //Act
        Response response = VoucherController.applyVoucher(voucherCode);
        response.then().log().all();

        //Assert
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("error"), "Warning: Gift Voucher is either invalid or the balance has been used up!");
    }

    /**
     * NAME: [OC007] - [TC00703] Apply voucher successfully
     */

    @SneakyThrows
    @Test
    public void applyVoucherSuccessfully() {
        //Set up data
        //Add a product to cart
        Response addProductResponse = CartController.addProductToCart("31", "2");
        addProductResponse.then().log().all();
        Assert.assertEquals(addProductResponse.statusCode(), 200);

        //Get product in cart before applied
        Response getProductResponse = CartController.getProductInCart();
        Product productResponse = getProductResponse.as(Product.class);
        List<ProductDetails> productDetails = productResponse.getProducts();
        getProductResponse.then().log().all();

        String total = productDetails.get(0).getTotal();
        //Convert value from string to double
        String numPart1 = total.replaceAll("[^0-9.]", "");
        double totalValue = Double.parseDouble(numPart1);

        Assert.assertEquals(getProductResponse.statusCode(), 200);

        //Act
        Response response = VoucherController.applyVoucher(Constants.VOUCHER_CODE);
        response.then().log().all();

        //Assert
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("success"), "Success: Your gift voucher discount has been applied!");

        //Get product in cart after apply
        Response getProductAfterVoucherResponse = CartController.getProductInCart();
        Product productResponse2 = getProductAfterVoucherResponse.as(Product.class);
        List<Cost> costDetail = productResponse2.getTotals();
        getProductAfterVoucherResponse.then().log().all();
        String giftVoucherTitle = costDetail.get(3).getTitle();
        String giftVoucherText = costDetail.get(3).getText();
        String totalPriceTitle = costDetail.get(4).getTitle();
        String totalPriceText = costDetail.get(4).getText();

        Assert.assertEquals(getProductAfterVoucherResponse.statusCode(), 200);
        Assert.assertEquals(giftVoucherTitle, "Gift Certificate (" + Constants.VOUCHER_CODE + ")");
        Assert.assertEquals(giftVoucherText, Constants.VOUCHER_CODE_VALUE);
        Assert.assertEquals(totalPriceTitle, "Total");

        //Convert value from string to double
        String numPart = Constants.VOUCHER_CODE_VALUE.replaceAll("[^0-9.]", "");
        double voucherValue = Double.parseDouble(numPart);

        Assert.assertEquals(totalPriceText, decimalFormat.format(totalValue - voucherValue));
    }

}
