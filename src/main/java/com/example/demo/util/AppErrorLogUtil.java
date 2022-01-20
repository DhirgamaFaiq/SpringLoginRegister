package com.example.demo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppErrorLogUtil {
	private static final Logger log = LoggerFactory.getLogger(AppErrorLogUtil.class);

	public static void WriteErrorLog(String uuid, String message, String ex) {
		if(null!=uuid){
			log.error(uuid+" | "+message + " cause by, " + ex);
		}else {
			log.error(message+ " cause by, " + ex);
		}
	}
}
