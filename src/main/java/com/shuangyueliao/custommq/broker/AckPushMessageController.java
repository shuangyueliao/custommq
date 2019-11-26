package com.shuangyueliao.custommq.broker;


import com.shuangyueliao.custommq.core.AckMessageCache;

import java.util.concurrent.Callable;

public class AckPushMessageController implements Callable<Void>{

    private volatile boolean stoped = false;
	
    public void stop() {
        stoped = true;
    }

    public boolean isStoped() {
        return stoped;
    }
    
	@Override
	public Void call() throws Exception {
		AckMessageCache cache = AckMessageCache.getInsance();
		int timeout  = 1000;
		while(!stoped){
			if(cache.hold(timeout)){
				cache.commit();
			}
		}
		return null;
	}

}
