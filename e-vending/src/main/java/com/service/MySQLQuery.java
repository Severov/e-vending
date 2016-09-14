package com.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("mySQLQuery")
public class MySQLQuery {

	private static final Map<String, String>	sqlQuery	= new HashMap<String, String>();
	private static final String					path		= "/META-INF/sql/";

	@Autowired
	public MySQLQuery(ServletContext context) {
		File F = new File(context.getRealPath(path));
		File[] fList = F.listFiles();

		for (int i = 0; i < fList.length; i++) {
			if (fList[i].isFile()) {
				sqlQuery.put(fList[i].getName(), getText(fList[i]));
			}
		}
	}

	private String getText(File file) {
		String buf = "";
		try {
			Scanner in = new Scanner(file);
			while (in.hasNext())
				buf += in.nextLine() + "\r\n";
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return buf;
	}

	public String getSQL(String key) {
		return sqlQuery.get(key);
	}

	public void beep() {
		sqlQuery.forEach((k, v) -> System.out.println("key: " + k + " value:" + v));
	}

}
