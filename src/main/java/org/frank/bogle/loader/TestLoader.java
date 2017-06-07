package org.frank.bogle.loader;

import org.frank.bogle.model.*;
import org.frank.bogle.service.CustomerService;
import org.kie.api.builder.Results;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.logger.KnowledgeRuntimeLogger;
import org.kie.internal.logger.KnowledgeRuntimeLoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by frankbogle on 25/05/2017.
 */
@Service
public class TestLoader {

    private final KieContainer kieContainer;
    private final CustomerService customerService;
    private final static Logger logger = LoggerFactory.getLogger(TestLoader.class);

    @Autowired
    public TestLoader(KieContainer kieContainer, CustomerService customerService) {
        this.kieContainer = kieContainer;
        this.customerService = customerService;
    }

    @PostConstruct
    public void init(){
        logger.info("INFO ::: TestLoader init() @PostConstruct init method invoked now: " + LocalDateTime.now());

        try {

            KieSession kSession =  kieContainer.newKieSession("ClassificationSession");
            kSession.setGlobal("logger", logger);

            Results results = kieContainer.verify();

            results.getMessages().stream()
                    .forEach((message) -> {
                    logger.info("KieContainer Message: ( "+message.getLevel()+" ): " + message.getText());
                    });

            int fired = 0;
            int orderFired = 0;

            List<OrderLine> orderLines = new ArrayList<OrderLine>();

            Discount discount = new Discount();
            discount.setPercentage(17.5);

            Item item1 = new Item("A", 23.0,234.0);
            Item item2 = new Item("B", 433.0,334.0);
            Item item3 = new Item("C", 123.0,434.0);
            Item item4 = new Item("D", 163.0,334.0);
            Item item5 = new Item("E", 223.0,434.0);
            Item item6 = new Item("F", 623.0,534.0);

            OrderLine orderLine1 = new OrderLine();
            orderLine1.setItem(item1);
            orderLine1.setQuantity(4);
            orderLines.add(orderLine1);

            OrderLine orderLine2 = new OrderLine();
            orderLine2.setItem(item2);
            orderLine2.setQuantity(5);
            orderLines.add(orderLine2);

            OrderLine orderLine3 = new OrderLine();
            orderLine3.setItem(item3);
            orderLine3.setQuantity(7);
            orderLines.add(orderLine3);

            OrderLine orderLine4 = new OrderLine();
            orderLine4.setItem(item4);
            orderLine4.setQuantity(76);
            orderLines.add(orderLine4);

            OrderLine orderLine5 = new OrderLine();
            orderLine5.setItem(item5);
            orderLine5.setQuantity(16);
            orderLines.add(orderLine5);

            OrderLine orderLine6 = new OrderLine();
            orderLine6.setItem(item6);
            orderLine6.setQuantity(34);
            orderLines.add(orderLine6);

            Customer customer = new Customer();
            customer.setAge(47);
            customer.setCustomerId(123L);
            customer.setEmail("fcbogle@gmail.com");
            customer.setName("Frank Bogle");
            customer.setCategory(Customer.Category.NA);

            Order order = new Order();
            order.setCustomer(customer);
            order.setDate(new Date());
            order.setState(OrderState.PENDING);
            order.setOrderId(456L);
            order.setItems(orderLines);
            order.setDiscount(discount);
            order.setState(OrderState.PENDING);


            for (OrderLine o : orderLines) {
                kSession.insert(o.getItem());
            }

            kSession.insert(order);
            kSession.insert(order.getCustomer());

            fired = kSession.fireAllRules();

            logger.info("Processed Order into Rules Processing: " + order.toString());
            logger.info("OrderLine Items: " + order.getOrderLines().toString());
            logger.info("Number of Fired Rules is : " + fired);


        } catch (Exception e) {
            e.printStackTrace();
        }

        try {

            this.customerService.deleteAll();

            Customer customer1 = new Customer();
            customer1.setName("David Jones");
            customer1.setEmail("dave.jones@msn.com");
            customer1.setCustomerId(12345L);
            customer1.setAge(34);

            this.customerService.createCustomer(customer1);

            Customer customer2 = new Customer();
            customer2.setName("Peter Smith");
            customer2.setEmail("peter.smith@msn.com");
            customer2.setCustomerId(54321L);
            customer2.setAge(48);

            this.customerService.createCustomer(customer2);

            Customer customer3 = new Customer();
            customer3.setName("Emma Charles");
            customer3.setEmail("emma@charles.com");
            customer3.setCustomerId(12321L);
            customer3.setAge(50);

            this.customerService.createCustomer(customer3);




        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    @PreDestroy
    public void destroy() {

    }





}
