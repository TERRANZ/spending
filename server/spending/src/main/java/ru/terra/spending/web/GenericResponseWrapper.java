package ru.terra.spending.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class GenericResponseWrapper extends HttpServletResponseWrapper
{

	private ByteArrayOutputStream output;
	private int contentLength;
	private String contentType;
	private PrintWriter printWriter;
	private ServletOutputStream servletOutputStream;

	public GenericResponseWrapper(HttpServletResponse response)
	{
		super(response);

		output = new ByteArrayOutputStream();

		servletOutputStream = new FilterServletOutputStream(output);

		printWriter = new PrintWriter(servletOutputStream, true);

	}

	public byte[] getData()
	{
		try
		{
			output.flush();
		} catch (IOException e)
		{
			// do nothing
		}

		return output.toByteArray();
	}

	public ServletOutputStream getOutputStream()
	{
		return servletOutputStream;
		// return new FilterServletOutputStream(output);
	}

	public PrintWriter getWriter()
	{
		return printWriter;
	}

	public void setContentLength(int length)
	{
		this.contentLength = length;
		// super.setContentLength(length);
	}

	public int getContentLength()
	{
		return contentLength;
	}

	public void setContentType(String type)
	{
		this.contentType = type;
		// super.setContentType(type);
	}

	public String getContentType()
	{
		return contentType;
	}

	public void setHeader(String name, String value)
	{
		if ("Content-Length".equalsIgnoreCase(name))
		{
			// do nothig. Set it Manually later
			return;
		}
		super.setHeader(name, value);
	}

	public void setIntHeader(String name, int value)
	{
		if ("Content-Length".equalsIgnoreCase(name))
		{
			// do nothig. Set it Manually later
			return;
		}
		super.setIntHeader(name, value);
	}

	public void addHeader(String name, String value)
	{
		if ("Content-Length".equalsIgnoreCase(name))
		{
			// do nothig. Set it Manually later
			return;
		}
		super.addHeader(name, value);
	}

	public void addIntHeader(String name, int value)
	{
		if ("Content-Length".equalsIgnoreCase(name))
		{
			// do nothig. Set it Manually later
			return;
		}
		super.addIntHeader(name, value);
	}

}
