package com.shuangyueliao.custommq.spring;

import com.shuangyueliao.custommq.broker.CustomMQBroker;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Spring容器会检测容器中的所有Bean，如果发现某个Bean实现了ApplicationContextAware接口，Spring容器会在创建该Bean之后，自动调用该Bean的setApplicationContextAware()方法
 * InitializingBean接口为bean提供了初始化方法的方式，它只包括afterPropertiesSet方法，凡是继承该接口的类，在初始化bean的时候会执行该方法。
 */
public class CustomMQStarter extends CustomMQBroker implements ApplicationContextAware, InitializingBean{

	public CustomMQStarter(Integer port) {
		super(port);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		init();
		start();
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
        System.out.printf("CustomMQ Server Start Success![author shuangyueliao]\n");
	}

}
