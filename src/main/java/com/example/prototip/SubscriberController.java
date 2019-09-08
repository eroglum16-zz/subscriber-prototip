package com.example.prototip;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.CacheManager;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SubscriberController {
	
	@Autowired
	SubscriberService subscriberService;
	
	@Autowired
	LogManager logManager;
	
	@GetMapping("/getAllSubscribers")
	public Collection<Subscriber> getAll(){
		logManager.log("getAllSubscribers");
		return subscriberService.getAllSubscribers();
	}
	
	@GetMapping("/getSubscriberById/{id}")
	public Subscriber getOne(@PathVariable Long id){
		String params = "id="+id;
		logManager.log("getSubscriberById",params);
		return subscriberService.getSubscriberById(id);
	}
	
	@PostMapping("/subscriber")
	public Subscriber createOne(@RequestBody Subscriber newSubscriber){
		Subscriber savedSubscriber = subscriberService.createSubscriber(newSubscriber);
		String params = "id="+savedSubscriber.getId()+",name="+savedSubscriber.getName()+",msisdn="+savedSubscriber.getMsisdn();
		logManager.log("subscriber",logManager.METHOD_POST,params);
		return savedSubscriber;
	}
	
	@PutMapping("/subscriber/{id}")
	Subscriber updateOne(@RequestBody Subscriber newSubscriber, @PathVariable Long id) {
		Subscriber updatedSubscriber = subscriberService.updateSubscriber(newSubscriber, id);
		String params = "id="+updatedSubscriber.getId()+",name="+updatedSubscriber.getName()+",msisdn="+updatedSubscriber.getMsisdn();
		logManager.log("subscriber",logManager.METHOD_PUT,params);
		return updatedSubscriber;
	}
	
	@DeleteMapping("/subscriber/{id}")
	Subscriber deleteOne(@PathVariable Long id) {
		String params = "id="+id;
		logManager.log("subscriber",logManager.METHOD_DELETE,params);
		return subscriberService.deleteSubscriber(id);
	}
	
	//Non-REST Actions
	
	ConfigurationManager configurationManager = new ConfigurationManager();
	
	String dataFilePath = configurationManager.getDataFilePath();
	
	DataController dataController = new DataController();
	
	@EventListener(ApplicationReadyEvent.class)
	public void onSpringLaunch() {
	    
	    ArrayList<Subscriber> subscriberList = dataController.readData(dataFilePath);
		
	    if(subscriberList.size()!=0) {
	    	for(Subscriber subscriber : subscriberList) {
				subscriberService.createSubscriber(subscriber);
			}	
	    }
	}
	
	@Scheduled(cron="${cronExpression}")
	public void cronJob() {
		dataController.writeData(subscriberService.getAllSubscribers(),dataFilePath);
	}
}
