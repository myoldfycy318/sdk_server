package com.dome.sdkserver.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.InitializingBean;

public class MyThreadExecutor implements InitializingBean{

	private static MyThreadExecutor instance;

	private ExecutorService executor;

	private MyThreadExecutor(){};
	
	@Override
	public void afterPropertiesSet() throws Exception {
		instance = new MyThreadExecutor();
		instance.executor = Executors.newFixedThreadPool(20);
	}

	/**
	 * 将线程放到线程池中执行
	 * 
	 * @param runable
	 */
	public static void executeInPool(Runnable runable) {
		instance.executor.execute(runable);
	}
}
