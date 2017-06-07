package org.frank.bogle.service;

import org.frank.bogle.model.Customer;
import org.frank.bogle.repository.CustomerRepository;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final KieContainer kieContainer;
    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    public CustomerService(CustomerRepository customerRepository,
                           KieContainer kieContainer) {
        this.customerRepository = customerRepository;
        this.kieContainer = kieContainer;
    }

    public void createCustomer(Customer customer) {
        logger.info("CustomerService method createCustomer invoked: " + LocalDateTime.now());
        KieSession kSession =  kieContainer.newKieSession("ClassificationSession");
        kSession.setGlobal("logger", logger);
        kSession.insert(customer);
        kSession.fireAllRules();
        this.customerRepository.save(customer);
    }

    public List<Customer> findAllCustomers() {
        logger.info("CustomerService method findAllCustomers invoked: " + LocalDateTime.now());
        List<Customer> customers = this.customerRepository.findAll();
        return customers;
    }

    public Customer findCustomerByMongoId(String mongoId) {
        logger.info("CustomerService method findByCustomerId invoked: " + LocalDateTime.now());
        Customer customer = this.customerRepository.findCustomerByMongoId(mongoId);
        return customer;
    }

    public void deleteAll() {
        logger.info("CustomerService method deleteAll invoked: " + LocalDateTime.now());
        this.customerRepository.deleteAll();

    }
}
