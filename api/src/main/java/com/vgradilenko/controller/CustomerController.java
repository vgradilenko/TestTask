package com.vgradilenko.controller;

import com.vgradilenko.entity.Customer;
import com.vgradilenko.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/customers")
    public List<Customer> getAll() {
        return customerService.getAllCustomers();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/customer/{id}")
    public Customer findOne(@PathVariable Long id) {
        return customerService.findById(id);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/customer/new")
    public ResponseEntity<String> create(@RequestBody Customer customer) {
        customerService.createCustomer(customer);
        return new ResponseEntity<>("Customer saved successfully", HttpStatus.OK);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/customer/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Customer customer) {
        customerService.edit(id, customer);
        return new ResponseEntity<>("Customer updated successfully", HttpStatus.OK);
    }

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/customer/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        customerService.deleteById(id);
        return new ResponseEntity<>("Customer deleted successfully", HttpStatus.OK);
    }
}
