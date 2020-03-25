package com.huiyou.liuzhi.service;

import me.fishlord.common.result.BaseResultEntity;

/**
 * @author Joe
 * @version 1.0
 * @date 2020/2/26 16:41
 */
public interface AliPayService {

    BaseResultEntity getSign(String OrderNo);

    String callBack(String appId, String orderNo, String totalAmount, String sellerId, String tradeNo);

    BaseResultEntity payResult(String orderNo);

    BaseResultEntity settlement(String orders, String info);
}
