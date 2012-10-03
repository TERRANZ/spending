package ru.terra.spending.web;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonpCallbackFilter implements Filter
{

	private static Logger log = LoggerFactory.getLogger(JsonpCallbackFilter.class);

	public void init(FilterConfig fConfig) throws ServletException
	{
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
	{
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		@SuppressWarnings("unchecked")
		Map<String, String[]> parms = httpRequest.getParameterMap();

		if (parms.containsKey("callback"))
		{
			log.debug("Wrapping response with JSONP callback '" + parms.get("callback")[0] + "'");
			httpResponse.setContentType("text/javascript;charset=UTF-8");
			GenericResponseWrapper wrapper = new GenericResponseWrapper(httpResponse);
			chain.doFilter(request, wrapper);

			OutputStream out = httpResponse.getOutputStream();
			out.write(new String(parms.get("callback")[0] + "(").getBytes());
			out.write(wrapper.getData());
			// chain.doFilter(request, httpResponse);
			out.write(new String(");").getBytes());
		}
		else
		{
			chain.doFilter(request, response);
		}
	}

	public void destroy()
	{
	}
}
