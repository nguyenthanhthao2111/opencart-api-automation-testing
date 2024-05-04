package api.endpoints;

import api.Constants;

public class Routes {
    public static String base_url = "http://"+ Constants.OC_IP_ADDRESS_2 + "/opencart/upload/index.php?route=";
    public static String login_url = base_url + "api/login";

    public static String addProductToCart_url = base_url + "api/cart/add";
    public static String getProductInCart_url = base_url + "api/cart/products";
    public static String updateProductInCart_url = base_url + "api/cart/edit";
    public static String deleteProductInCart_url = base_url + "api/cart/remove";

    public static String addVoucher_url = base_url + "api/voucher";

    public static String addCustomerData_url = base_url + "api/customer";

    public static String addPaymentAddress_url = base_url + "api/payment/address";
    public static String getPaymentMethod_url = base_url + "api/payment/methods";
    public static String addPaymentMethod_url = base_url + "api/payment/method";

    public static String addShippingAddress_url = base_url + "api/shipping/address";
    public static String getShippingMethod_url = base_url + "api/shipping/methods";
    public static String addShippingMethod_url = base_url + "api/shipping/method";

    public static String addNewOrder_url = base_url + "api/order/add";
}
