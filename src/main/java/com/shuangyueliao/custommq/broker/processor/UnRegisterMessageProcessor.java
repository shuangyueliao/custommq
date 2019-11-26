package com.shuangyueliao.custommq.broker.processor;

import com.shuangyueliao.custommq.broker.ConsumerMessageListener;
import com.shuangyueliao.custommq.broker.ProducerMessageListener;
import com.shuangyueliao.custommq.consumer.ConsumerContext;
import com.shuangyueliao.custommq.entity.RequestMessage;
import com.shuangyueliao.custommq.entity.ResponseMessage;
import com.shuangyueliao.custommq.entity.UnRegisterMessage;
import io.netty.channel.ChannelHandlerContext;

public class UnRegisterMessageProcessor implements BrokerProcessor{

	public void messageDispatch(RequestMessage request, ResponseMessage response) {
		//broker server 接收到了 consumer 的取消订阅消息
		UnRegisterMessage unsub = (UnRegisterMessage) request.getMessage();
		//从消费者集群中去掉这个消费者
		ConsumerContext.removeQueue(unsub.getConsumerId());
		ConsumerContext.removeClusters(unsub.getConsumerId());
	}

	public void setHookProducer(ProducerMessageListener processProducer) {
	
	}

	public void setHookConsumer(ConsumerMessageListener processConsumer) {
		
	}

	public void setChannelHandler(ChannelHandlerContext channelHandler) {
		
	}

}
