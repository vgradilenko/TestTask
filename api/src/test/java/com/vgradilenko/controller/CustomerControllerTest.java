package com.vgradilenko.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vgradilenko.entity.Customer;
import com.vgradilenko.service.CustomerService;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class CustomerControllerTest {
    private MockMvc mockMvc;

    @Mock
    private CustomerService service;

    @InjectMocks
    private CustomerController controller;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
    }

    @Test
    public void test_get_all_customers() throws Exception {
        List<Customer> customers = Arrays.asList(
                new Customer(1L, "Bob", "Black", "+380971234567"),
                new Customer(2L, "Joy", "Grey", "+380971234567")
        );

        when(service.getAllCustomers()).thenReturn(customers);

        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Bob")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Joy")));

        verify(service, times(1)).getAllCustomers();
        verifyNoMoreInteractions(service);
    }

    @Test
    public void test_get_one_by_id() throws Exception {
        Customer customer = new Customer(1L, "Bob", "Black", "+380971234567");

        when(service.findById(1L)).thenReturn(customer);

        mockMvc.perform(get("/customer/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Bob")));

        verify(service, times(1)).findById(1L);
        verifyNoMoreInteractions(service);
    }


    @Test
    public void test_get_by_id_fail_204_no_content() throws Exception {

        when(service.findById(1L)).thenReturn(null);

        mockMvc.perform(get("/customer/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(service, times(1)).findById(1L);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void test_create_new_customer() throws Exception {
        Customer customer = new Customer(1L, "Bob", "Black", "+380971234567");

        mockMvc.perform(
                post("/customer/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(customer)))
                .andExpect(status().isCreated());

        verify(service, times(1)).createCustomer(customer);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void test_delete_customer_by_id() throws Exception {
        Customer customer = new Customer(1L, "Bob", "Black", "+380971234567");

        when(service.findById(customer.getId())).thenReturn(customer);
        doNothing().when(service).deleteById(customer.getId());

        mockMvc.perform(
                delete("/customer/{id}", customer.getId()))
                .andExpect(status().isOk());

        verify(service, times(1)).deleteById(customer.getId());
        verifyNoMoreInteractions(service);
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}