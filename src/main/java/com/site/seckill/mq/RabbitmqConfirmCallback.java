package com.site.seckill.mq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
@Component
public class RabbitmqConfirmCallback implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {
    private Logger logger = LoggerFactory.getLogger(RabbitmqConfirmCallback.class);
    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        if(b){
            logger.info("消息投递成功，消息Id：{}",correlationData.getId());
        }else{
            //消息没有发送到交换机
            //将消息存放在本地消息表，使用定时任务重新发送
            //1 在本地缓存已发送的message
            //2 通过confirmCallback或者被确认的ack，将被确认的message从本地删除
            //3 定时扫描本地的message，如果大于一定时间未被确认，则重发
            logger.error("消息投递失败，Id:{},错误提示：{}",correlationData.getId(),s);
        }
    }

    @Override
    public void returnedMessage(Message message, int i, String s, String s1, String s2) {
        logger.info("消息没有路由到消息队列，获得返回消息");
        SeckillMessage seckillMessage = byteToObject(message.getBody(),SeckillMessage.class);
        logger.info("message body: {}", seckillMessage == null ? "" : seckillMessage.toString());
        logger.info("replyCode: {}",i);
        logger.info("replyText: {}", s);
        logger.info("exchange: {}", s1);
        logger.info("routingKey: {}", s2);
        logger.info("------------> end <------------");
    }
    @SuppressWarnings("unchecked")
    private <T> T byteToObject(byte[] bytes, Class<T> clazz) {
        T t;
        try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
             ObjectInputStream ois = new ObjectInputStream(bis)) {
            t = (T) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return t;
    }
}
