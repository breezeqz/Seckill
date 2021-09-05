package com.site.seckill.redis;

public abstract class BasePrefix implements KeyPrefix{


	private String prefix;

	public BasePrefix( String prefix) {
		this.prefix = prefix;
	}

	public String getPrefix() {
		//getClass()获取运行时的类，getSimpleName（）获取底层类名
		String className = getClass().getSimpleName();
		return className+":" + prefix;
	}

}
