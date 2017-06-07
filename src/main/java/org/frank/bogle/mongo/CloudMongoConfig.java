package org.frank.bogle.mongo;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

import java.time.LocalDateTime;

import static java.util.Collections.singletonList;

@Profile("development")
@PropertySource("classpath:mongodev.properties")
@Configuration
public class CloudMongoConfig extends AbstractMongoConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(CloudMongoConfig.class);

    @Value("${mongo.dev.host}")
    private String host;

    @Value("${mongo.dev.port}")
    private Integer port;

    @Value("${mongo.dev.database}")
    private String database;

    @Value("${mongo.dev.username}")
    private String username;

    @Value("${mongo.dev.password}")
    private String password;

    @Override
    public String getDatabaseName() {
        return database;
    }

    @Override
    @Bean
    public Mongo mongo() throws Exception {
        logger.info("CloudMongoConfig method mongo() invoked: " + LocalDateTime.now());
        return new MongoClient(
                singletonList(new ServerAddress(host, port)),
                singletonList(MongoCredential.createCredential(username,
                        database, password.toCharArray()))
        );

    }



}
