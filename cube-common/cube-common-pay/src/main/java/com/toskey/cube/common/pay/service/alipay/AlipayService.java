package com.toskey.cube.common.pay.service.alipay;

import com.toskey.cube.common.pay.entity.PayTrade;
import com.toskey.cube.common.pay.entity.PrepayResult;

import java.util.Map;

/**
 * ZxAlipayService
 *
 * @author toskey
 * @version 1.0.0
 */
public interface AlipayService {

    PrepayResult<Map<String, String>> pay(PayTrade payTrade);

}
