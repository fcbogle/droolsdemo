package org.frank.bogle.controller;

import org.frank.bogle.model.Customer;
import org.frank.bogle.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@ExposesResourceFor(CustomerController.class)
public class CustomerController {

    private final CustomerService customerService;
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @RequestMapping(value = "/customer", method = RequestMethod.POST)
    public @ResponseBody Customer saveNewCustomer(@RequestBody Customer customer, HttpServletRequest request){
        logger.info("CustomerController method saveNewCustomer() invoked: " + LocalDateTime.now());
        this.customerService.createCustomer(customer);
        return customer;
    }

    @RequestMapping(value = "/customer/{mongoId}", method = RequestMethod.GET)
    public @ResponseBody Customer getSingleCustomer(@PathVariable String mongoId) {
        logger.info("CustomerController method getSingleCustomer() invoked: " + LocalDateTime.now());
        Customer customer = this.customerService.findCustomerByMongoId(mongoId);
        return customer;
    }

    @RequestMapping(value = "/customers", method = RequestMethod.GET)
    public @ResponseBody List<Customer> getAllCustomers() {
        logger.info("CustomerController method getAllCustomers() invoked: " + LocalDateTime.now());
        List<Customer> customers = this.customerService.findAllCustomers();

        for(Customer c : customers) {
            Link selfLink = linkTo(CustomerController.class).slash(c.getMongoId()).withSelfRel();
            c.add(selfLink);
        }

        return customers;
    }


}
