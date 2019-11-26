package com.shuangyueliao.custommq.util;

import com.google.common.base.Preconditions;
import io.netty.channel.Channel;

public class NettyUtil {
    public static boolean validateChannel(Channel channel) {
        Preconditions.checkNotNull(channel, "channel can not be null");
        return channel.isActive() && channel.isOpen() && channel.isWritable();
    }
}
