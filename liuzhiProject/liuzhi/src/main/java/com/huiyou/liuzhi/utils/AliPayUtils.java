package com.huiyou.liuzhi.utils;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;

import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Map;

/**
 * @author Joe
 * @version 1.0
 * @date 2019/12/27 13:05
 */
public class AliPayUtils {
    /**
     * 沙箱环境参数
     */
    // appid
    private static String APP_ID = "2018121062529203";
    private static String SAPP_ID = "2016101600701698";

    // app私钥
    private static String APP_PRIVATE_KEY = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCFCp4WCqIiozhKnrdfYA28m6sCOzqJpej7CL0JkE3Vat7kgd8JLGkFPOTig1J5vs6JKhdDL7xgp8FQSoFgDx+mzNxzmuIWblXemvSY1ooVP4sH3hJfrqZ1jOb6AWI71FW72KxYwhf4lOkioXtFqnuIZxmkBdDKpcxkECa9xqhnvdmswsMO+U8dY/+edz9hR0CeuPir/KP+ToSSLxH4Qpbgy+zUzB9ERGHw0UTarSI5D2Yqxefa+nfuN+BwvExVN5TX7NHbuJRYDg2XWNlPgiYCNmTL+vivK5HKFp6orso9oW7ohHQ/cj54/cpabtB8tz7zyuEjl9PbrbTXemDPsHyzAgMBAAECggEAG4NLmAFQ8FrSvFPAVj/190g2ADshau/dk3SGK2ElR3pXZ+gXqLz/Zu7l4pYHy1eTPLhwoRtRGKf4iVMvGB9oi0UYXevsz3i311YyOR085xIB/XmGUTU8BVlDNvZsSzUpGbTAR4a0EFszL083v/t2mom/urTdLdtFvne8m0hm8TT5FzUo76kYAAbhGf32PEMBbINobxsn4MpMDYF9WtVnS2U4ff0r92CjBJk2SzOpvuyzILYVscZ2A+mwYf/sSZcwrxnEdun0mN6xjc87vquBeOsVjXBf/QKTkaqCsukmMVxUMg/uMnIXr4tLLBMeqi6Y9qyDUANRJJqRXIhBZob1aQKBgQD3hddM7hiZD2gDqkkgpi2TTG521afPQ5fYX7OwH+tDKvFiFZXdM8TSJR5R1TBYqjrIVyISnS2f7zt46oC5yV/Zt9JV6JzZNeDWVdWp39uNJgHRYOKwXu/rDYubSRkb2ETyIZ+emvqh4GeH0EdZTBK1klhp8TibFbxJbA7gn9ZtjQKBgQCJmQ9inqOOPBgbcaNvIuPGOdojfy+oeVE0dvs+QKF7ZkjHeHEipFk1v5Fsd/krznCK6UhT8z48uRWFWwwPycn5h02pg/VqMsFoz4XDOVc5QYOxMjQAz992hxYQ0HK8KAAl/o6LqrHwF2gjiULIogIllgF0QlI0VfnE5N0BPUZjPwKBgCW0RpKaZjbSiPad9tu5VJHuf5ksfI5u7m9wTI/UnOKzZOebACQc4atdKhwvDFlFPQUNeMw7ExsctwN5PTiPu0EP5kNd1p6jvWcI1i41Ql1i374EMyPIomU8v/pcjS2XqRD1HDptwyXrZ/nhOS0aEHOGIl+o9gVxryfEBRQkZOZ1AoGAHdL0N78E2maCHyixcPxR/Hw7oOgHb2/kPwfvCx49Ih8YdUvQ0d5ni7PbFHSoqGsVj7Ziu/eELOs9E+uZIK7vPUurdKuqfPtFWq9oYdlC46EkpzqndOmZmPpMuRZFWC0fYxzazsEP0mdwZgtxU0VyS61I4WcipD9iZN/qW0Npx0cCgYBcMLAOm9IV4Gb4iS3xv8bwhbBuza6eTEsT2fL+uMIrxyuC60jZB2MdWP9DfrVLKMYY7KEaP6uF4W+okLxjA3hBPXWT4BnD2yCynFDiTQQFELpMC1mahhkeBlLKVMIo26wddiFR3yxf5JtTN2dGHX8+x68qNk11SvHejWnloZK1RQ==";
    private static String SAPP_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCHvK3CeS3/g39W78oApxY+CNKvUH3in3FHEhC04IelgJLN10kxtBSCGErl+hl3SEYKvdEGQWsr2Qn/5xDYd+RRKk9NEGsEpZ3Cblr1j+CRE8H8V8ACVcjnyxcubsjE9d5FcSUGQMFtJLzbkp1mKebHtV45uZqao6QmiWgAImTtAyPMbr6130CUX8Z9R3ca3u87hP5euBM91irSxTtYRNU4lxgGtTMO+A5E04+lf3WThwvmjg6kCMoHFWAUmw32Xv2u97hbZgVUoLK13dWcVS59gEgohzpeKHLqwKppYsBnuWWnHFMEyPLtXhtar0cD56FWlKeMrfBcJGvp3tR9PYUZAgMBAAECggEAfFboTRc4fbF1eqvZvVNR2q22YYY4atpnqBTyXN65I3BuCkVF9VPi3XFnWtgJb+0yzDApQ7O1esssj9CY69KXwYoibpO1lOIpa6wvgnaz3fHdZ0zyxLDUgxDVQWGWZGZdn/sPUXUMOvDGV9UsfiOwFg0cLYDKERIKEosYS9c14K4D5v+RS+0jmKq0t/n9ZuIE8FIyh5Nr6hrAGBj2+92Gg7wvbZmgOo5Iw5v+hv6SMO6iZfRSaaL3wQHcKcLEBLm2KExj7Zj9RC57cTEljBDsRoXe3WTLT96jrGJ+t351Bc7k8rT12D38H0VztxpaG/qeYcR//zg10fXw9Gp7q7hJAQKBgQDBIVscnKlqMOjQVCyzQiFZLdwCzILL6nnRIqABkY60d1pxUTKWGGbEr0VCShA8CWNaNarGf24rd1FOUJuXDMbjAwTGixYnFxI1ImLix+hLFnMg46sYTvxJn9ZZtRDhP5aC2T03RfpGXNdzrURFZNeBfy6LHHlzW1lU5ccHz98gCQKBgQCz7GpxjEIG1vtgr+lageW2qAeV8kyHjBkX/3kXIUGgg9H4xWt+O/QGWJC5Q5nldZCuw1/hsuVojPqNkSN0qcJtaBgK55UU7cGpw35DUzvOKRDMNG1LLB5STUS4n8uUaXB5m1lcsXpeSNzh6FuxSA1XsWIyj/s5N8Tgk7lNqVFgkQKBgHoNI3BkjSg/+pHV8F3k+ABM3BKkvjAyYYPKdwYmv+HK38xnPSngB+pavdFTzwiyAii7a86AL9amVCrDBGEkQPisKrNyqLBRgz8zGP0qyuHRMTbiynjVSdkZezQ8GovPTMYqleY9gho/iCVhpZiZTxxWjMp7V0GQxFbD09FbDC5JAoGBALMFMIP6Qlf73/K8FTtws9eiKKoRhAP6aZ1vHFTfSiUkA++6vtHDZXJK/PTZRm8BIwxAaU5wacI1Q7dICjcyEbo6MHHpKnTAfEklsqPaVU8znPsRzw1UwHazWwU4OZ9ZqgfVgnywOklFxkEgqNskDeKLJGGa0SSFUOcDFQUoy0eBAoGAJ+QMwdEidQy2v4suD8ykhPaYMgh+J68gz/fuoFOA5kvB4oNzTXOCrun0VyEhdgDHS3KjTBGw8RgZvXTNYF/1MXJVo7SONzSvCqq1k/AdeJIbweJiSSqitbyTI4KNG1/4Jdal7scQ6ZKe6WgPWvSJIe40sJwTHQEunS2lJG6KUPI=";
    private static String CHARSET = "UTF-8";
    // 支付宝公钥
    private static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhaf+N2tqb+WnWQ5aAahujwYu2fAoXzhhxeHP1ZgV1sslwUeLjJb60/BYBUZQ8FWIwrJQ3i6xGhs+ls5l2Vu7oW2YckIAahHfuwXRKi+vXcSQun3TSrQNXSB8Lxq82LClIgwGwNrevezsMBIlXY+/byXpO9fGXpFHGzeNlaLtaIP3HMDUkudjA0nSoqcgcIa76CiCDgA3MIaclZgDSj/XSatlkmeMC5uRQSvuwMbBo7moKUMZdktW0mCEWxDYjlFZwJUUbKd/oPAr6A3BdRGRENmtQTWOB+l6lLqGexDGEeQNdh773z0llHGXQKp3610691lEfejzM5snqhGXg7xIAQIDAQAB";
    private static String SALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAj7QZI8O4d0Or5vj0hlpPy975Q8faVY3tbUsJqy1jI4M7RhBd3WkFSz40I8TOBkCaXK5d0ojl7DZTiKTOIAGwMcoRrLUSdeK3PNzLSmCMg2wyV+qtZ02LT5nSjDHQIhw2js5VRxRefllRwqDCskFBGFNasQlyaZzkRt4/MgUqp8IdpEcuvqdvbTvwcxZnGKctPzFGtE7t95CaeF70Ep3slxxIRTXjyP1ImQiCnLkZs056aMJD/zNApxj3pqS9ZSOhhbZ40klfVG5yyvBm7A8JShvyXogzOZWwpZconJGVee0Dj0sF9xWNylFeBNsIrrAJb48bn4IGwUKARCegGOYK7wIDAQAB";
    // 网管
       private static String serverUrl = "https://openapi.alipay.com/gateway.do";
     private static String SserverUrl = "https://openapi.alipaydev.com/gateway.do";
    // 支付宝商家账号id(UID)[
    private static String seller_id = "2088331095091456";
    private static String Sseller_id = "2088102179867841";
    // 支付宝客户端
    private  AlipayClient alipayClient;

    private static AliPayUtils aliPay;


    private AliPayUtils() {
        //实例化客户端
        alipayClient = new DefaultAlipayClient(SserverUrl, SAPP_ID, SAPP_PRIVATE_KEY, "json", CHARSET, SALIPAY_PUBLIC_KEY, "RSA2");
    }

    public static AliPayUtils getInstance() {
        if (aliPay == null) {
            aliPay = new AliPayUtils();
        }
        return aliPay;
    }
    public String pay(String body, String subject, String outTradeNo,String totalAmount){
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
//SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody(body);
        model.setSubject(subject);
        model.setOutTradeNo(outTradeNo);
        model.setTimeoutExpress("30m");
        model.setTotalAmount(totalAmount);
        model.setProductCode("QUICK_MSECURITY_PAY");
        request.setBizModel(model);
        request.setNotifyUrl("http://client2.365hy.com/liuzhi/pay/zfbResult");
        try {
            //这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
//            System.out.println(response.getBody());
            return response.getBody();//就是orderString 可以直接给客户端请求，无需再做处理。
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return null;
    }
    // 支付宝订单号
    public String getApliPayTradeNo(String outTradeNo,String tradeNo) throws AlipayApiException{
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        request.setBizContent("{" +
                "   \"out_trade_no\":\""+outTradeNo+"\"," +
                "   \"trade_no\":\""+tradeNo+"\"" +
                "  }");//设置业务参数
        AlipayTradeQueryResponse response = alipayClient.execute(request);
        if(response.isSuccess()){
          return response.getBody();
        } else {
            System.out.println("调用失败");
            return response.getBody();
        }
    }

    public  Map<String,String> signResult(HttpServletRequest request, HttpServletResponse response) throws AlipayApiException{
//        //获取支付宝POST过来反馈信息
//        Map<String,String> params = new HashMap<String,String>();
//        Map requestParams = request.getParameterMap();
//        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
//            String name = (String) iter.next();
//            String[] values = (String[]) requestParams.get(name);
//            String valueStr = "";
//            for (int i = 0; i < values.length; i++) {
//                valueStr = (i == values.length - 1) ? valueStr + values[i]
//                        : valueStr + values[i] + ",";
//            }
//            //乱码解决，这段代码在出现乱码时使用。
//            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
//            params.put(name, valueStr);
//        }
////切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
////boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
//        boolean flag = AlipaySignature.rsaCheckV1(params, ALIPAY_PUBLIC_KEY, CHARSET,"RSA2");
//        if(flag){
//            return params;
//        }
        return null;
    }
    // 商家支付宝id和应用appid验证
    public static boolean equalsSign(String appId, String sellerId){
        if(SAPP_ID.equals(appId) && Sseller_id.equals(sellerId)){
            return true;
        }
        return false;
    }



    public static void main(String[] args){

    }

    public String payFormal(String body, String subject, String outTradeNo,String totalAmount){
        AlipayClient alipayClientFormal = new DefaultAlipayClient(serverUrl, APP_ID, APP_PRIVATE_KEY, "json", CHARSET, ALIPAY_PUBLIC_KEY, "RSA2");;
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
//SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody(body);
        model.setSubject(subject);
        model.setOutTradeNo(outTradeNo);
        model.setTimeoutExpress("30m");
        model.setTotalAmount(totalAmount);
        model.setProductCode("QUICK_MSECURITY_PAY");
        request.setBizModel(model);
        request.setNotifyUrl("http://client2.365hy.com/liuzhi/pay/zfbResultFormal");
        try {
            //这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClientFormal.sdkExecute(request);
            System.out.println(response.getBody());
            return response.getBody();//就是orderString 可以直接给客户端请求，无需再做处理。
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return "ok";
    }

    // 商家支付宝id和应用appid验证
    public static boolean equalsSignFormal(String appId, String sellerId){
        if(APP_ID.equals(appId) && seller_id.equals(sellerId)){
            return true;
        }
        return false;
    }
}
