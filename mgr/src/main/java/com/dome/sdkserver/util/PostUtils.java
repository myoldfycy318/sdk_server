package com.dome.sdkserver.util;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.*;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.nio.charset.CodingErrorAction;
import java.util.List;
import java.util.Map;

/**
 * Created by heyajun on 2017/7/27.
 */
public class PostUtils {

    private static final Logger logger = LoggerFactory
            .getLogger(ApiConnector.class);

    private static PoolingHttpClientConnectionManager connManager = null;
    private static CloseableHttpClient httpclient = null;

    /**
     * 最大连接数
     */
    public final static int MAX_TOTAL_CONNECTIONS = 800;
    /**
     * 获取连接的最大等待时间
     */
    public final static int WAIT_TIMEOUT = 60000;
    /**
     * 每个路由最大连接数
     */
    public final static int MAX_ROUTE_CONNECTIONS = 400;
    /**
     * 连接超时时间
     */
    public final static int CONNECT_TIMEOUT = 10000;

    private final static String ENCODER_UTF8 = "UTF-8";

    private static final String APPLICATION_JSON = "application/json";

    private static final String CONTENT_TYPE_TEXT_JSON = "text/json";

    static {
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
                .<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .build();

        connManager = new PoolingHttpClientConnectionManager(
                socketFactoryRegistry);

        // Create socket configuration
        SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true)
                .build();

        connManager.setDefaultSocketConfig(socketConfig);
        // Create message constraints
        MessageConstraints messageConstraints = MessageConstraints.custom()
                .setMaxHeaderCount(200).setMaxLineLength(2000).build();
        // Create connection configuration
        ConnectionConfig connectionConfig = ConnectionConfig.custom()
                .setMalformedInputAction(CodingErrorAction.IGNORE)
                .setUnmappableInputAction(CodingErrorAction.IGNORE)
                .setCharset(Consts.UTF_8)
                .setMessageConstraints(messageConstraints).build();
        connManager.setDefaultConnectionConfig(connectionConfig);
        connManager.setMaxTotal(MAX_TOTAL_CONNECTIONS);
        connManager.setDefaultMaxPerRoute(MAX_ROUTE_CONNECTIONS);

        httpclient = HttpClients.custom().setConnectionManager(connManager)
                .build();
    }

    public static String post(String url, List<NameValuePair> pairs,
                              String encoding, Map<String, String> headParams) {
        HttpPost post = new HttpPost(url.trim());
        try {
            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(CONNECT_TIMEOUT)
                    .setConnectTimeout(CONNECT_TIMEOUT)
                    .setConnectionRequestTimeout(CONNECT_TIMEOUT)
                    .setExpectContinueEnabled(false).build();
            post.setConfig(requestConfig);
            for (Map.Entry<String, String> entry : headParams.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                post.addHeader(key, value);
            }
            if (pairs != null && pairs.size() > 0) {
                post.setEntity(new UrlEncodedFormEntity(pairs, encoding));
            }

//            logger.info("[HttpUtils Post] begin invoke url:{} , params:{}",
//                    url, pairs);
            long s1 = System.currentTimeMillis();
            CloseableHttpResponse response = httpclient.execute(post);
            long s2 = System.currentTimeMillis();
            try {
                HttpEntity entity = response.getEntity();
                try {
                    if (entity != null) {
                        String str = EntityUtils.toString(entity, encoding);
//                        logger.info(
//                                "[HttpUtils Post]Debug response, url : {}  , params : {}, response string : {} ,time : {}",
//                                url, pairs, trunkRespContent(str), s2 - s1);
                        return str;
                    }
                } finally {
                    if (entity != null) {
                        entity.getContent().close();
                    }
                }
            } finally {
                if (response != null) {
                    response.close();
                }
            }
        } catch (UnsupportedEncodingException e) {
            logger.error("UnsupportedEncodingException", e);
        } catch (Exception e) {
            logger.error("Exception", e);
        } finally {
            post.releaseConnection();
        }
        return "";
    }
}
