package com.shuangyueliao.custommq.broker.processor;

import com.shuangyueliao.custommq.broker.ConsumerMessageListener;
import com.shuangyueliao.custommq.broker.ProducerMessageListener;
import com.shuangyueliao.custommq.entity.*;
import io.netty.channel.ChannelHandlerContext;

public class RegisterMessageProcessor implements BrokerProcessor{
	
	private ConsumerMessageListener processConsumer;
	private ChannelHandlerContext channelHandler;

	public void messageDispatch(RequestMessage request, ResponseMessage response) {
		//broker server 接收到 消费者得注册监听消息后得处理方法
		RegisterMessage sub = (RegisterMessage) request.getMessage();
		String consumerId = sub.getConsumerId();
		ChannelData channelData = new ChannelData(channelHandler.channel(),consumerId);
		processConsumer.processConsumerMessage(sub, channelData);
		//服务端给消费者应答
		response.setMessageType(MessageType.CONSUMERACK);
		channelHandler.writeAndFlush(response);
	}

	public void setHookProducer(ProducerMessageListener processProducer) {
		
	}

	public void setHookConsumer(ConsumerMessageListener processConsumer) {
		this.processConsumer = processConsumer;
	}

	public void setChannelHandler(ChannelHandlerContext channelHandler) {
		this.channelHandler = channelHandler;
	}

}
