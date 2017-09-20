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
    public ResponseEntity<List<Customer>> getAll() {
        List<Customer> customers = customerService.getAllCustomers();

        if (customers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/customer/{id}")
    public ResponseEntity<Customer> findOne(@PathVariable Long id) {
        Customer customer = customerService.findById(id);

        if (customer == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/customer/new")
    public ResponseEntity<Customer> create(@RequestBody Customer customer) {
        customerService.createCustomer(customer);
        return new ResponseEntity<>(customer, HttpStatus.CREATED);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/customer/{id}")
    public ResponseEntity<Customer> update(@PathVariable Long id, @RequestBody Customer customer) {
        Customer currentCustomer = customerService.findById(id);

        if (currentCustomer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        customerService.edit(id, customer);
        return new ResponseEntity<>(currentCustomer, HttpStatus.OK);
    }

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/customer/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        Customer customer = customerService.findById(id);

        if (customer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        customerService.deleteById(id);
        return new ResponseEntity<>("Customer deleted successfully", HttpStatus.OK);
    }
}
