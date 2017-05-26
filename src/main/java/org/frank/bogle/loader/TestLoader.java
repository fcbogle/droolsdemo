package org.frank.bogle.loader;

import org.drools.devguide.eshop.model.Item;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Created by frankbogle on 25/05/2017.
 */
@Service
public class TestLoader {

    private final KieContainer kieContainer;
    private final static Logger logger = LoggerFactory.getLogger(TestLoader.class);

    @Autowired
    public TestLoader(KieContainer kieContainer) {
        this.kieContainer = kieContainer;
    }

    @PostConstruct
    public void init(){
        logger.info("INFO ::: TestLoader init() @PostConstruct init method invoked now: " + LocalDateTime.now());

        try{

            ArrayList<Item> franksItems = new ArrayList<>();
            KieSession kSession =  kieContainer.newKieSession();

            Item item = new Item("A", 123.0,234.0);
            Item item1 = new Item("B", 223.0,234.0);
            Item item2 = new Item("C", 23.0,234.0);
            franksItems.add(item);
            franksItems.add(item1);
            franksItems.add(item2);

            int fired = 0;

            for (Item i : franksItems) {
                System.out.println( "Item Category: " + item.getCategory());
                kSession.insert(i);
                fired = kSession.fireAllRules();
            }

            System.out.println( "Number of Rules executed = " + fired );
            System.out.println( "Item Category: " + item.getCategory());

        } catch(Exception e) {
            e.printStackTrace();

        }
    }





}
