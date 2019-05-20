package com.exeter.sender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.exeter.pojos.ProductOrder;

@RestController
public class SendController {

	@Autowired
	ArtemisPublisher pub;
	
	@GetMapping("/send/{message}")
	public String sendMessage(@PathVariable("message") String message) {
		pub.sendMessage(message);
		return "Message sent!";
	}
	
	@GetMapping("/sendBytes/{message}")
	public String sendBytesMessage(@PathVariable("message") String message) {
		pub.sendBytesMessage(message);
		return "Message sent!";
	}
	
	@PostMapping("/sendOrder")
	public String sendObjectMessage(@RequestBody ProductOrder newOrder) {
		pub.broadcastProductOrder(newOrder);
		return "Order placed!";
	}
}
