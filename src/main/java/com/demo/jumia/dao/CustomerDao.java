package com.demo.jumia.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.jumia.model.Customer;


@Repository
public interface CustomerDao extends JpaRepository<Customer, Integer> {

}
