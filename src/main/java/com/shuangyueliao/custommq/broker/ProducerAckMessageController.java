package com.shuangyueliao.custommq.broker;

import com.shuangyueliao.custommq.core.AckMessageTaskQueue;
import com.shuangyueliao.custommq.core.ProducerCache;
import com.shuangyueliao.custommq.core.SemaphoreCache;
import com.shuangyueliao.custommq.core.SemaphoreConfig;
import com.shuangyueliao.custommq.entity.MessageType;
import com.shuangyueliao.custommq.entity.ProducerAckMessage;
import com.shuangyueliao.custommq.entity.ResponseMessage;
import com.shuangyueliao.custommq.entity.SourceType;
import com.shuangyueliao.custommq.util.NettyUtil;
import io.netty.channel.Channel;

import java.util.concurrent.Callable;

public class ProducerAckMessageController implements Callable<Void>{
	
	private volatile boolean stoped = false;
	
    public void stop() {
        stoped = true;
    }

    public boolean isStoped() {
        return stoped;
    }

	@Override
	public Void call() throws Exception {
		while(!stoped){
			//线程启动后会停到这，等待应答消息队列释放信号量
			SemaphoreCache.acquire(SemaphoreConfig.ACKMESSAGE.value);
			ProducerAckMessage ack = AckMessageTaskQueue.getAck();
			String requestId = ack.getAck();
			ack.setAck("");
			//从生产者 channel 队列中获取 生产者的 channel，获取的同时要删除
			Channel channel = ProducerCache.remove(requestId);
			if(NettyUtil.validateChannel(channel)){
				ResponseMessage response = new ResponseMessage();
				response.setMsgId(requestId);
				response.setSourceType(SourceType.BROKER);
				response.setMessageType(MessageType.PRODUCERACK);
				response.setMessage(ack);
				
				channel.writeAndFlush(response);
			}
			
		}
		return null;
	}

}
