package com.dome.sdkserver.aop;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dome.sdkserver.util.MultipleDatasource;
import com.qianbao.framework.datasource.DataSourceKeyDeterminer;
import com.qianbao.framework.datasource.annotation.DataSource;

/**
 * 切换到测试联调数据源，同步商户、应用、计费点数据
 * @author li
 *
 */
public class DataSourceAspect
{
	private Logger logger = LoggerFactory.getLogger(DataSourceAspect.class);
	
	private DataSourceKeyDeterminer dataSourceKeyDeterminer;
	
	public Object doAround(ProceedingJoinPoint jp) throws Throwable {
		Method method = ((MethodSignature)jp.getSignature()).getMethod();
		Object[] parameterValues = jp.getArgs();
		Class<?>[] parameterTypes = method.getParameterTypes();
		Annotation[][] parameterAnnotations = null;
		try {
			parameterAnnotations = jp.getTarget().getClass().getMethod(method.getName(), parameterTypes).getParameterAnnotations();
		} catch (NoSuchMethodException e) {
			logger.error("switch data source occurs exception", e);
		} catch (SecurityException e) {
			logger.error("switch data source occurs exception", e);
		}
		String dataSource = null;
		out :
		if (parameterAnnotations != null){
			for (int i = 0; i < parameterAnnotations.length; i++) {
				Annotation[] ans = parameterAnnotations[i];
				for (int j = 0; j < ans.length; j++) {
					Annotation annotation = ans[j];
					if (annotation instanceof DataSource) {
						DataSource data = (DataSource) annotation;
						String dataSourceName = data.name();
						if(!StringUtils.isEmpty(dataSource)){
							dataSource = dataSourceName;
							break out;
						}
						String field = data.field();
						if(StringUtils.isEmpty(field)){
							continue;
						}else{							
							dataSource = dataSourceKeyDeterminer.determine(data.field(), Long.valueOf(parameterValues[i].toString()));
							break out;
						}
					}
				}
			}
		}
		String orginalDataSource = MultipleDatasource.getDataSource();
		Object obj = null;
		if (!StringUtils.isEmpty(dataSource)) {
			MultipleDatasource.setDataSource(dataSource);
			obj = jp.proceed(parameterValues);
			MultipleDatasource.setDataSource(orginalDataSource);
		} else {
			obj = jp.proceed(parameterValues);
		}
		
		return obj;
	}

	public DataSourceKeyDeterminer getDataSourceKeyDeterminer() {
		return dataSourceKeyDeterminer;
	}

	public void setDataSourceKeyDeterminer(
			DataSourceKeyDeterminer dataSourceKeyDeterminer) {
		this.dataSourceKeyDeterminer = dataSourceKeyDeterminer;
	}

}
