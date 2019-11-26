package com.shuangyueliao.custommq.test;

import com.shuangyueliao.custommq.entity.Message;
import com.shuangyueliao.custommq.entity.ProducerAckMessage;
import com.shuangyueliao.custommq.entity.WorkMode;
import com.shuangyueliao.custommq.producer.CustomMQProducer;


public class Producer1 {
	
	public static void main(String[] args){
		CustomMQProducer producer = new CustomMQProducer("127.0.0.1",8092);
        producer.init();
        producer.start();
        
        System.out.println("开始发送数据");
//        Exchange exchange = new Exchange();
//        exchange.setName("Exchanger");
//        exchange.setRegrex("m.select");
        Message message = new Message();
        String str = "Hello CustomMQ From Producer1[" + 1 + "]";
        message.setBody(str.getBytes());
//        ProducerAckMessage result = producer.produce(WorkMode.EXCHANGE_TOPIC,"",exchange,message);
        ProducerAckMessage result = producer.produce(WorkMode.WORKER_ROBIN,"phonequeue",null,message);
        if (result.getStatus() == (ProducerAckMessage.SUCCESS)) {
            System.out.printf("CustomMQProducer1 生产消息发送成功得到反馈\n", result.getMsgId());
        }
        
        System.out.println("发送数据结束");
        producer.stop();
	}
}
