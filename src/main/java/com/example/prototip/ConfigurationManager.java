package com.example.prototip;

import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

public class ConfigurationManager {
	
	private String dataFilePath;
	private String cronExpression;
	
	RandomAccessFile configurationFile;
	
	public ConfigurationManager() {
		this.readConfigurationFile();
	}

	public String getDataFilePath() {
		return dataFilePath;
	}

	public void setDataFilePath(String dataFilePath) {
		this.dataFilePath = dataFilePath;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
	
	public void readConfigurationFile() {
		try {
			
			configurationFile = new RandomAccessFile("src/main/resources/app.conf","r");
			
			String line, name, value;
			int beginIndex, endIndex;
			
			while(configurationFile.getFilePointer() < configurationFile.length()) {
				line = configurationFile.readLine();
				beginIndex = line.indexOf("={");
				endIndex = line.indexOf("}");
			
				name = line.substring(0,beginIndex);
				value = line.substring(beginIndex+2,endIndex);
				
				if(name.equals("data_file_path")) this.dataFilePath = value;
				if(name.equals("scheduler")) this.cronExpression = value;
			}
				
		}catch(Exception e){
			System.out.println(e.getStackTrace());
		}
	}
	
}
