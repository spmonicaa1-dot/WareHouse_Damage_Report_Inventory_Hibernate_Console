package com.kce.util;

public class InsufficientStockException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String toString()
	{
		return ("Insufficent Stock available");
	}

}
