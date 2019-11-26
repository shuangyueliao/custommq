package com.shuangyueliao.custommq.broker.processor;

import com.shuangyueliao.custommq.broker.ConsumerMessageListener;
import com.shuangyueliao.custommq.broker.ProducerMessageListener;
import com.shuangyueliao.custommq.entity.Message;
import com.shuangyueliao.custommq.entity.RequestMessage;
import com.shuangyueliao.custommq.entity.ResponseMessage;
import io.netty.channel.ChannelHandlerContext;

/**
 * 
 * borker server 接收到生产者得消息后得处理类
 *
 */
public class ProducerMessageProcessor implements BrokerProcessor{
	
	private ProducerMessageListener processProducer;
	private ChannelHandlerContext channelHandler;

	public void messageDispatch(RequestMessage request, ResponseMessage response) {
		//得到生产者发送得消息
		Message message = (Message) request.getMessage();
		//生产者发送消息后得回调方法
		processProducer.processProducerMessage(message, request.getMsgId(), channelHandler.channel());
	}

	public void setHookProducer(ProducerMessageListener processProducer) {
		this.processProducer = processProducer;
	}

	public void setHookConsumer(ConsumerMessageListener processConsumer) {
		
	}

	public void setChannelHandler(ChannelHandlerContext channelHandler) {
		this.channelHandler = channelHandler;
	}

}
