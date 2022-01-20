package com.example.demo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AppLogUtil {
	private static final Logger log = LoggerFactory.getLogger(AppLogUtil.class);

	public static void WriteInfoLog(String guid, String message) {
		String msg = message;
		if(null==guid){
			log.info(msg);
		}else {
			log.info(guid+" | "+msg);
		}
	}

	public static void WriteErrorLog(String guid, String message, String ex) {
		AppErrorLogUtil.WriteErrorLog(guid, message, ex);
	}
}
