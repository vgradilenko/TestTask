package com.vgradilenko.service;

import com.vgradilenko.entity.Customer;
import com.vgradilenko.repo.CustomerRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepo repo;

    @Autowired
    public CustomerServiceImpl(CustomerRepo repo) {
        this.repo = repo;
    }


    @Override
    public List<Customer> getAllCustomers() {
        return repo.findAll();
    }

    @Override
    public Customer findById(Long id) {
        return repo.findOne(id);
    }

    @Override
    public Customer createCustomer(Customer customer) {
        return repo.saveAndFlush(customer);
    }

    @Override
    public Customer edit(Long id, Customer customer) {
        Customer storedCustomer = findById(id);

        storedCustomer.setName(customer.getName());
        storedCustomer.setSurname(customer.getSurname());
        storedCustomer.setPhone(customer.getPhone());

        return repo.saveAndFlush(storedCustomer);
    }

    @Override
    public void deleteById(Long id) {
         repo.delete(id);
    }
}
