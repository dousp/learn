package com.dou.learn.rocketmq.producer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.LongAdder;

@Service
@Slf4j
public class TxService {

    private final LongAdder sum = new LongAdder();

    public void doUpdate(long money) throws InterruptedException {
        log.info("本地账户金额扣减....");
        if(money>3){
            sum.add(money);
            log.info("本地账户金额扣减成功....");
        }else{
            Thread.sleep(3*1000);
            // 当业务执行时间与mq回查周期相近的时候：
            // 回查返回commit，但是业务恰巧在这一瞬间报错
            // throw new RuntimeException("本地账户金额扣减异常....");
        }
    }
}
