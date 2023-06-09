 package com.example.App1.service;

public class Util {
	
	//-------------------------------------------------------------------------------------------------------
	public static String generateActivationCode()
	{
		StringBuilder sb = new StringBuilder() ;
		String alphanumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"+
									"0123456789"+
									"abcdefghijklmnopqrstuvwxyz" ;
		int index  ;
		for (int i = 0; i < 30 ; i++) {
			index = (int) (alphanumericString.length()*Math.random()) ;
			sb.append(alphanumericString.charAt(index));
		}
		return sb.toString() ;
	}
	
	public static String generateCode()
	{
		StringBuilder sb = new StringBuilder() ;
		String alphanumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"+
				"0123456789"+
				"abcdefghijklmnopqrstuvwxyz" ;
		int index  ;
		for (int i = 0; i < 8 ; i++) {
			index = (int) (alphanumericString.length()*Math.random()) ;
			sb.append(alphanumericString.charAt(index));
		}
		return sb.toString() ;
	}
}
