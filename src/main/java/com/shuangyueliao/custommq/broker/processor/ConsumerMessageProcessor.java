package com.shuangyueliao.custommq.broker.processor;

import com.shuangyueliao.custommq.broker.ConsumerMessageListener;
import com.shuangyueliao.custommq.broker.ProducerMessageListener;
import com.shuangyueliao.custommq.broker.SendMessageLauncher;
import com.shuangyueliao.custommq.common.CallBack;
import com.shuangyueliao.custommq.entity.RequestMessage;
import com.shuangyueliao.custommq.entity.ResponseMessage;
import io.netty.channel.ChannelHandlerContext;

public class ConsumerMessageProcessor implements BrokerProcessor{

	public void messageDispatch(RequestMessage request, ResponseMessage response) {
		//broker server 接收到了 consumer 得应答消息，表明消费者接收到了消息
        String key = response.getMsgId();
        if (SendMessageLauncher.getInstance().trace(key)) {
            CallBack<Object> future = SendMessageLauncher.getInstance().detach(key);
            if (future == null) {
                return;
            } else {
                future.setMessageResult(request);
            }
        } else {
            return;
        }
	}

	public void setHookProducer(ProducerMessageListener processProducer) {
		
	}

	public void setHookConsumer(ConsumerMessageListener processConsumer) {
		
	}

	public void setChannelHandler(ChannelHandlerContext channelHandler) {
		
	}

}
