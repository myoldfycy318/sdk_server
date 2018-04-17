package com.dome.sdkserver.util;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 使用多数据源
 * @author li
 *
 */
public class MultipleDatasource extends AbstractRoutingDataSource {

	private static ThreadLocal<String> dataSourceKey = new ThreadLocal<String>();
	
	@Override
	protected Object determineCurrentLookupKey() {
		
		return dataSourceKey.get();
	}

	public static void setDataSource(String dataSource){
		dataSourceKey.set(dataSource);
	}
	
	public static String getDataSource(){
		return dataSourceKey.get();
	}
}
