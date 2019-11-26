package com.shuangyueliao.custommq.broker.processor;

import com.shuangyueliao.custommq.broker.ConsumerMessageListener;
import com.shuangyueliao.custommq.broker.ProducerMessageListener;
import com.shuangyueliao.custommq.entity.RequestMessage;
import com.shuangyueliao.custommq.entity.ResponseMessage;
import io.netty.channel.ChannelHandlerContext;


public interface BrokerProcessor {
	
    void messageDispatch(RequestMessage request, ResponseMessage response);

    void setHookProducer(ProducerMessageListener processProducer);

    void setHookConsumer(ConsumerMessageListener processConsumer);

    void setChannelHandler(ChannelHandlerContext channelHandler);
}
