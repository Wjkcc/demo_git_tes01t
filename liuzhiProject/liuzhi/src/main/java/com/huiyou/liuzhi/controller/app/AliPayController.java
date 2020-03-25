package com.huiyou.liuzhi.controller.app;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.huiyou.liuzhi.alipay.AliPayTool;
import com.huiyou.liuzhi.mapper.OrderCopyMapper;
import com.huiyou.liuzhi.model.OrderCopy;
import com.huiyou.liuzhi.service.AliPayService;
import com.huiyou.liuzhi.utils.AliPayUtils;
import me.fishlord.common.result.BaseResultEntity;
import me.fishlord.common.result.ResultEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.huiyou.liuzhi.controller.app.AppPayController.mapsStr;

/**
 * @author Joe
 * @version 1.0
 * @date 2020/2/26 16:44
 */

@Controller
@RequestMapping("aliPay")
public class AliPayController {
    @Autowired
    private AliPayService aliPayService;
    @Autowired
    private AliPayTool aliPayTool;
    @Autowired
    private OrderCopyMapper orderCopyMapper;
    /**
     * 支付宝付款,获取加签信息
     * @param orderNum
     * @return
     */
    @ResponseBody
    @RequestMapping("getSign")
    public BaseResultEntity getSign(@RequestAttribute long userId, @RequestParam("orderNum") String orderNum) {

        if(StringUtils.isBlank(orderNum)) {
            return ResultEntity.fail("订单号为空");
        }
        return aliPayService.getSign(orderNum);
    }

    /**
     * 支付宝异步回调验证
     * 正式
     * @param request
     * @param httpResponse
     * @return
     * @throws Exception
     */
    @RequestMapping("aliPayCallback")
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



        boolean flag = AlipaySignature.rsaCheckV1(params, aliPayTool.getALIPAY_PUBLIC_KEY(), "UTF-8","RSA2");
        if(flag){
            // 支付宝订单显示交易成功
            if(params.get("trade_status").equals("TRADE_SUCCESS")) {
//

                return  aliPayService.callBack(params.get("app_id"), params.get("out_trade_no"),
                        params.get("total_amount"), params.get("seller_id"), params.get("trade_no"));


            }
        }
        return "fail";

    }

    /**
     * 通过订单号查询支付宝订单
     * @param orderNo
     * @param tradeNo
     * @return
     */
    @ResponseBody
    @RequestMapping("queryPayInfo")
    public String queryPayInfo(@RequestParam("orderNo") String orderNo,
                                         @RequestParam("tradeNo") String tradeNo) {
        String data = "error";
        try{
            data = aliPayTool.getAliOrder(orderNo, tradeNo);
        }catch(AlipayApiException e){
            e.printStackTrace();
        }
        return data;
    }

    /**
     * 返回最终支付结果
     * @param orderNo
     * @return
     */
    @ResponseBody
    @RequestMapping("payResult")
    public BaseResultEntity payResult(String orderNo){
        if(StringUtils.isBlank(orderNo)) {
            return BaseResultEntity.fail("orderNo不能为空");
        }
        return aliPayService.payResult(orderNo);
    }
    /**
     * payTest
     * @return
     */
    @ResponseBody
    @RequestMapping("payTest")
    public BaseResultEntity payTest(String orderNum) {
        String data= aliPayTool.test(orderNum);
        return ResultEntity.success("ok",data);

    }
    /**
     * 订单结算
     * orders 订单id 逗号隔开
     * info 订单商品项信息 id-num，id-num
     * @return
     */
    @ResponseBody
    @RequestMapping("settlement")
    public BaseResultEntity settlement(@RequestParam("orders") String orders, @RequestParam("info") String info, @RequestAttribute long userId) {
        if (StringUtils.isBlank(info)) {
            return ResultEntity.fail("info不能为空");
        }
        if (StringUtils.isBlank(orders)) {
            return ResultEntity.fail("orders不能为空");
        }
        return aliPayService.settlement(orders, info);
    }
}
