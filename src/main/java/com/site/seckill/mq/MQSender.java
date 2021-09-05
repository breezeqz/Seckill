package com.site.seckill.mq;

import com.site.seckill.redis.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.text.resources.CollationData;

import javax.annotation.PostConstruct;
import java.util.UUID;


@Service
public class MQSender{

	private static Logger log = LoggerFactory.getLogger(MQSender.class);
	
	@Autowired
	RabbitTemplate rabbitTemplate ;
	@Autowired
	RabbitmqConfirmCallback rabbitmqConfirmCallback;
	@PostConstruct
	public void init() {
		//指定 ConfirmCallback
		rabbitTemplate.setConfirmCallback(rabbitmqConfirmCallback);
		//指定 ReturnCallback
		rabbitTemplate.setReturnCallback(rabbitmqConfirmCallback);
	}
	public void sendSeckillMessage(SeckillMessage mm) {
		String msgId = UUID.randomUUID().toString().replace("-", "").substring(0, 32);
		CorrelationData correlationData = new CorrelationData(msgId);
		String msg = RedisService.beanToString(mm);
		log.info("send message:"+msg);
		rabbitTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE, MQConfig.TOPIC_Routing,msg,correlationData);
	}

}
