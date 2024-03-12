package com.muchiri.applicationevents;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.muchiri.applicationevents.service.OrderManagement;

@SpringBootApplication
@EnableAsync
@Controller
@ResponseBody
public class ApplicationEventsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationEventsApplication.class, args);
	}

	@Autowired
	private OrderManagement orderManagement;

	@GetMapping("/order/{name}")
	public void Order(@PathVariable("name") String orderName) {
		orderManagement.completeOrder(orderName);
	}

}
