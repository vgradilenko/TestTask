package com.vgradilenko.service;

import com.vgradilenko.entity.Customer;
import com.vgradilenko.repo.CustomerRepo;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceImplTest {

    @Mock
    private CustomerRepo repo;

    @InjectMocks
    private CustomerServiceImpl service;
    private Customer customer;
    private List<Customer> customers;

    @Before
    public void setUp() throws Exception {
        customer = new Customer(10L, "testName", "testSurname", "phone");

        customers = new ArrayList<>();
        customers.add(new Customer(1L, "Bob", "Black", "+380971324321"));
        customers.add(new Customer(2L, "Joy", "Grey", "+380971324999"));

        when(repo.findAll()).thenReturn(customers);
        when(repo.findOne(1L)).thenReturn(customers.get(0));
        when(repo.saveAndFlush(any(Customer.class))).thenReturn(customer);
    }

    @After
    public void tearDown() throws Exception {
        service = null;
        customers = null;
        customer = null;
    }

    @Test
    public void getAllCustomers() throws Exception {
        Assert.assertNotNull(service.getAllCustomers());
        Assert.assertEquals(2, service.getAllCustomers().size());
    }

    @Test
    public void findById() throws Exception {
        Assert.assertNotNull(service.findById(1L));
        Assert.assertNotEquals(customer.getName(), service.findById(1L));
        Assert.assertEquals(new Customer(1L, "Bob", "Black", "+380971324321"), service.findById(1L));
    }

    @Test
    public void createCustomer() throws Exception {
        Assert.assertNotNull(service.createCustomer(customer));
        customers.add(customer);
        Assert.assertEquals(service.createCustomer(customer), customers.get(2));
        customers.remove(customer);
    }
}