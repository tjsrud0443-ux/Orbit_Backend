package com.study.app.domains.checkoutRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/checkoutRQ")
public class CheckoutRequestController {
	
	@Autowired
	private CheckoutRequestService checkoutServ;
}
