package org.frank.bogle.repository;

import org.frank.bogle.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {

    public Customer findCustomerByMongoId(String mongoId);

}
