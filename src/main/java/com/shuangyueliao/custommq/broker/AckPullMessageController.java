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

/**
 * 这个线程负责从应答消息队列中获取应答消息，并发送
 * @author swang18
 *
 */
public class AckPullMessageController implements Callable<Void>{

	private volatile boolean stoped = false;
	
	public void stop(){
		stoped = false;
	}
	
    public boolean isStoped() {
        return stoped;
    }
	
	@Override
	public Void call() throws Exception {
		while(!stoped){
			SemaphoreCache.acquire(SemaphoreConfig.ACKMESSAGE.value);
            ProducerAckMessage ack = AckMessageTaskQueue.getAck();
            String requestId = ack.getAck();
            ack.setAck("");
            
            Channel channel = ProducerCache.remove(requestId);
            if(NettyUtil.validateChannel(channel)){
            	ResponseMessage response = new ResponseMessage();
            	response.setMsgId(requestId);
            	response.setSourceType(SourceType.BROKER);
            	response.setMessage(ack);
            	response.setMessageType(MessageType.PRODUCERACK);
            	
            	channel.writeAndFlush(response);
            }
		}
		return null;
	}

}
