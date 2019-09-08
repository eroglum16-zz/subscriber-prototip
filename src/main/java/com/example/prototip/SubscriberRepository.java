package com.example.prototip;

import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.prototip.Subscriber;

public interface SubscriberRepository extends JpaRepository<Subscriber, Long>{
	
	Collection<Subscriber> findById(long id);
	
}