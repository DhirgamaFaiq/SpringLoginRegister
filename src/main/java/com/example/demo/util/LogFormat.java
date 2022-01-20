package com.example.demo.util;

public class LogFormat {

	public static String LogFormatter(String guid, String prefix, String messages) {
		StringBuilder sb = new StringBuilder();
		sb.append(guid);
		sb.append(" || ");
		sb.append("["+prefix+"]");
		sb.append(" "+messages+"");
		
		return sb.toString();
	}

}
