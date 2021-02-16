package com.dou.learn.rocketmq.producer.rest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dou.learn.rocketmq.producer.conf.TxRocketMQTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.security.SecureRandom;

@Slf4j
@RestController
@RequestMapping("/rocketmq")
public class SendMsgController {

    private final SecureRandom random = new SecureRandom();

    @Resource
    private RocketMQTemplate rocketMQTemplate;
    @Resource
    private TxRocketMQTemplate txRocketMQTemplate;

    @RequestMapping(value = "/sync", method = RequestMethod.GET)
    public String sync(){
        JSONObject json = new JSONObject();
        json.put("name","tt");
        json.put("addr","sh");
        json.put("txNo",System.currentTimeMillis()+"");
        json.put("money",6);
        Message<String> message = MessageBuilder.withPayload(json.toJSONString()).build();
        SendResult result = rocketMQTemplate.syncSend("TEST-TX", message);
        return result.getSendStatus().toString();
    }

    @RequestMapping(value = "/tx", method = RequestMethod.GET)
    public String tx(){
        JSONObject json = new JSONObject();
        json.put("name","dd");
        json.put("addr","bj");
        json.put("txNo",System.currentTimeMillis()+"");
        // json.put("money",random.nextInt(6));
        json.put("money",3);
        Message<String> message = MessageBuilder.withPayload(json.toJSONString()).build();
        TransactionSendResult result = txRocketMQTemplate.sendMessageInTransaction("TEST-TX",message,null);
        log.info("result:{}", JSON.toJSONString(result));
        return result.getSendStatus().toString();
    }


}
