package com.dome.sdkserver.listener;

import com.dome.sdkserver.biz.utils.SpringBeanProxy;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContextEvent;

/**
 * 不必专门为注入WebApplicationContext写一个servlet，继承ContextLoaderListener即可
 */
public class SuperContextLoaderListener extends ContextLoaderListener {

    /**
     * @param event ServletContextEvent
     * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent event) {
        super.contextInitialized(event);

        WebApplicationContext context = (WebApplicationContext) event.getServletContext()
                .getAttribute(
                        WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);

        SpringBeanProxy.setApplicationContext(context);
        //SDKConfig.getConfig().loadPropertiesFromSrc(); 银联暂不接入-2017-5-8
    }
}
