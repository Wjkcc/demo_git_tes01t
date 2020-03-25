package com.huiyou.liuzhi.controller.app;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.huiyou.liuzhi.mapper.OrderCopyMapper;
import com.huiyou.liuzhi.model.Order;
import com.huiyou.liuzhi.model.OrderCopy;
import com.huiyou.liuzhi.model.User;
import com.huiyou.liuzhi.service.PayService;
import com.huiyou.liuzhi.utils.AliPayUtils;
import me.fishlord.common.result.BaseResultEntity;
import me.fishlord.common.result.ResultEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.*;


/**
 * @author Joe
 * @version 1.0
 * @date 2019/12/25 10:31
 */
@Controller
@RequestMapping("pay")
public class AppPayController {
    @Autowired
    private PayService payService;
    @Autowired
    private OrderCopyMapper orderCopyMapper;
    // 支付宝公钥
    private static String aALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhaf+N2tqb+WnWQ5aAahujwYu2fAoXzhhxeHP1ZgV1sslwUeLjJb60/BYBUZQ8FWIwrJQ3i6xGhs+ls5l2Vu7oW2YckIAahHfuwXRKi+vXcSQun3TSrQNXSB8Lxq82LClIgwGwNrevezsMBIlXY+/byXpO9fGXpFHGzeNlaLtaIP3HMDUkudjA0nSoqcgcIa76CiCDgA3MIaclZgDSj/XSatlkmeMC5uRQSvuwMbBo7moKUMZdktW0mCEWxDYjlFZwJUUbKd/oPAr6A3BdRGRENmtQTWOB+l6lLqGexDGEeQNdh773z0llHGXQKp3610691lEfejzM5snqhGXg7xIAQIDAQAB";
    private static String aSALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAj7QZI8O4d0Or5vj0hlpPy975Q8faVY3tbUsJqy1jI4M7RhBd3WkFSz40I8TOBkCaXK5d0ojl7DZTiKTOIAGwMcoRrLUSdeK3PNzLSmCMg2wyV+qtZ02LT5nSjDHQIhw2js5VRxRefllRwqDCskFBGFNasQlyaZzkRt4/MgUqp8IdpEcuvqdvbTvwcxZnGKctPzFGtE7t95CaeF70Ep3slxxIRTXjyP1ImQiCnLkZs056aMJD/zNApxj3pqS9ZSOhhbZ40klfVG5yyvBm7A8JShvyXogzOZWwpZconJGVee0Dj0sF9xWNylFeBNsIrrAJb48bn4IGwUKARCegGOYK7wIDAQAB";

    /**
     * 支付宝异步回调验证
     * 沙箱）
     * @param request
     * @param httpResponse
     * @return
     * @throws Exception
     */
    @RequestMapping("zfbResult")
    @ResponseBody
    public String zfbPayResult(HttpServletRequest request, HttpServletResponse httpResponse) throws Exception {
        request.setCharacterEncoding("UTF-8");
//        OrderCopy orderCopys = new OrderCopy();
//        orderCopys.setOrderNumber("first");
//        orderCopyMapper.insertSelective(orderCopys);
//        OrderCopy orderCopy1 = new OrderCopy();
//        orderCopy1.setOrderNumber("second");

        //获取支付宝POST过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map requestParams = request.getParameterMap();

        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";

            }
            //乱码解决，这段代码在出现乱码时使用。
//            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");

            params.put(name, valueStr);
        }

//        orderCopy1.setSubject(mapsStr(params));
//        orderCopyMapper.insertSelective(orderCopy1);
//        orderCopys.setBuyerMessage(params.toString());
////        orderCopyMapper.insertSelective(orderCopys);
//切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
//boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
        boolean flag = AlipaySignature.rsaCheckV1(params, SALIPAY_PUBLIC_KEY, "UTF-8","RSA2");
        if(flag){
            // 支付宝订单显示交易成功
            if(params.get("trade_status").equals("TRADE_SUCCESS")) {
//                OrderCopy orderCopy = new OrderCopy();
//                orderCopy.setOrderNumber("third"+new Date().toString());
//                orderCopy.setBuyerMessage(params.get("app_id")+"+"+params.get("out_trade_no")+"+"+
//                        params.get("total_amount")+"+"+params.get("seller_id"));
//                orderCopyMapper.insertSelective(orderCopy);
               return  payService.ResultVerification(params.get("app_id"), params.get("out_trade_no"),
                        params.get("total_amount"), params.get("seller_id"), params.get("trade_no"));
               // return "success";

            }
        }
        return "fail";

    }

    /**
     * 支付宝异步回调验证
     * 正式
     * @param request
     * @param httpResponse
     * @return
     * @throws Exception
     */
    @RequestMapping("zfbResultFormal")
    @ResponseBody
    public String zfbPayResultFormal(HttpServletRequest request, HttpServletResponse httpResponse) throws Exception {
        request.setCharacterEncoding("UTF-8");

        //获取支付宝POST过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map requestParams = request.getParameterMap();

        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";

            }
            //乱码解决，这段代码在出现乱码时使用。
//            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");

            params.put(name, valueStr);
        }

        boolean flag = AlipaySignature.rsaCheckV1(params, ALIPAY_PUBLIC_KEY, "UTF-8","RSA2");
        if(flag){
            // 支付宝订单显示交易成功
            if(params.get("trade_status").equals("TRADE_SUCCESS")) {
//
                return  payService.ResultVerificationFormal(params.get("app_id"), params.get("out_trade_no"),
                        params.get("total_amount"), params.get("seller_id"), params.get("trade_no"));


            }
        }
        return "fail";

    }

    /**
     *生成订单
     * @param orderItemId
     * 可以添加多个逗号分隔
     * 商品信息id
     *  商品标题
     * @param subject
     * 支付金额
     * @param totalAmount
     * 数量
     * @param number
     * 买家留言
     * @param buyerMessage
     * 快递id
     * @param expressId
     * 商品总价
     * @param price
     * 最终价格
     * @param finalPrice
     * 快递价格
     * @param expressPrice
     * 折扣
     * @param discount
     * @return
     */
    @RequestMapping("getOrder")
    @ResponseBody
    public BaseResultEntity zfbInf(@RequestAttribute long userId, @RequestParam("orderItemId") String orderItemId, @RequestParam("subject")String subject,
                                   @RequestParam("totalAmount") String totalAmount, @RequestParam("number") String number, String buyerMessage,
                                   @RequestParam("expressId") String expressId, @RequestParam("price") String price,
                                   @RequestParam("finalPrice") String finalPrice, @RequestParam("expressPrice") String expressPrice,
                                   @RequestParam("discount") String discount,@RequestParam("addressId") String addressId ) {
        if(StringUtils.isBlank(orderItemId)){
            return BaseResultEntity.fail("orderItemId不能为空");
        }
        // 前端数据切割，切割为商品数组
//        String[] products = body.split("_");

        return payService.getOrder(userId, orderItemId, subject, totalAmount, Integer.parseInt(number),
                buyerMessage, Long.parseLong(expressId), price, finalPrice, expressPrice ,discount,
                Long.parseLong(addressId));

    }

    /**
     * 支付宝付款（沙箱）
     * @param orderNum
     * @return
     */
    @ResponseBody
    @RequestMapping("aliPay")
    public BaseResultEntity aliPay(@RequestAttribute long userId, @RequestParam("orderNum") String orderNum) {

        if(StringUtils.isBlank(orderNum)) {
            return ResultEntity.fail("订单号为空");
        }
        return payService.aliPay(orderNum);
    }

    /**
     * 支付宝付款(正式）
     * @param orderNum
     * @return
     */
    @ResponseBody
    @RequestMapping("aliPayFormal")
    public BaseResultEntity aliPayFormal(@RequestAttribute long userId, @RequestParam("orderNum") String orderNum) {

        if(StringUtils.isBlank(orderNum)) {
            return ResultEntity.fail("订单号为空");
        }
        return payService.aliPayFormal(orderNum);
    }
    /**
     * 同步结果验证
     * @param
     * @return
     */
    private static String APP_ID = " 2018121062529203";
    private static String SAPP_ID = "2016101600701698";

    // app私钥
    private static String APP_PRIVATE_KEY = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCRmgngRrFzhG8a0uvJ9ItUIFy/qHAq7wVxMoHqxhzc2JhGl+ccAG8a25ESXYOgvIbTkllXL/mWbL4lV94ToLLwxCSSBl1SGw/AZxdFj5n90Ro5ek7u9FAimryAn+8UXL0HnD2IfDeUZaUPbOGTgMWthPPcAx9i/Z3C74EQ21YLuH4Y5lDaqjxPk99EeB67FgWK8LydxXE7jEkEVYQ9+G+mVqzkStTJokn8LDeW+PZO8nZaBh7XEwO66lvuo/2s3u9nf44TlBIwc9eBUVC3JQFMOTUsEGpIumBDx3YIY+PH918QbwicnGf/nF3I03q+5u5cKhSTFQXELPddZ72l1ALtAgMBAAECggEAKfnsyvnWguU1WthgHfMgj/pcguy+mcIbIY1cIyFGhOz0ybVVbZ86aNXQNyk6n4Z6oFha5WXxYMqs7vssDSCRNhR2LpsjIF6o5eFStj02OhSkqNl4FC/fQnq1yadSN96cqKsEmmqlY2UCdzX+upWvZ90iqNVFoTZY2zBNV1zEq8/uKbz9nDUyBYVEVWK6ydaQfGR6rmQTZUwqiIwJ5H3o15cY4HrYPmsjNttpVXSqYWPLZOhquvpI9EEWaulJWNaoCjximRrIIliKVNRfiIzpDTStapcw6imn2UtCcheQN4YwZ//zhXCKPSxxqjHQPEUGxBiC9QRj8w9ZNbYLg7EXSQKBgQDedmymNoYo92vnFkaf+p7xNmFWs+24DhrXlln2Dd3GVN+XdoVi/HllV1yXMWZfjqZ1pzHNVa4e1ajbbJbOX1dds6p3fhiEVVTy70N2Fji8zoGjkNrhbz/HIuH2eV/Y0EihNusHNXEbLKz+deQQknOb10aax34ZaHs8W2TFc95aNwKBgQCnjUuwbI0Gb41EDCuV1QO+XFn46UpaZTDXqaxJROQzzHBwMAR3SQkcjFUjdfCtYLDaWDbhdg2cUXymJd3CQa/9ACnJd+ZAXawfDsd5YvFJQkQ6mk5/wM+93HwWpFCsYPtngtmCBV6hDNXce8eg1I2DOiIq5AOhKsp3wxJfOjdp+wKBgGObYsqJX0i4f/fYYYcAEGqwjqTjq2zd4+bjyyQA0WwcVIDc8JyBr1lQ5IRN+iiPFrlXQ1VNJ8lmoVSeKmf9CIFMEOev7gpSqtabivk0ufLQA/vYwChwR35CgCp77g6GM/77OADiTOJ9/ndxEczZxinhGhvzduKphYRg554BY23BAoGARGolZbxtORuDiy2M/nkkNi5vbnuTqKV49BaMCr7ej2gC4Uw6BpsD5WxHt1sJE0JP9Ffg0N7rX9DGHPGJQzCfM6dFaMZUdiPJjET0nlQQQeGVCnjHZNLOALBu2q/tYPWYsgNz3OtkYE5lZMoGEV25CsRJ/0lrUfuvAIfekJOs3NECgYBK539Zj1Ar4qryOykdPg2qjmN/TtV9hvODDi5DRGkEvKtV2zwraYybDo+snw/f2KnqSbBFVu3xPVIgaCFxiZMxMFWUMOIlrTPrOX6+O7XLv6FiTErcQWqtmG6xBqsr/zWD4WbTsNP/d/2kNVFMu13HJofCo8NxNyNnLRDHtg3exQ==";
    private static String SAPP_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCHvK3CeS3/g39W78oApxY+CNKvUH3in3FHEhC04IelgJLN10kxtBSCGErl+hl3SEYKvdEGQWsr2Qn/5xDYd+RRKk9NEGsEpZ3Cblr1j+CRE8H8V8ACVcjnyxcubsjE9d5FcSUGQMFtJLzbkp1mKebHtV45uZqao6QmiWgAImTtAyPMbr6130CUX8Z9R3ca3u87hP5euBM91irSxTtYRNU4lxgGtTMO+A5E04+lf3WThwvmjg6kCMoHFWAUmw32Xv2u97hbZgVUoLK13dWcVS59gEgohzpeKHLqwKppYsBnuWWnHFMEyPLtXhtar0cD56FWlKeMrfBcJGvp3tR9PYUZAgMBAAECggEAfFboTRc4fbF1eqvZvVNR2q22YYY4atpnqBTyXN65I3BuCkVF9VPi3XFnWtgJb+0yzDApQ7O1esssj9CY69KXwYoibpO1lOIpa6wvgnaz3fHdZ0zyxLDUgxDVQWGWZGZdn/sPUXUMOvDGV9UsfiOwFg0cLYDKERIKEosYS9c14K4D5v+RS+0jmKq0t/n9ZuIE8FIyh5Nr6hrAGBj2+92Gg7wvbZmgOo5Iw5v+hv6SMO6iZfRSaaL3wQHcKcLEBLm2KExj7Zj9RC57cTEljBDsRoXe3WTLT96jrGJ+t351Bc7k8rT12D38H0VztxpaG/qeYcR//zg10fXw9Gp7q7hJAQKBgQDBIVscnKlqMOjQVCyzQiFZLdwCzILL6nnRIqABkY60d1pxUTKWGGbEr0VCShA8CWNaNarGf24rd1FOUJuXDMbjAwTGixYnFxI1ImLix+hLFnMg46sYTvxJn9ZZtRDhP5aC2T03RfpGXNdzrURFZNeBfy6LHHlzW1lU5ccHz98gCQKBgQCz7GpxjEIG1vtgr+lageW2qAeV8kyHjBkX/3kXIUGgg9H4xWt+O/QGWJC5Q5nldZCuw1/hsuVojPqNkSN0qcJtaBgK55UU7cGpw35DUzvOKRDMNG1LLB5STUS4n8uUaXB5m1lcsXpeSNzh6FuxSA1XsWIyj/s5N8Tgk7lNqVFgkQKBgHoNI3BkjSg/+pHV8F3k+ABM3BKkvjAyYYPKdwYmv+HK38xnPSngB+pavdFTzwiyAii7a86AL9amVCrDBGEkQPisKrNyqLBRgz8zGP0qyuHRMTbiynjVSdkZezQ8GovPTMYqleY9gho/iCVhpZiZTxxWjMp7V0GQxFbD09FbDC5JAoGBALMFMIP6Qlf73/K8FTtws9eiKKoRhAP6aZ1vHFTfSiUkA++6vtHDZXJK/PTZRm8BIwxAaU5wacI1Q7dICjcyEbo6MHHpKnTAfEklsqPaVU8znPsRzw1UwHazWwU4OZ9ZqgfVgnywOklFxkEgqNskDeKLJGGa0SSFUOcDFQUoy0eBAoGAJ+QMwdEidQy2v4suD8ykhPaYMgh+J68gz/fuoFOA5kvB4oNzTXOCrun0VyEhdgDHS3KjTBGw8RgZvXTNYF/1MXJVo7SONzSvCqq1k/AdeJIbweJiSSqitbyTI4KNG1/4Jdal7scQ6ZKe6WgPWvSJIe40sJwTHQEunS2lJG6KUPI=";
    private static String CHARSET = "UTF-8";
    // 支付宝公钥
    private static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhaf+N2tqb+WnWQ5aAahujwYu2fAoXzhhxeHP1ZgV1sslwUeLjJb60/BYBUZQ8FWIwrJQ3i6xGhs+ls5l2Vu7oW2YckIAahHfuwXRKi+vXcSQun3TSrQNXSB8Lxq82LClIgwGwNrevezsMBIlXY+/byXpO9fGXpFHGzeNlaLtaIP3HMDUkudjA0nSoqcgcIa76CiCDgA3MIaclZgDSj/XSatlkmeMC5uRQSvuwMbBo7moKUMZdktW0mCEWxDYjlFZwJUUbKd/oPAr6A3BdRGRENmtQTWOB+l6lLqGexDGEeQNdh773z0llHGXQKp3610691lEfejzM5snqhGXg7xIAQIDAQAB";
    private static String SALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAj7QZI8O4d0Or5vj0hlpPy975Q8faVY3tbUsJqy1jI4M7RhBd3WkFSz40I8TOBkCaXK5d0ojl7DZTiKTOIAGwMcoRrLUSdeK3PNzLSmCMg2wyV+qtZ02LT5nSjDHQIhw2js5VRxRefllRwqDCskFBGFNasQlyaZzkRt4/MgUqp8IdpEcuvqdvbTvwcxZnGKctPzFGtE7t95CaeF70Ep3slxxIRTXjyP1ImQiCnLkZs056aMJD/zNApxj3pqS9ZSOhhbZ40klfVG5yyvBm7A8JShvyXogzOZWwpZconJGVee0Dj0sF9xWNylFeBNsIrrAJb48bn4IGwUKARCegGOYK7wIDAQAB";
    // 网管
    private static String serverUrl = "https://openapi.alipay.com/gateway.do";
    private static String SserverUrl = "https://openapi.alipaydev.com/gateway.do";
    // 支付宝商家账号id(UID)[
    private static String seller_id = "2088102179867841";

    @ResponseBody
    @RequestMapping("ResultVerification")
    public BaseResultEntity ResultVerification(@RequestParam("response") String response){
        if(StringUtils.isBlank(response)) {
            return BaseResultEntity.fail("response不能为空");
        }
        JSONObject object  = JSON.parseObject(response);
        String alipay_trade_app_pay_response = object.getString("alipay_trade_app_pay_response");
        String sign_type = object.getString("sign_type");
        String sign = object.getString("sign");
        // 4.验证签名是否合法：
        // 使用各自语言对应的 SHA256WithRSA 签名验证函数，
        // 传入签名的原始字符串、支付宝公钥、签名类型 RSA、
        // 签名字符进行合法性验证。

        /**
         * 商户需要验证该通知数据中的 out_trade_no 是否为商户系统中创建的订单号；
         * 判断 total_amount 是否确实为该订单的实际金额（即商户订单创建时的金额）；
         * 校验通知中的 seller_id（或者 seller_email) 是否为 out_trade_no 这笔单据
             对应的操作方（有的时候，一个商户可能有多个 seller_id/seller_email）；
         * 验证 app_id 是否为该商户本身。上述1、2、3、4有任何一个验证不通过，
         * 则表明同步校验结果是无效的，只有全部验证通过后，才可以认定买家付款成功。
         */

        return  null;
//        return payService.ResultVerification(appId, outTradeNo, totalAmount, sellerId);
    }

    /**
     * 订单交易查询
     * @param orderNo
     * @return
     */
    @ResponseBody
    @RequestMapping("queryPayInfo")
    public BaseResultEntity queryPayInfo(@RequestParam("orderNo") String orderNo,
                                         @RequestParam("tradeNo") String tradeNo) {
        String data = "";
        try{
           data =  AliPayUtils.getInstance().getApliPayTradeNo(orderNo,tradeNo);
        }catch(AlipayApiException e){
            data = "error";
            e.printStackTrace();
        }
        return ResultEntity.success("查询成功",data);
    }
    @RequestMapping("testPay")
    @ResponseBody
    public BaseResultEntity testPay(){
        //实例化客户端
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipaydev.com/gateway.do", SAPP_ID, SAPP_PRIVATE_KEY, "json", CHARSET, SALIPAY_PUBLIC_KEY, "RSA2");
//实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
//SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody("我是测试数据");
        model.setSubject("App支付测试Java");
        String outtradeno = new Date().getTime()+""+Math.random()*10;
        model.setOutTradeNo(outtradeno);
        model.setTimeoutExpress("30m");
        model.setTotalAmount("0.01");
        model.setProductCode("QUICK_MSECURITY_PAY");
        request.setBizModel(model);
        request.setNotifyUrl("http://client2.365hy.com/liuzhi/pay/zfbResult");
        try {
            //这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            String info = response.getBody();
//            System.out.println(response.getBody());
            //就是orderString 可以直接给客户端请求，无需再做处理。
            return ResultEntity.success("ok",info);
        } catch (AlipayApiException e) {

            e.printStackTrace();
        }
        return null;
    }

    public static String mapsStr(Map<String,String> map){
        String u = "";
        for(String s:map.keySet()){
            u += s+"="+map.get(s)+"&";
        }
        u = u.substring(0,u.length()-1);
        return u;
    }

    /**
     * 返回最终支付结果
     * @param orderNo
     * @return
     */
    @ResponseBody
    @RequestMapping("payStatus")
    public BaseResultEntity payStatus(String orderNo){
        if(StringUtils.isBlank(orderNo)) {
            return BaseResultEntity.fail("orderNo不能为空");
        }
        return ResultEntity.success(payService.payStatus(orderNo));
    }
    @ResponseBody
    @RequestMapping("test")
    public String Test(String all){
        String[] s =  all.split(",");
        return payService.ResultVerification(s[0],s[1],s[2],s[3],"");
    }

    /**
     * 未付款订单结算再次付款
     * 把原先的订单删除组成一个新的订单
     * @param userId
     * @param total
     * @param  info 每个订单里商品的订单id数量金额 {12-1-22,23-3-33}
     * @return
     */
    @RequestMapping("aliPayAgain")
    @ResponseBody
    public BaseResultEntity aliPayAgain( @RequestAttribute Long userId,
                                         @RequestParam("total") String total, @RequestParam("info") String info, @RequestParam("subject")String subject,
                                         @RequestParam("number") String number, String buyerMessage,@RequestParam("addressId") String addressId,
                                         @RequestParam("expressId") String expressId, @RequestParam("price") String price,
                                         @RequestParam("expressPrice") String expressPrice, @RequestParam("discount") String discount
                                         ) {
        if (StringUtils.isBlank(total)) {
            return BaseResultEntity.fail("total不能为空");
        }
        if (StringUtils.isBlank(info)) {
            return BaseResultEntity.fail("info为空");
        }
        if(StringUtils.isBlank(subject)) {
            return BaseResultEntity.fail("subject为空");
        }
        if(StringUtils.isBlank(number)) {
            return BaseResultEntity.fail("number为空");
        }
        if(StringUtils.isBlank(buyerMessage)) {
            buyerMessage = "";
        }
        if(StringUtils.isBlank(expressId)) {
            return BaseResultEntity.fail("expressId为空");
        }
        if(StringUtils.isBlank(price)) {
            return BaseResultEntity.fail("price为空");
        }
        if(StringUtils.isBlank(expressPrice)) {
            return BaseResultEntity.fail("expressPrice为空");
        }
        if(StringUtils.isBlank(discount)){
            return BaseResultEntity.fail("discount为空");
        }
        if(StringUtils.isBlank(addressId)) {
            return BaseResultEntity.fail("addressId为空");
        }
        // 创建订单，添加订单信息
        Order order = new Order();
        // 金额数量*100
        BigDecimal multiply = new BigDecimal(100);
        BigDecimal bprice  = new BigDecimal(price);
        BigDecimal bexpressPrice  = new BigDecimal(expressPrice);
        BigDecimal bpayPrice  = new BigDecimal(total);
        BigDecimal bfinalPrice  = new BigDecimal(total);
        BigDecimal bdiscount  = new BigDecimal(discount);
         // 订单添加项
        order.setBuyerMessage(buyerMessage);
        order.setAddressId(Long.parseLong(addressId));
        order.setPrice(bprice.multiply(multiply));
        order.setUserId(userId);
        order.setExpressPrice(bexpressPrice.multiply(multiply));
        order.setPayPrice(bpayPrice.multiply(multiply));
        order.setFinalPrice(bfinalPrice.multiply(multiply)); //最终支付价格
        order.setSubject(subject);
        order.setNumber(Integer.parseInt(number));
        order.setDiscount(bdiscount.multiply(multiply));
        order.setExpressId(Long.parseLong(expressId));

        return payService.aliPayAgain(order, info, total);
    }

    /**
     * 添加购物车，生成为付款订单
     * @param userId
     * @param number
     * @param price
     * @param orderItemId
     * @return
     */
    @RequestMapping("addCart")
    @ResponseBody
    public BaseResultEntity addCart(@RequestAttribute long userId, @RequestParam("number") String number, @RequestParam("price") String price, @RequestParam("orderItemId") String orderItemId,
            @RequestParam("subject") String subject) {

        return payService.addCart(userId, Integer.parseInt(number), price, Long.parseLong(orderItemId), subject);
    }
}
