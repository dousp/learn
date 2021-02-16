package com.dou.learn.rocketmq.producer.listener;

import com.alibaba.fastjson.JSONObject;
import com.dou.learn.rocketmq.producer.service.TxService;
import lombok.extern.slf4j.Slf4j;

import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
@RocketMQTransactionListener(rocketMQTemplateBeanName = "txRocketMQTemplate")
public class ProducerTxMsgListener implements RocketMQLocalTransactionListener {

    @Resource
    TxService txService;

    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        log.info("local tx......");
        //解析消息内容
        try {
            String jsonString = new String((byte[]) msg.getPayload());
            JSONObject jsonObject = JSONObject.parseObject(jsonString);
            log.info("local 事务编号：{}",jsonObject.getString("txNo"));
            txService.doUpdate(jsonObject.getLong("money"));
            log.info("local 事务执行成功，事务编号：{}",jsonObject.getString("txNo"));
            return RocketMQLocalTransactionState.COMMIT;
        } catch (Exception e) {
            log.error("local 事务执行失败",e);
            e.printStackTrace();
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }


    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        log.info("local check......");
        String jsonString = new String((byte[]) msg.getPayload());
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        log.info("local check data：{}",jsonObject.toJSONString());
        return RocketMQLocalTransactionState.COMMIT;
    }
}
