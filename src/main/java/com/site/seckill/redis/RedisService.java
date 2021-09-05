package com.site.seckill.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class RedisService {
	
	@Autowired
	JedisPool jedisPool;
	
	//获取java对象
	public <T> T get(KeyPrefix prefix, String key,  Class<T> clazz) {
		 Jedis jedis = null;
		 try {
			 jedis =  jedisPool.getResource();//获取jedis连接
			 //生成真正的key
			 String realKey  = prefix.getPrefix() + key;
			 String  str = jedis.get(realKey);
			 T t =  stringToBean(str, clazz);//将字符串转换成json对象再转换成java对象如json字符串‘{GoodsKey:gl}’
			 return t;
		 }finally {
			  returnToPool(jedis);//归还连接
		 }
	}
	//重新设置键的超时时间
	public  Long expice(KeyPrefix prefix,String key,int exTime){
		Jedis jedis = null;
		Long result = null;
		try {
			jedis =  jedisPool.getResource();
			result = jedis.expire(prefix.getPrefix()+key,exTime);//设置键的过期时间，设置成功返回1，key不存在或者key已经设置了超时时间返回0.
			return result;
		} finally {
			returnToPool(jedis);
		}
	}
	
	//设置键值对的超时时间
	public <T> boolean set(KeyPrefix prefix, String key,  T value ,int exTime) {
		 Jedis jedis = null;
		 try {
			 jedis =  jedisPool.getResource();
			 String str = beanToString(value);//java对象转换成json字符串
			 if(str == null || str.length() <= 0) {
				 return false;
			 }
			//生成真正的key
			 String realKey  = prefix.getPrefix() + key;
			 if(exTime == 0) {
			 	 //直接保存
				 jedis.set(realKey, str);
			 }else {
			 	 //设置键值对的过期时间
				 jedis.setex(realKey, exTime, str);
			 }
			 return true;
		 }finally {
			  returnToPool(jedis);
		 }
	}
	//删除键
	public  Long del(KeyPrefix prefix,String key){
		Jedis jedis = null;
		Long result = null;
		try {
			jedis =  jedisPool.getResource();
			result = jedis.del(prefix.getPrefix()+key);
			return result;
		} finally {
			returnToPool(jedis);
		}
	}
	//判断key是否存在
	public <T> boolean exists(KeyPrefix prefix, String key) {
		 Jedis jedis = null;
		 try {
			 jedis =  jedisPool.getResource();
			//生成真正的key
			 String realKey  = prefix.getPrefix() + key;
			return  jedis.exists(realKey);
		 }finally {
			  returnToPool(jedis);
		 }
	}
	
	//键的值加1
	public <T> Long incr(KeyPrefix prefix, String key) {
		 Jedis jedis = null;
		 try {
			 jedis =  jedisPool.getResource();
			//生成真正的key
			 String realKey  = prefix.getPrefix() + key;
			 //将key中储存的数字值增一。
			 //如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCR 操作。
			 //如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误。
			 //本操作的值限制在 64 位(bit)有符号数字表示之内
			return  jedis.incr(realKey);
		 }finally {
			  returnToPool(jedis);
		 }
	}
	
	//键的值减少1
	public <T> Long decr(KeyPrefix prefix, String key) {
		 Jedis jedis = null;
		 try {
			 jedis =  jedisPool.getResource();
			//生成真正的key
			 String realKey  = prefix.getPrefix() + key;
			return  jedis.decr(realKey);
		 }finally {
			  returnToPool(jedis);
		 }
	}

	//java对象转换成json字符串
	public static <T> String beanToString(T value) {
		if(value == null) {
			return null;
		}
		Class<?> clazz = value.getClass();
		if(clazz == int.class || clazz == Integer.class) {
			 return ""+value;
		}else if(clazz == String.class) {
			 return (String)value;
		}else if(clazz == long.class || clazz == Long.class) {
			return ""+value;
		}else {
			return JSON.toJSONString(value);//将java对象转换成json字符串
		}
	}


	//json字符串转成java对象
	public static <T> T stringToBean(String str, Class<T> clazz) {
		if(str == null || str.length() <= 0 || clazz == null) {
			 return null;//空
		}
		if(clazz == int.class || clazz == Integer.class) {
			 return (T)Integer.valueOf(str);//int,Integer
		}else if(clazz == String.class) {
			 return (T)str;//String
		}else if(clazz == long.class || clazz == Long.class) {
			return  (T)Long.valueOf(str);//long,Long
		}else {
			//JSON.parseObject()方法将json字符串装换成json对象，JSON.toJavaObject()将接送对象装换成java对象
			return JSON.toJavaObject(JSON.parseObject(str), clazz);
		}
	}
	//归还连接到连接池中
	private void returnToPool(Jedis jedis) {
		 if(jedis != null) {
			 jedis.close();
		 }
	}

}
