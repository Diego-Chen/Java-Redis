package com.cctv.http;

import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Transaction;



public class JedisConnect {
public static void main(String[] args) {
		
		tsac();

	}


	//事务操作
	public static void tsac(){
		
		
		try {
			Jedis jedis = new Jedis();
			//标记事务开始
			Transaction tran = jedis.multi();
			
			String key1 = "t1";
			String key2 = "t2";
			String key3 = "t3";
			
			
			
			tran.watch(key1,key2,key3);
			tran.set(key1, "12");
			tran.set(key2, "23");
			//jedis.set(key1, "qqqq");
			//取消事务 放弃事务中已有的命令 并且取消事务标记（消除multi()效果）
			tran.discard();
			tran = jedis.multi();
			tran.set(key3, "33");
			
			
			//所有命令的返回值
			List<Object> os = tran.exec();
			for (Object object : os) {
				System.err.println(object);
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	/**
	 * 连接池测试
	 */
	public static void jedisPoolTest(){
		
		JedisPoolConfig poocfg = new JedisPoolConfig();
		//最大空闲数
		poocfg.setMaxIdle(50);
		//最大连接数
		poocfg.setMaxTotal(100);
		//最大等待毫秒数
		poocfg.setMaxWaitMillis(20000);
		//使用配置创建连接池
		JedisPool pool = new JedisPool(poocfg,"localhost");
		//从连接池中获取单个连接
		Jedis jedis = pool.getResource();
		//如果需要密码
		//jedis.auth("123456");
		jedis.set("TEST", "连接池测试");
		String test = jedis.get("TEST");
		System.err.println("取值==》" + test);
		
		
		
		jedis.close();
	}

	/**
	 * 直接测试jedis
	 */
	public static void jedisTest(){
		Jedis jedis = new Jedis("localhost",6379);
		int i = 0;
		try {
			
			long start = System.currentTimeMillis();
			while(true){
				long end = System.currentTimeMillis();
				if(end - start >= 1000){
					break;
				}
				
				jedis.set("TESTL_"+ i, i+"");
				i++;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		jedis.close();
		System.err.println("当前redis数据量为:"+jedis.dbSize());
		System.err.println("一秒钟写入次数为:"+i+"");
	}
}
