package com.shuangyueliao.custommq.test;


import com.shuangyueliao.custommq.consumer.CustomMQConsumer;
import com.shuangyueliao.custommq.consumer.ReceiveMessageCallBack;
import com.shuangyueliao.custommq.entity.Message;

public class Consumer1 {
	public static ReceiveMessageCallBack callBack = new ReceiveMessageCallBack(){
		@Override
		public void onCallBack(Message message) {
			System.out.printf("CustomMQConsumer 收到消息编号:%s,消息内容:%s\n", message.getMsgId(), new String(message.getBody()));
		}
	};
	
	public static void main(String[] args){
//		Exchange exchange = new Exchange();
//		exchange.setName("Exchanger");
//		exchange.setRegrex(".*update.*");
//		CustomMQConsumer consumer = new CustomMQConsumer("0.0.0.0",8092,"",exchange,callBack);
		CustomMQConsumer consumer = new CustomMQConsumer("0.0.0.0",8092,"phonequeue",null,callBack);
		consumer.init();
		consumer.start();
	}
}
