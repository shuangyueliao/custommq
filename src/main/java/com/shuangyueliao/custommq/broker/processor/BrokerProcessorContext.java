package com.shuangyueliao.custommq.broker.processor;

import com.shuangyueliao.custommq.broker.ConsumerMessageListener;
import com.shuangyueliao.custommq.broker.ProducerMessageListener;
import com.shuangyueliao.custommq.entity.RequestMessage;
import com.shuangyueliao.custommq.entity.ResponseMessage;
import com.shuangyueliao.custommq.entity.SourceType;
import io.netty.channel.ChannelHandlerContext;

public class BrokerProcessorContext {
    
    private RequestMessage request;
    private ResponseMessage response;
    private ChannelHandlerContext channelHandler;
    
    
    private ProducerMessageListener processProducer;
    private ConsumerMessageListener processConsumer;
    private BrokerProcessor processor;
    
    
    public BrokerProcessorContext(RequestMessage request,ResponseMessage response,ChannelHandlerContext channelHandler){
    	this.request = request;
    	this.response = response;
    	this.channelHandler = channelHandler;
    }


	public void setProcessProducer(ProducerMessageListener processProducer) {
		this.processProducer = processProducer;
	}

	public void setProcessConsumer(ConsumerMessageListener processConsumer) {
		this.processConsumer = processConsumer;
	}
    
	public void invoke(){
		switch(request.getMessageType()){
			case MESSAGE:
				//收到 message 类型得消息后对应得 processor
				processor = request.getSourceType() == SourceType.PRODUCER ? new ProducerMessageProcessor():new ConsumerMessageProcessor();
				break;
			case REGISTER:
				processor = new RegisterMessageProcessor();
				break;
			case UNREGISTER:
				processor = new UnRegisterMessageProcessor();
				break;
			default:
				break;
		}
		
		processor.setChannelHandler(channelHandler);
		processor.setHookConsumer(processConsumer);
		processor.setHookProducer(processProducer);
		processor.messageDispatch(request, response);
	}
    

}
