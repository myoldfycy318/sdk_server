package com.dome.sdkserver.security.role;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jasig.cas.client.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.util.Assert;

public class CasAuthenticationEntryPoint implements AuthenticationEntryPoint,
		InitializingBean {
	private static final String RESPONSE_TYPE_APPLICATION_JSON = "application/json";
	private static final String HEADER_RESPONSE_CONTENT_TYPE = "Response-Content-Type";
	private static final String JSON_MSG_INVALID_SESSION = "{\"success\":false,\"message\":\"session timeout,please login!\",\"returnCode\":-1000,\"data\":null}";
	private static final String SESSION_TIME_OUT_MSG = "{\"success\":false,\"message\":\"session timeout,please login!\",\"returnCode\":-1,\"data\":null}";
	private static final String JSON_MSG_INVALID_SESSION_NEW = "{\"message\":\"session timeout,please login!\",\"responseCode\":1004,\"data\":null}";
	private final static Logger LOG = LoggerFactory
			.getLogger(CasAuthenticationEntryPoint.class);
	// ~ Instance fields
	// ================================================================================================
	private ServiceProperties serviceProperties;

	private String loginUrl;
	private String checkUserUrl;

	/**
	 * Determines whether the Service URL should include the session id for the
	 * specific user. As of CAS 3.0.5, the session id will automatically be
	 * stripped. However, older versions of CAS (i.e. CAS 2), do not
	 * automatically strip the session identifier (this is a bug on the part of
	 * the older server implementations), so an option to disable the session
	 * encoding is provided for backwards compatibility.
	 * 
	 * By default, encoding is enabled.
	 * 
	 * @deprecated since 3.0.0 because CAS is currently on 3.3.5.
	 */
	@Deprecated
	private boolean encodeServiceUrlWithSessionId = true;

	// ~ Methods
	// ========================================================================================================

	public void afterPropertiesSet() throws Exception {
		Assert.hasLength(this.loginUrl, "loginUrl must be specified");
		Assert.notNull(this.serviceProperties,
				"serviceProperties must be specified");
	}

	private boolean isNotCheckUserUrl(String url) {
		return checkUserUrl == null || !checkUserUrl.equalsIgnoreCase(url);
	}

	public final void commence(final HttpServletRequest servletRequest,
			final HttpServletResponse response,
			final AuthenticationException authenticationException)
			throws IOException, ServletException {
		
		final String urlEncodedService = createServiceUrl(servletRequest,
				response);
		final String redirectUrl = createRedirectUrl(urlEncodedService);

		preCommence(servletRequest, response);

		String type = servletRequest.getHeader(HEADER_RESPONSE_CONTENT_TYPE);
		String requestURI = servletRequest.getServletPath();
		LOG.info("invalid session for content type < " + type + ">"
				+ " and url:" + requestURI);

		// 新版客户端，2013-10-17.
		String client = servletRequest.getHeader("sourceType");
		LOG.info("client_sourceType:" + client);
		if (client != null && client.equals("client")
				&& isNotCheckUserUrl(requestURI)) {
			LOG.info("客户端请求session失效，需要登录" + "\"");
			// response.setHeader("needTicket", "1");
			// response.setStatus(200);
			PrintWriter writer = response.getWriter();
			writer.write(JSON_MSG_INVALID_SESSION_NEW);
			writer.close();
			return;
		}

		// web端ajax异步session失效跳转到登陆页面2014-02-18
		if (servletRequest.getHeader("x-requested-with") != null
				&& servletRequest.getHeader("x-requested-with")
						.equalsIgnoreCase("XMLHttpRequest")) {
			LOG.info("web端ajax异步调用session失效，需要登录");
			PrintWriter writer = response.getWriter();
			writer.write(SESSION_TIME_OUT_MSG);
			writer.close();
			return;
		}

		if (RESPONSE_TYPE_APPLICATION_JSON.equalsIgnoreCase(type)
				&& isNotCheckUserUrl(requestURI)) {
			LOG.info("return json format data of session timeout.");
			PrintWriter writer = response.getWriter();
			writer.write(JSON_MSG_INVALID_SESSION);
			writer.close();
		} else {
			response.sendRedirect(redirectUrl);
		}
	}

	/**
	 * Constructs a new Service Url. The default implementation relies on the
	 * CAS client to do the bulk of the work.
	 * 
	 * @param request
	 *            the HttpServletRequest
	 * @param response
	 *            the HttpServlet Response
	 * @return the constructed service url. CANNOT be NULL.
	 */
	protected String createServiceUrl(final HttpServletRequest request,
			final HttpServletResponse response) {
		return CommonUtils.constructServiceUrl(null, response,
				this.serviceProperties.getService(), null,
				this.serviceProperties.getArtifactParameter(),
				this.encodeServiceUrlWithSessionId);
	}

	/**
	 * Constructs the Url for Redirection to the CAS server. Default
	 * implementation relies on the CAS client to do the bulk of the work.
	 * 
	 * @param serviceUrl
	 *            the service url that should be included.
	 * @return the redirect url. CANNOT be NULL.
	 */
	protected String createRedirectUrl(final String serviceUrl) {
		return CommonUtils.constructRedirectUrl(this.loginUrl,
				this.serviceProperties.getServiceParameter(), serviceUrl,
				this.serviceProperties.isSendRenew(), false);
	}

	/**
	 * Template method for you to do your own pre-processing before the redirect
	 * occurs.
	 * 
	 * @param request
	 *            the HttpServletRequest
	 * @param response
	 *            the HttpServletResponse
	 */
	protected void preCommence(final HttpServletRequest request,
			final HttpServletResponse response) {

	}

	/**
	 * The enterprise-wide CAS login URL. Usually something like
	 * <code>https://www.mycompany.com/cas/login</code>.
	 * 
	 * @return the enterprise-wide CAS login URL
	 */
	public final String getLoginUrl() {
		return this.loginUrl;
	}

	public final ServiceProperties getServiceProperties() {
		return this.serviceProperties;
	}

	public final void setLoginUrl(final String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public void setCheckUserUrl(String checkUserUrl) {
		this.checkUserUrl = checkUserUrl;
	}

	public final void setServiceProperties(
			final ServiceProperties serviceProperties) {
		this.serviceProperties = serviceProperties;
	}

	/**
	 * Sets whether to encode the service url with the session id or not.
	 * 
	 * @param encodeServiceUrlWithSessionId
	 *            whether to encode the service url with the session id or not.
	 * @deprecated since 3.0.0 because CAS is currently on 3.3.5.
	 */
	@Deprecated
	public final void setEncodeServiceUrlWithSessionId(
			final boolean encodeServiceUrlWithSessionId) {
		this.encodeServiceUrlWithSessionId = encodeServiceUrlWithSessionId;
	}

	/**
	 * Sets whether to encode the service url with the session id or not.
	 * 
	 * @return whether to encode the service url with the session id or not.
	 * 
	 * @deprecated since 3.0.0 because CAS is currently on 3.3.5.
	 */
	@Deprecated
	protected boolean getEncodeServiceUrlWithSessionId() {
		return this.encodeServiceUrlWithSessionId;
	}
}
