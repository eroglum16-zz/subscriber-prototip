package com.example.prototip;

import java.util.Collection;

import javax.validation.ValidationException;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.example.prototip.Subscriber;
import com.example.prototip.SubscriberRepository;

@Service
public class SubscriberService {

	private final SubscriberRepository repository;
	
	SubscriberService(SubscriberRepository repository){
		this.repository = repository;
	}
	
	public Collection<Subscriber> getAllSubscribers(){
		return repository.findAll();
	}
	
	@Cacheable(value="subscriber", key="#id")
	public Subscriber getSubscriberById(long id){
		
		Subscriber subscriber = null;
		for (Subscriber s : repository.findById(id))
			subscriber = s;
		
		return subscriber;
	}
	
	@CacheEvict(value="subscriber", key="#p0.id")
	public Subscriber createSubscriber(Subscriber newSubscriber) {
		return repository.save(newSubscriber);
	}
	
	@CacheEvict(value="subscriber", key="#id")
	public Subscriber updateSubscriber(Subscriber newSubscriber, Long id) {
		return repository.findById(id)
			.map(subscriber -> {
		    	  subscriber.setName(newSubscriber.getName());
		    	  subscriber.setMsisdn(newSubscriber.getMsisdn());
		        return repository.save(subscriber);
		      })
		      .orElseGet(() -> {
		    	  throw new ValidationException("Subscriber Not Found");
		      });
	}
	
	@CacheEvict(value="subscriber", key="#id")
	public Subscriber deleteSubscriber(long id) {
		Subscriber subscriber = null;
		try {
			for (Subscriber s : repository.findById(id))
				subscriber = s;
		}catch(Exception e) {
			throw new ValidationException("Subscriber Not Found");
		}
		repository.deleteById(id);
		return subscriber;
	}
}
