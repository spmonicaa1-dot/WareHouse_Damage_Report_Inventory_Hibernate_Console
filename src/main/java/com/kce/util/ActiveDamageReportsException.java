package com.kce.util;

public class ActiveDamageReportsException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String toString()
	{
		return ("Active damage reports exist for this item");
	}

}
