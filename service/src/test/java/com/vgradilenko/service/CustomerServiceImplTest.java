package com.vgradilenko.service;

import com.vgradilenko.entity.Customer;
import com.vgradilenko.repo.CustomerRepo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceImplTest {

    @Mock
    private CustomerRepo repo;

    @InjectMocks
    private CustomerServiceImpl service;

    @Test
    public void test_get_all_customers() throws Exception {
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer(1L, "Bob", "Black", "+380971324321"));
        customers.add(new Customer(2L, "Joy", "Grey", "+380971324999"));

        when(repo.findAll()).thenReturn(customers);

        List<Customer> retrivedCustomers = service.getAllCustomers();

        Assert.assertNotNull(retrivedCustomers);
        Assert.assertEquals(2, retrivedCustomers.size());
        Assert.assertEquals(customers, retrivedCustomers);
    }

    @Test
    public void test_to_find_customer_by_id() throws Exception {
        Customer customer = new Customer(1L, "Bob", "Black", "+380971324321");

        when(repo.findOne(1L)).thenReturn(customer);

        Customer retrivedCustomer = service.findById(1L);

        Assert.assertNotNull(retrivedCustomer);
        Assert.assertEquals(customer, retrivedCustomer);
    }

    @Test
    public void test_create_new_customer() throws Exception {
        Customer customer = new Customer(1L, "Bob", "Black", "+380971324321");

        when(repo.saveAndFlush(any(Customer.class))).thenReturn(customer);
        when(repo.findOne(any(Long.class))).thenReturn(customer);

        Customer savedCustomer = service.createCustomer(new Customer());

        Assert.assertEquals(customer, service.findById(savedCustomer.getId()));
        Assert.assertNotNull(service.findById(savedCustomer.getId()));
    }

    @Test
    public void test_update_current_customer() throws Exception {
        Customer customer = new Customer(1L, "Bob", "Black", "+380971324321");
        Customer dbCustomer = new Customer(1L, "Joy", "Grey", "+380971324321");

        when(repo.findOne(any(Long.class))).thenReturn(customer);
        when(repo.saveAndFlush(any(Customer.class))).thenReturn(dbCustomer);

        Customer retrivedCustomer = service.findById(1L);

        retrivedCustomer.setName("Joy");
        retrivedCustomer.setSurname("Grey");

        Customer updatedCustomer = service.edit(1L, retrivedCustomer);

        Assert.assertNotNull(updatedCustomer);
        Assert.assertEquals(dbCustomer, updatedCustomer);
        Assert.assertEquals(dbCustomer.getId(), updatedCustomer.getId());
        Assert.assertEquals(dbCustomer.getName(), updatedCustomer.getName());
        Assert.assertEquals(dbCustomer.getSurname(), updatedCustomer.getSurname());
    }
}