package com.vgradilenko.service;

import com.vgradilenko.entity.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> getAllCustomers();

    Customer findById(Long id);

    Customer createCustomer(Customer customer);

    Customer edit(Long id, Customer customer);

    void deleteById(Long id);
}
