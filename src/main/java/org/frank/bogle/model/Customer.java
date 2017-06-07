package org.frank.bogle.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.hateoas.ResourceSupport;

import java.util.Objects;

@Document
public class Customer extends ResourceSupport {



    public enum Category {
        NA, GOLD, SILVER, BRONZE
    };

    public enum LifeExperience {
        NA, CHILD, YOUTH, ADULT, ANCIENT
    };

    @Id
    private String mongoId;

    private Long customerId;
    private Integer age;
    private String name;
    private String email;
    private Category category = Category.NA;
    private LifeExperience lifeExperience = LifeExperience.NA;

    public Customer() {
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Category getCategory() {
        return category;
    }

    public LifeExperience getLifeExperience() {
        return lifeExperience;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setLifeExperience(LifeExperience lifeExperience) { this.lifeExperience = lifeExperience; }

    public String getMongoId() {
        return mongoId;
    }

    public void setMongoId(String mongoId) {
        this.mongoId = mongoId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.customerId);
        hash = 67 * hash + Objects.hashCode(this.age);
        hash = 67 * hash + Objects.hashCode(this.email);
        hash = 67 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Customer other = (Customer) obj;
        if (!Objects.equals(this.customerId, other.customerId)) {
            return false;
        }
        if (!Objects.equals(this.age, other.age)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "mongoId='" + mongoId + '\'' +
                ", customerId=" + customerId +
                ", age=" + age +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", category=" + category +
                ", lifeExperience=" + lifeExperience +
                '}';
    }
}
