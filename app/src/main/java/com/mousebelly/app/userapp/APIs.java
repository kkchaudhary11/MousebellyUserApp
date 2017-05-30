package com.mousebelly.app.userapp;

/**
 * Created by Kamal Kant on 25-05-2017.
 */

public class APIs {


    public static final String DOMAIN = "http://mousebelly.herokuapp.com";



    //UserDetails, User Location, LoginActivity
    public static final String sign_sign  = DOMAIN + "/sign/sign/";

    //pre order stack
    public static final String preorder_preorderbymail = DOMAIN + "/preorder/preorderbymail/";

    //Products
    public static final String housewifesign_getauthorisedhw = DOMAIN + "/housewifesign/getauthorisedhw/";


    public static final String housewifesign_housewifesign = DOMAIN + "/housewifesign/housewifesign/";



    //Login Activity
    public static final String sign_sockSessionid = DOMAIN + "/sign/sockSessionid/";
    public static final String sign_LoggedinCheck = DOMAIN + "/sign/LoggedinCheck/";
    public static final String sign_isConnectedtrue = DOMAIN + "/sign/isConnectedtrue/";
    public static final String mail_sendotp = DOMAIN + "/mail/sendotp/";

    //GetFavItems
    public static final String prod_approval_prod_approval11 = DOMAIN + "/prod_approval/prod_approval11/";


    //OrderPayment
    public static final String ccavRequestHandler = DOMAIN + "/ccavRequestHandler";
    public static final String order_order = "/order/order";
    public static final String order_walletOrder = "/order/walletOrder";

    //order stack
    public static final String order_order2 = DOMAIN + "/order/order2/";

    //PaymentStatus
    public static final String order_order3 = DOMAIN + "/order/order3/";


    //orderFood
    public static final String mealplan_mealplan = DOMAIN + "/mealplan/mealplan/";

    //WalletHandler
    public static final String sign_walletRemainbal = DOMAIN + "/sign/walletRemainbal/";
    public static final String walletmoneyupdate = DOMAIN + "/sign/walletmoneyupdate/";


}
