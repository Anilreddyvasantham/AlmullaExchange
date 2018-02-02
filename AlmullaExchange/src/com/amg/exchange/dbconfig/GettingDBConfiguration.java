package com.amg.exchange.dbconfig;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class GettingDBConfiguration {
	
	public String getPath() {
		String path = this.getClass().getClassLoader().getResource("").getPath();
		String fullPath;
		String reponsePath = "";
		try {
			fullPath = URLDecoder.decode(path, "UTF-8");
			String pathArr[] = fullPath.split("/WEB-INF/");
			fullPath = pathArr[0];
			reponsePath = new File(fullPath).getPath() + File.separatorChar + "WEB-INF/dbProc.properties";
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return reponsePath;
	}
}
