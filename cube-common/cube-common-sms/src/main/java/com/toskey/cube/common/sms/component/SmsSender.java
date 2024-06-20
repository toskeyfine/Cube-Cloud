package com.toskey.cube.common.sms.component;

import com.toskey.cube.common.sms.model.MessageResult;

/**
 * 短信发送接口
 *
 * @author toskey
 * @version 1.0.0
 */
public interface SmsSender {

    /**
     * 单发短信
     *
     * @description 向指定手机号发送短信
     * @param templateCode 短信模版编号
     * @param content 短信内容
     * @param mobileNumber 接收端手机号
     * @return MessageResult
     */
    MessageResult sendTo(String templateCode, String content, String mobileNumber);

    /**
     * 群发短信
     *
     * @description 向指定手机号群发短信
     * @param templateCode 短信模版编号
     * @param content 短信内容
     * @param mobileNumbers 接收端手机号集合
     * @return MessageResult
     */
    MessageResult broadcast(String templateCode, String content, String... mobileNumbers);

    /**
     * 发送登录验证码短信
     *
     * @description 向指定手机号发送登录验证码短信
     * @param code 验证码
     * @param mobileNumber 接收端手机号
     * @return MessageResult
     */
    MessageResult sendLoginCode(String code, String mobileNumber);

    /**
     * 发送注册验证码短信
     *
     * @description 向指定手机号发送注册验证码短信
     * @param code 验证码
     * @param mobileNumber 接收端手机号
     * @return MessageResult
     */
    MessageResult sendRegCode(String code, String mobileNumber);

}
