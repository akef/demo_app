package com.demo.jumia.controller;

import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.jumia.dto.CustomerResponse;
import com.demo.jumia.exception.InvalidException;
import com.demo.jumia.exception.NoDataException;
import com.demo.jumia.service.CustomerService;

@RestController
@RequestMapping("/customer")
public class JumiaController {
	private static final Logger logger = LoggerFactory.getLogger(JumiaController.class);
	private final CustomerService customerService;

	@Autowired
	public JumiaController(CustomerService customerService) {
		super();
		this.customerService = customerService;
	}

	@GetMapping(value = "/country_code/{countryCode}/state/{state}/page_number/{pageNo}/page_siza/{pageSize}")
	public ResponseEntity<?> inquiry(@PathVariable String countryCode, @PathVariable String state,
			@PathVariable Integer pageNo, @PathVariable Integer pageSize)
			throws InvalidException, NoDataException {

		logger.info("Enter getCustomers Method of CustomerController Class, with parameters countryCode:" + countryCode
				+ " and state:" + state);
		List<CustomerResponse> customerResponseList = customerService.getCustomerByCountrycodeAndState(countryCode,
				state, pageNo, pageSize);
		logger.info("Exit getCustomers Method of CustomerController Class");
		return ResponseEntity.ok(customerResponseList);
	}
}
