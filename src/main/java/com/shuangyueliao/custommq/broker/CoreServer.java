package com.shuangyueliao.custommq.broker;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.shuangyueliao.custommq.common.MQServer;

import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;

public class CoreServer implements MQServer {

    protected int workerThreads = Runtime.getRuntime().availableProcessors() * 2;
    protected ListeningExecutorService executor = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(workerThreads));
    protected ExecutorCompletionService<Void> executorService;
    
	@Override
	public void init() {
		executorService = new ExecutorCompletionService<Void>(executor);
	}

	@Override
	public void start() {
        for (int i = 0; i < workerThreads; i++) {
            executorService.submit(new SendMessageController());
            executorService.submit(new AckPullMessageController());
            executorService.submit(new AckPushMessageController());
        }
	}

	@Override
	public void stop() {
        executor.shutdown();
	}

}
