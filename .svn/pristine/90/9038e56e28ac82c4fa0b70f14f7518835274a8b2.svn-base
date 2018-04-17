package com.dome.sdkserver.bq.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.InitializingBean;

public class NotifyExecutor implements InitializingBean {
	private static NotifyExecutor instance;

	private ExecutorService notifyExecutor;

	@Override
	public void afterPropertiesSet() throws Exception {
		instance = new NotifyExecutor();
		instance.notifyExecutor = Executors.newFixedThreadPool(20);
	}

	/**
	 * 执行支付结果异步通知线程
	 * 
	 * @param runable
	 */
	public static void executePayResultNotify(Runnable runable) {
		instance.notifyExecutor.execute(runable);
	}
}