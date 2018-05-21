package com.tcs.springboot.redistest;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

public class App {
	public static final String REDIS_HOST = "app2900.gha.kfplc.com";
	public static final int REDIS_PORT = 6379;
	public static final String REDIS_PASSWORD = "sumangal";

	public static void setValue(String key, String value, Jedis redis) {
		redis.set(key, value);
	}

	public static String getValue(String key, Jedis redis) {
		return redis.get("my-key");
	}

	public static Jedis getCache(JedisPool redisPool) {
		return redisPool.getResource();
	}

	public static void closeCachePool(JedisPool redisPool) {
		redisPool.close();
	}

	public static JedisPool getCachePool() {
		return (new JedisPool(new JedisPoolConfig(), REDIS_HOST, REDIS_PORT,
				Protocol.DEFAULT_TIMEOUT, REDIS_PASSWORD));
	}

	public static void main(String[] args) throws Exception {
		JedisPool redisPool = App.getCachePool();
		Jedis redis = App.getCache(redisPool);
		App.setValue("my-key", "my-value", redis);
		System.out.println(App.getValue("my-key", redis));
		App.closeCachePool(redisPool);
	}
}