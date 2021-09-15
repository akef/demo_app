package com.demo.jumia.controller;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.demo.jumia.dto.CustomerResponse;
import com.demo.jumia.exception.InvalidException;
import com.demo.jumia.exception.NoDataException;
import com.demo.jumia.service.CustomerService;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
public class CustomerControllerTest {
    @Mock
    private CustomerService customerService;
    @InjectMocks
    private JumiaController jumiaController;


    private CustomerResponse customerNumOneResponse;
    private CustomerResponse customerNumTwoResponse;
    private List<CustomerResponse> customerResponseList;
    private List<CustomerResponse> validCustomerResponseList;
    private List<CustomerResponse> invalidCustomerResponseList;

    @Before
    public void init() {
        customerNumOneResponse = new CustomerResponse();
        customerNumOneResponse.setCountry("Morocco");
        customerNumOneResponse.setState("Invalid");
        customerNumOneResponse.setCountryCode("+212");
        customerNumOneResponse.setPhone("6007989253");
        customerNumOneResponse.setName("Walid Hammadi");
        customerNumTwoResponse = new CustomerResponse();
        customerNumTwoResponse.setCountry("Morocco");
        customerNumTwoResponse.setState("Valid");
        customerNumTwoResponse.setCountryCode("+212");
        customerNumTwoResponse.setPhone("698054317");
        customerNumTwoResponse.setName("Yosaf Karrouch");
        customerResponseList = new ArrayList<>();
        customerResponseList.add(customerNumOneResponse);
        customerResponseList.add(customerNumTwoResponse);
        validCustomerResponseList = new ArrayList<>();
        validCustomerResponseList.add(customerNumTwoResponse);
        invalidCustomerResponseList = new ArrayList<>();
        invalidCustomerResponseList.add(customerNumOneResponse);
    }

    @Test
    public void getCustomersTest() throws NoDataException, InvalidException {
        Mockito.when(customerService.getCustomerByCountrycodeAndState("all", "all",0,10)).thenReturn(customerResponseList);
        ResponseEntity<?> responseEntity = jumiaController.inquiry("all", "all",0,10);
        Assert.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());
        Assert.assertEquals(responseEntity.getBody(), customerResponseList);
    }

    @Test
    public void getCustomersTest2() throws  NoDataException, InvalidException {
        Mockito.when(customerService.getCustomerByCountrycodeAndState("212","all",0,10)).thenReturn(customerResponseList);
        ResponseEntity<?> responseEntity = jumiaController.inquiry("212","all",0,10);
        Assert.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());
        Assert.assertEquals(responseEntity.getBody(), customerResponseList);
    }

    @Test
    public void getCustomersTest3() throws  NoDataException, InvalidException {
        Mockito.when(customerService.getCustomerByCountrycodeAndState("212","valid",0,10)).thenReturn(validCustomerResponseList);
        ResponseEntity<?> responseEntity =  jumiaController.inquiry("212","valid",0,10);
        Assert.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());
        Assert.assertEquals(responseEntity.getBody(), validCustomerResponseList);
    }

    @Test
    public void getCustomersTest4() throws NoDataException, InvalidException {
        Mockito.when(customerService.getCustomerByCountrycodeAndState("212", "notValid",0,10)).thenReturn(invalidCustomerResponseList);
        ResponseEntity<?> responseEntity = jumiaController.inquiry("212","notValid",0,10);
        Assert.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());
        Assert.assertEquals(responseEntity.getBody(), invalidCustomerResponseList);
    }

    @Test
    public void getCustomersTest5() throws  NoDataException, InvalidException  {
        Mockito.when(customerService.getCustomerByCountrycodeAndState("all", "valid",0,10)).thenReturn(validCustomerResponseList);
        ResponseEntity<?> responseEntity = jumiaController.inquiry("all", "valid",0,10);
        Assert.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());
        Assert.assertEquals(responseEntity.getBody(), validCustomerResponseList);
    }

    @Test
    public void getCustomersTest6() throws  NoDataException, InvalidException {
        Mockito.when(customerService.getCustomerByCountrycodeAndState("all", "notValid",0,10)).thenReturn(invalidCustomerResponseList);
        ResponseEntity<?> responseEntity = jumiaController.inquiry("all", "notValid",0,10);
        Assert.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());
        Assert.assertEquals(responseEntity.getBody(), invalidCustomerResponseList);
    }

    @Test(expected = InvalidException.class)
    public void getCustomersTest7() throws NoDataException, InvalidException {
        Mockito.when(customerService.getCustomerByCountrycodeAndState("allxx", "invalid",0,10)).thenThrow(InvalidException.class);
        ResponseEntity<?> responseEntity = jumiaController.inquiry("allxx", "invalid",0,10);
        Assert.assertTrue(responseEntity.getStatusCode().is4xxClientError());
        Assert.assertEquals(404, responseEntity.getStatusCodeValue());
    }

}