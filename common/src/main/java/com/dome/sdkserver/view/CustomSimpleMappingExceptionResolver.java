package com.dome.sdkserver.view;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.alibaba.fastjson.JSONObject;



public class CustomSimpleMappingExceptionResolver extends SimpleMappingExceptionResolver 
{
    private static final Logger log = LoggerFactory.getLogger(CustomSimpleMappingExceptionResolver.class);

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) 
    {
    	String url = request.getRequestURI();
        log.error("request url:" +url, ex);
        String viewName = this.determineViewName(ex, request);
        // JSP格式返回
        if (viewName != null) 
        {
            boolean isJson = request.getHeader("accept").indexOf("application/json") != -1;
            boolean isAjax = request.getHeader("X-Requested-With") != null && request.getHeader("X-Requested-With").indexOf("XMLHttpRequest") != -1;
            if (!isJson && !isAjax) 
            {
                // 如果不是异步请求,返回页面内容
                Integer statusCode = this.determineStatusCode(request, viewName);
                if (statusCode != null) {
                    this.applyStatusCodeIfPossible(request, response, statusCode);
                }
                return this.getModelAndView(viewName, ex, request);
            } 
            else 
            {
                // JSON格式返回
                AjaxResult result = AjaxResult.failedSystemError();
                //byte[] byteArray = null;
                response.setCharacterEncoding("utf-8");
                response.setContentType("text/json;charset=utf-8");
                try
                {
                    //byteArray = JSONObject.toJSONString(result).getBytes();
                    // 输出字节，会出现乱码{"errorMsg":"ç³»ç»Ÿå¼‚å¸¸","responseCode":1005}
                    FileCopyUtils.copy(JSONObject.toJSONString(result), response.getWriter());
                    //FileCopyUtils.copy(byteArray, response.getOutputStream());
                } catch (IOException e)
                {
                    log.error("处理异常出错", e);
                }
                // 本想直接return null，但是发现return null时，会将抛出的异常打印到控制台，干脆return一个新的视图了。
                return new ModelAndView();
            }
        }
        else
        {
            return null;
        }
    }
    
    //把自定义的错误处理器的执行顺序配置为最高
    @Override
    public int getOrder() 
    {
        return HIGHEST_PRECEDENCE;
    }
}
