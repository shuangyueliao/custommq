package com.shuangyueliao.custommq.broker;


import com.shuangyueliao.custommq.entity.ChannelData;
import com.shuangyueliao.custommq.entity.RegisterMessage;

//broker 接收到消费者消息后的处理方法，broker 会持有这个接口的实现类
public interface ConsumerMessageListener {
    void processConsumerMessage(RegisterMessage msg, ChannelData channel);
}
