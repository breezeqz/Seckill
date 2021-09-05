package com.site.seckill;

public class Singleton {
    private static class SingletonHolder{
        private static Singleton singleton = new Singleton();
    }
    private Singleton(){}
    public static Singleton getInstance(){
        return SingletonHolder.singleton;
    }
}
