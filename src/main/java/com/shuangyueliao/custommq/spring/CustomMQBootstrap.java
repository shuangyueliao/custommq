package com.shuangyueliao.custommq.spring;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CustomMQBootstrap {

	public static final String CONFIG = "classpath:com/shuangyueliao/custommq/spring/broker-server.xml";
	
	public void start(){
		AbstractApplicationContext context = new ClassPathXmlApplicationContext(CONFIG);
		context.start();
	}
	
	public static void main(String[] args){
		CustomMQBootstrap bootstrap = new CustomMQBootstrap();
		bootstrap.start();
	}
}
