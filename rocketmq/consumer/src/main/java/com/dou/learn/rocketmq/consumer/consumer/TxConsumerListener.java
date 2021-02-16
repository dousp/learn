package com.dou.learn.rocketmq.consumer.consumer;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RocketMQMessageListener(topic = "TEST-TX", selectorExpression = "*", consumerGroup = "tx-consumer")
public class TxConsumerListener implements RocketMQListener<String>  {

    public void onMessage(String message) {
        JSONObject jsonObject = JSONObject.parseObject(message);
        log.info("data {}",jsonObject.toJSONString());
        // throw new RuntimeException("无法消费......");
    }
}
