package com.demo.jumia.service;

import java.util.List;


import com.demo.jumia.dto.CustomerResponse;
import com.demo.jumia.exception.InvalidException;
import com.demo.jumia.exception.NoDataException;


public interface CustomerService {

	List<CustomerResponse> getCustomerByCountrycodeAndState(String countryCode, String state,Integer pageNo,Integer pageSize) throws InvalidException, NoDataException;

}
