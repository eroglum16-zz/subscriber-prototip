package com.example.prototip;

import java.io.RandomAccessFile;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class LogManager {
	
	public final int METHOD_POST = 0;
	public final int METHOD_GET = 1;
	public final int METHOD_PUT = 2;
	public final int METHOD_DELETE = 3;
	
	public final String[] methods = {"POST", "GET", "PUT", "DELETE"};
	
	RandomAccessFile logFile;
	
	public void log(String endpoint) {
		String line = "";
		line += getTimeAsString();
		line += " /" + endpoint;
		writeToFile(line);
	}
	
	public void log(String endpoint, int method, String params) {
		String line = "";
		line += getTimeAsString();
		line += " /" + endpoint;
		line += "[" + methods[method] + "] ";
		line += params;
		writeToFile(line);
	}
	
	public void log(String endpoint, String params) {
		String line = "";
		line += getTimeAsString();
		line += " /" + endpoint;
		line += " " + params;
		writeToFile(line);
	}
	
	private void writeToFile(String line) {
		try {
			logFile = new RandomAccessFile("src/main/resources/app.log","rw");

			logFile.seek(logFile.length());
			logFile.writeBytes(line+"\n");
				
		}catch(Exception e){
			System.out.println(e.getStackTrace());
		}
	}
	
	private String getTimeAsString() {
		String pattern = "dd.MM.yyyy HH:mm:ss";

		DateFormat df = new SimpleDateFormat(pattern);

		Date today = Calendar.getInstance().getTime();        
		
		String timeAsString = df.format(today);
		
		return timeAsString;
	}
	
}
