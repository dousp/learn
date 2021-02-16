package com.dou.learn.rocketmq.producer.conf;

import org.apache.rocketmq.spring.annotation.ExtRocketMQTemplateConfiguration;
import org.apache.rocketmq.spring.core.RocketMQTemplate;

@ExtRocketMQTemplateConfiguration(group = "txRocketMQTemplate")
public class TxRocketMQTemplate extends RocketMQTemplate {
}
