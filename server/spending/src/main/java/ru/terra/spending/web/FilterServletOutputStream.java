package ru.terra.spending.web;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletOutputStream;

public class FilterServletOutputStream extends ServletOutputStream
{

	private DataOutputStream stream;

	public FilterServletOutputStream(OutputStream output)
	{
		stream = new DataOutputStream(output);
	}

	public void write(String b) throws IOException
	{
		stream.writeChars(b);
	}

	public void write(int b) throws IOException
	{
		stream.write(b);
	}

	public void write(byte[] b) throws IOException
	{
		stream.write(b);
	}

	public void write(byte[] b, int off, int len) throws IOException
	{
		stream.write(b, off, len);
	}

	public void close() throws IOException
	{
		// do nothing, do not allow
		super.close();
	}

}
