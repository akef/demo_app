package com.demo.jumia.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.demo.jumia.dao.CustomerDao;
import com.demo.jumia.dto.CustomerResponse;
import com.demo.jumia.exception.InvalidException;
import com.demo.jumia.exception.NoDataException;
import com.demo.jumia.model.Customer;
import com.demo.jumia.model.enumeration.State;
import com.demo.jumia.service.CustomerService;

@Service
@Primary
public class CustomerServiceImpl implements CustomerService {
	private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);
    private final CustomerDao customerDao;

	private Map<String, String> countries;
	
	
	@Autowired
	public CustomerServiceImpl(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	@Override
	public List<CustomerResponse> getCustomerByCountrycodeAndState(String countryCode, String state, Integer pageNo,
			Integer pageSize) throws InvalidException, NoDataException {
		logger.info("inquiry () : Calling getCustomer method with country code : [{}], and state : [{}] ...........",
				countryCode, state);
		validateCountryCodeAndState(countryCode, state);
		List<CustomerResponse> customerList = new ArrayList<CustomerResponse>();

		logger.info("inquiry () : Calling getCustomer method with country code : [{}], and state : [{}] ...........",
				countryCode, state);
		Pageable paging = PageRequest.of(pageNo, pageSize);
		Page<Customer> customerPage = customerDao.findAll(paging);
		if (customerPage.hasContent()) {
			createCustomerListResponse(customerPage, customerList, countryCode, state);
			return customerList;
		} else {
			logger.error("no data found for this country code (" + countryCode + ") and state (" + state + ")");

			throw new NoDataException("no data found for this country code (" + countryCode + ")");
		}

	}

	private void validateCountryCodeAndState(String countryCode, String state) throws InvalidException {
		logger.info("validate country code :[{}],and state :[{}]");
		if (countryCode == null || !getCountries().containsKey(countryCode)) {
			logger.error("Invalid country code (" + countryCode + ")");
			throw new InvalidException("country code (" + countryCode + ") not found");
		}
		if (state == null || !(state.equals(State.VALID.getCode()) || state.equals(State.NOT_VALID.getCode())
				|| state.equals(State.ALL.getCode()))) {
			logger.error("state can't be " + state + " it must be valid or notValid or all");
			;
			throw new InvalidException("state (" + state + ") not valid");
		}
	}

	private void createCustomerListResponse(Page<Customer> customerPage, List<CustomerResponse> customerList,
			String countryCode, String state) {
		for (Customer customer : customerPage.toList()) {
			CustomerResponse customerResponse = new CustomerResponse();
			customerResponse.setCountryCode("+"+customer.getPhone().split(" ")[0].replace("(", "").replace(")", ""));
			if (customerResponse.getCountryCode().equalsIgnoreCase(countryCode)
					|| countryCode.equalsIgnoreCase("all")) {
				customerResponse.setState(checkPhoneNumberWithState(customer.getPhone()));
				if (customerResponse.getState().equals(state)
						||state.equals(State.ALL.getCode())) {
					customerResponse.setName(customer.getName());
					customerResponse.setPhone(customer.getPhone().split(" ")[1]);
					customerResponse.setCountry(getCountries().get(customerResponse.getCountryCode()));
					customerList.add(customerResponse);
				} else {
					continue;
				}
			} else {
				continue;
			}
		}
	}

	public Map<String, String> getCountries() {
		countries = new HashMap<String, String>();
		countries.put("all", "all");
		countries.put("+237", "Cameroon");
		countries.put("+251", "Ethiopia");
		countries.put("+212", "Morocco");
		countries.put("+258", "Mozambique");
		countries.put("+256", "Uganda");
		return countries;
	}

	private String checkPhoneNumberWithState(String phoneNumber) {
		Pattern cameroonRegex = Pattern.compile("\\(237\\)\\ ?[2368]\\d{7,8}$");
		Pattern ethiopiaRegex = Pattern.compile("\\(251\\)\\ ?[1-59]\\d{8}$");
		Pattern moroccoRegex = Pattern.compile("\\(212\\)\\ ?[5-9]\\d{8}$");
		Pattern mozambiqueRegex = Pattern.compile("\\(258\\)\\ ?[28]\\d{7,8}$");
		Pattern ugandaRegex = Pattern.compile("\\(256\\)\\ ?\\d{9}$");
		if (cameroonRegex.matcher(phoneNumber).matches() || ethiopiaRegex.matcher(phoneNumber).matches()
				|| moroccoRegex.matcher(phoneNumber).matches() || mozambiqueRegex.matcher(phoneNumber).matches()
				|| moroccoRegex.matcher(phoneNumber).matches()) {
			return State.VALID.getCode();
		}
		return State.NOT_VALID.getCode();
	}

}
