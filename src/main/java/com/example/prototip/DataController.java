package com.example.prototip;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

@Service
public class DataController {
	
	JSONParser jsonParser = new JSONParser();
	
	public ArrayList readData(String dataFilePath) {
		
		ArrayList<Subscriber> subscriberList = new ArrayList<Subscriber>();
	
		try(FileReader dataFile = new FileReader(dataFilePath)) {
			
			 Object obj = jsonParser.parse(dataFile);
			
			 JSONObject jsonObject = (JSONObject) obj;
			 
			 JSONArray subscriberObject = (JSONArray) jsonObject.get("subscribers");

			 subscriberObject.forEach( subscriberJson -> {
				 subscriberList.add(createSubscriberEntity( (JSONObject) subscriberJson ));
			 } );
			 
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}	
		
		return subscriberList;
	}
	
	public void writeData(Collection<Subscriber> subscriberCollection, String dataFilePath) {
		
		JSONArray subscriberList = new JSONArray();
		JSONObject subscriberJson;
		String id;
		
		for(Subscriber subscriber : subscriberCollection) {
			subscriberJson = new JSONObject();
			id = String.valueOf(subscriber.getId());
			subscriberJson.put("id", id);
			subscriberJson.put("name", subscriber.getName());
			subscriberJson.put("msisdn", subscriber.getMsisdn());
			subscriberList.add(subscriberJson);
		}
		
		JSONObject data = new JSONObject();
		data.put("subscribers", subscriberList);
		
        try (FileWriter file = new FileWriter(dataFilePath)) {
 
            file.write(data.toJSONString());
            file.flush();
 
        } catch (IOException e) {
            e.printStackTrace();
        }
		
	}
	
	private Subscriber createSubscriberEntity(JSONObject subscriberJson){
		
		Subscriber subscriber = new Subscriber();
		
		String id = (String) subscriberJson.get("id");
		subscriber.setId(Long.valueOf(id));
		
		String name = (String) subscriberJson.get("name");   
		subscriber.setName(name);
		
		String msisdn = (String) subscriberJson.get("msisdn");
		subscriber.setMsisdn(msisdn);
		
		return subscriber;
		
	}
	
}
