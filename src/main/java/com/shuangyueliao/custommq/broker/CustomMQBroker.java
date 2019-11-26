package com.shuangyueliao.custommq.broker;

import com.shuangyueliao.custommq.common.MQServer;
import com.shuangyueliao.custommq.serialize.KryoCodecUtil;
import com.shuangyueliao.custommq.serialize.KryoPoolFactory;
import com.shuangyueliao.custommq.serialize.MessageObjectDecoder;
import com.shuangyueliao.custommq.serialize.MessageObjectEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class CustomMQBroker extends CoreServer implements MQServer {
	
	private Integer port;
	
	private EventLoopGroup boss;
	private EventLoopGroup worker;
    private ServerBootstrap bootstrap;
	
	private BrokerNettyHandler handler;
	
	public CustomMQBroker(Integer port){
		this.port = port;
	}

	public void init() {
        final KryoCodecUtil util = new KryoCodecUtil(KryoPoolFactory.getKryoPoolInstance());
		handler = new BrokerNettyHandler().buildProcessConsumer(new ConsumerMessageListenerImpl()).buildProcessProducer(new ProducerMessageListenerImpl());
		boss = new NioEventLoopGroup(1);
		worker = new NioEventLoopGroup();
		
		bootstrap = new ServerBootstrap();
		
		bootstrap.group(boss,worker)
		.channel(NioServerSocketChannel.class)
		.option(ChannelOption.SO_BACKLOG, 128)
        .childOption(ChannelOption.SO_KEEPALIVE, true)
        .handler(new LoggingHandler(LogLevel.INFO))
		.childHandler(new ChannelInitializer<SocketChannel>(){

			@Override
			protected void initChannel(SocketChannel arg0) throws Exception {
				ChannelPipeline pipeline = arg0.pipeline();
				pipeline.addLast(new MessageObjectEncoder(util));
				pipeline.addLast(new MessageObjectDecoder(util));
				pipeline.addLast(handler);
				
			}
			
		});
		super.init();
	}

	public void start() {
		System.out.printf("broker server ip:[%s]\n", "0.0.0.0");
		try {
            ChannelFuture sync = this.bootstrap.bind(port).sync();
            super.start();
            sync.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void stop() {
		super.stop();
		boss.shutdownGracefully();
		worker.shutdownGracefully();
	}
	
	public static void main(String[] args){
		CustomMQBroker server = new CustomMQBroker(8092);
		server.init();
		server.start();
	}

}
