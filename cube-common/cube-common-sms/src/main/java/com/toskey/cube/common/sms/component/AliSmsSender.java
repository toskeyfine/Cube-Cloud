package com.toskey.cube.common.sms.component;

import com.aliyun.sdk.service.dysmsapi20170525.AsyncClient;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendBatchSmsRequest;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendBatchSmsResponse;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsResponse;
import com.google.gson.Gson;
import com.toskey.cube.common.sms.model.MessageResult;
import com.toskey.cube.common.sms.properties.SmsProperties;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.util.concurrent.CompletableFuture;

/**
 * AliSmsSender
 *
 * @author toskey
 * @version 1.0.0
 */
@AllArgsConstructor
public class AliSmsSender implements SmsSender {

    private final SmsProperties smsProperties;

    private final AsyncClient aliSmsClient;

    @SneakyThrows
    @Override
    public MessageResult sendTo(String templateCode, String content, String mobileNumber) {
        SendSmsRequest sendSmsRequest = SendSmsRequest.builder()
                .signName(smsProperties.getAli().getSignName())
                .templateCode(templateCode)
                .phoneNumbers(mobileNumber)
                .templateParam(content)
                .build();
        CompletableFuture<SendSmsResponse> response = aliSmsClient.sendSms(sendSmsRequest);
        return MessageResult.of(response.get().getBody().getCode());
    }

    @SneakyThrows
    @Override
    public MessageResult broadcast(String templateCode, String content, String... mobileNumbers) {
        SendBatchSmsRequest sendSmsRequest = SendBatchSmsRequest.builder()
                .signNameJson(new Gson().toJson(smsProperties.getAli().getSignName()))
                .templateCode(templateCode)
                .phoneNumberJson(new Gson().toJson(mobileNumbers))
                .templateParamJson(String.format("[%s]", content))
                .build();

        CompletableFuture<SendBatchSmsResponse> response = aliSmsClient.sendBatchSms(sendSmsRequest);
        return MessageResult.of(response.get().getBody().getCode());
    }

    @Override
    public MessageResult sendLoginCode(String code, String mobileNumber) {
        return sendTo(
                smsProperties.getAli().getLoginTemplateCode(),
                String.format("{\"code\":\"%s\"}", code),
                mobileNumber
        );
    }

    @Override
    public MessageResult sendRegCode(String code, String mobileNumber) {
        return sendTo(
                smsProperties.getAli().getRegTemplateCode(),
                String.format("{\"code\":\"%s\"}", code),
                mobileNumber
        );
    }
}
