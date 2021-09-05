package com.site.seckill.mq;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {
	//直连模式
	public static final String Direct_EXCHANGE = "directExchange";
	public static final String Direct_Queue = "directQueue";
	public static final String Direct_Routing = "directRouting";

	public static final String TOPIC_EXCHANGE = "topicExchange";
	public static final String TOPIC_QUEUE1 = "topic.queue1";
	public static final String TOPIC_QUEUE2 = "topic.queue2";
	public static final String TOPIC_Routing = "topic.#";
//	public static final String MIAOSHA_QUEUE = "seckill.queue";
//	public static final String QUEUE = "queue";

//	public static final String TOPIC_QUEUE1 = "topic.queue1";
//	public static final String TOPIC_QUEUE2 = "topic.queue2";
//	public static final String HEADER_QUEUE = "header.queue";

//	public static final String TOPIC_EXCHANGE = "topicExchange";
//	public static final String FANOUT_EXCHANGE = "fanoutExchage";
//	public static final String HEADERS_EXCHANGE = "headersExchange";

	//相当于xml中的如下配置，把类注入到容器中，由容器来创建对象
	//<beans>
    //<bean id="getMessageConverter" class="org.springframework.amqp.support.converter.MessageConvert"/>
	//</beans>
	@Bean
	public MessageConverter getMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	/**
	 * Direct模式 交换机Exchange(直连型交换机)
	 **/
//	//队列
//	@Bean
//	public Queue directQueue() {
//		return new Queue(Direct_Queue, true);
//	}
//	//交换机
//	@Bean
//	public DirectExchange directExchange(){
//		//使用提供的名称构造一个新的持久的、非自动删除的 Exchange
//		return new DirectExchange(Direct_EXCHANGE);
//	}
//	//绑定
//	@Bean
//	Binding bindingDirect() {
//		return BindingBuilder.bind(directQueue()).to(directExchange()).with("directRouting");
//	}

	/**
	 * Topic模式 交换机Exchange(主题交换机，可以用做直连交换机和扇形交换机)
	 * */
	@Bean
	public Queue topicQueue1() {
		return new Queue(TOPIC_QUEUE1, true);
	}
	@Bean
	public Queue topicQueue2() {
		return new Queue(TOPIC_QUEUE2, true);
	}
	@Bean
	public TopicExchange topicExchage(){
		return new TopicExchange(TOPIC_EXCHANGE,true,false);
	}
	@Bean
	public Binding topicBinding1() {
		return BindingBuilder.bind(topicQueue1()).to(topicExchage()).with(MQConfig.TOPIC_Routing);
	}
	@Bean
	public Binding topicBinding2() {
		return BindingBuilder.bind(topicQueue2()).to(topicExchage()).with(MQConfig.TOPIC_Routing);
	}


	/**
	 * Fanout模式 交换机Exchange(扇形交换机)
	 * */
//	@Bean
//	public FanoutExchange fanoutExchage(){
//		return new FanoutExchange(FANOUT_EXCHANGE);
//	}
//	@Bean
//	public Binding FanoutBinding1() {
//		return BindingBuilder.bind(topicQueue1()).to(fanoutExchage());
//	}
//	@Bean
//	public Binding FanoutBinding2() {
//		return BindingBuilder.bind(topicQueue2()).to(fanoutExchage());
//	}

	/**
	 * Header模式 交换机Exchange(头交换机)
	 * */
//	@Bean
//	public HeadersExchange headersExchage(){
//		return new HeadersExchange(HEADERS_EXCHANGE);
//	}
//	@Bean
//	public Queue headerQueue1() {
//		return new Queue(HEADER_QUEUE, true);
//	}
//	@Bean
//	public Binding headerBinding() {
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("header1", "value1");
//		map.put("header2", "value2");
//		return BindingBuilder.bind(headerQueue1()).to(headersExchage()).whereAll(map).match();
//	}
	
}
