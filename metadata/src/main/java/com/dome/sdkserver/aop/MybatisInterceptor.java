package com.dome.sdkserver.aop;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.dome.sdkserver.debug.DevepDebugConfig;

/**
 * 借助mybatis的拦截器，打印sql和执行时间
 * 若仅需要打印sql，通过logback配置就能满足要求
 * 
 * @author li
 *
 */
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }),
        @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
                RowBounds.class, ResultHandler.class }) })
public class MybatisInterceptor implements Interceptor {
 
	/**
	 * 项目中root日志级别一般设置为Info，aop注入日志使用.info()
	 */
	private Logger logger = LoggerFactory.getLogger("sql");
	
	private static final boolean NEED_MYBATIS_PRINTSQL_LOGGER = DevepDebugConfig.isDevepMode() && DevepDebugConfig.needMybatisPrintSql();
	private static final int MYBATIS_PRINTSQL_EXECUTETIME = DevepDebugConfig.getMybatisPrintSqlExecTime();
    @SuppressWarnings("unused")
	private Properties properties;
 
    public Object intercept(Invocation invocation) throws Throwable {
    	if (!NEED_MYBATIS_PRINTSQL_LOGGER){
    		return invocation.proceed();
    	}
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Object parameter = null;
        if (invocation.getArgs().length > 1) {
            parameter = invocation.getArgs()[1];
        }
        String sqlId = mappedStatement.getId();
        BoundSql boundSql = mappedStatement.getBoundSql(parameter);
        Configuration configuration = mappedStatement.getConfiguration();
        Object returnValue = null;
        long start = System.currentTimeMillis();
        returnValue = invocation.proceed();
        long end = System.currentTimeMillis();
        long time = (end - start);
        if (time >= MYBATIS_PRINTSQL_EXECUTETIME) {
            String sql = getSql(configuration, boundSql, sqlId, time);
            logger.info(sql);
            if (returnValue instanceof List){
            	logger.info("sql query result: " + ((List)returnValue).size());
            } else if (returnValue instanceof Integer){
            	logger.info("sql update result: " + (int)returnValue);
            } else {
            	logger.info("sql result: " + returnValue);
            }
        }
        return returnValue;
    }
 
    private static String getSql(Configuration configuration, BoundSql boundSql, String sqlId, long time) {
        String sql = showSql(configuration, boundSql);
        StringBuilder str = new StringBuilder("Class: ");
        str.append(sqlId);
        str.append(", Sql: \"");
        str.append(sql);
        str.append("\", Execute time: ");
        str.append(time);
        str.append("ms");
        return str.toString();
    }
 
    private static String getParameterValue(Object obj) {
        String value = null;
        if (obj instanceof String) {
            value = "'" + obj.toString() + "'";
        } else if (obj instanceof Date) {
            DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
            value = "'" + formatter.format((Date)obj) + "'";
        } else {
            if (obj != null) {
                value = obj.toString();
            } else {
                value = "";
            }
 
        }
        return value;
    }
 
    private static String showSql(Configuration configuration, BoundSql boundSql) {
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        if (parameterMappings.size() > 0 && parameterObject != null) {
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                sql = sql.replaceFirst("\\?", getParameterValue(parameterObject));
 
            } else {
                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                for (ParameterMapping parameterMapping : parameterMappings) {
                    String propertyName = parameterMapping.getProperty();
                    if (metaObject.hasGetter(propertyName)) {
                        Object obj = metaObject.getValue(propertyName);
                        sql = sql.replaceFirst("\\?", getParameterValue(obj));
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        Object obj = boundSql.getAdditionalParameter(propertyName);
                        sql = sql.replaceFirst("\\?", getParameterValue(obj));
                    }  else{
                        @SuppressWarnings("rawtypes")
						Map map = (Map)metaObject ;
                        sql = sql.replaceFirst("\\?", getParameterValue(map.get(propertyName)));
}  
                }
            }
        }
        return sql;
    }
 
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }
 
    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
