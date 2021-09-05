package com.site.seckill.mq;

import com.rabbitmq.client.Channel;
import com.site.seckill.bo.GoodsBo;
import com.site.seckill.entity.SeckillOrder;
import com.site.seckill.entity.User;
import com.site.seckill.redis.RedisService;
import com.site.seckill.service.OrderService;
import com.site.seckill.service.SeckillGoodsService;
import com.site.seckill.service.SeckillOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MQReceiver {

		private static Logger log = LoggerFactory.getLogger(MQReceiver.class);
		
		@Autowired
		RedisService redisService;
		
		@Autowired
        SeckillGoodsService goodsService;
		
		@Autowired
        OrderService orderService;
		
		@Autowired
        SeckillOrderService seckillOrderService;
		@RabbitHandler
		@RabbitListener(queues={MQConfig.TOPIC_QUEUE1})
		public void receive(String message, Message messages, Channel channel) {
			try{
				log.info("receive message:"+message);
				SeckillMessage mm  = RedisService.stringToBean(message, SeckillMessage.class);
				User user = mm.getUser();
				long goodsId = mm.getGoodsId();
				GoodsBo goods = goodsService.getseckillGoodsBoByGoodsId(goodsId);
				int stock = goods.getStockCount();
				if(stock <= 0) {
					return;
				}
				//判断是否已经秒杀到了
				SeckillOrder order = seckillOrderService.getSeckillOrderByUserIdGoodsId(user.getId(), goodsId);
				if(order != null) {
					return;
				}
				//减库存 下订单 写入秒杀订单
				seckillOrderService.insert(user, goods);
				//确认收到消息，false只确认当前consumer一个消息收到，true确认所有consumer获得的消息
				channel.basicAck(messages.getMessageProperties().getDeliveryTag(), false);
				}catch(Exception e){
					e.printStackTrace();
					try {
						if (messages.getMessageProperties().getRedelivered()) {
							System.out.println("================消息已重复处理失败,拒绝再次接收======================" + message);
							// 拒绝消息，requeue=false 表示不再重新入队，如果配置了死信队列则进入死信队列
							channel.basicReject(messages.getMessageProperties().getDeliveryTag(), false);
						} else {
							System.out.println("====================消息即将再次返回队列处理=========================" + message);
							// requeue为是否重新回到队列，true重新入队
							channel.basicNack(messages.getMessageProperties().getDeliveryTag(), false, true);
						}
					} catch (IOException ex) {
						ex.printStackTrace();
					}

				}

		}
}
