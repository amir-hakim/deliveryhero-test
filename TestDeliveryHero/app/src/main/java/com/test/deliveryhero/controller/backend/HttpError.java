package com.test.deliveryhero.controller.backend;

/**
 * Wrap the Http error in this class
 * Hold the code and message for the error, to Unify all the errors in one class
 */
public class HttpError extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	
	String msg = "";
	double statusCode = -1;

	/*
	 * Constructors
	 */
	public HttpError(String msg, double statusCode)
	{
		super(msg);
		this.msg = msg;
		this.statusCode = statusCode;
	}

	/*
	 * Setters & Getters
	 */
	public String getErrorMsg()
	{
		return msg;
	}

	public void setErrorMsg(String msg)
	{
		this.msg = msg;
	}

	public int getStatusCode()
	{
		return (int)statusCode;
	}

	public void setStatusCode(int statusCode)
	{
		this.statusCode = statusCode;
	}

}
