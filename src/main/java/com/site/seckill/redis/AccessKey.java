package com.site.seckill.redis;
//用于限流
public class AccessKey extends BasePrefix{

	private AccessKey( String prefix) {
		super(prefix);
	}
	
	public static AccessKey withExpire = new AccessKey("access");

}
