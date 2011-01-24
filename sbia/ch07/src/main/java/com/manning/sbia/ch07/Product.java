package com.manning.sbia.ch07;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author bazoud
 */
public class Product implements Serializable {
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private String operation;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getOperation() {
        return operation;
    }
    
    public void setOperation(String operation) {
        this.operation = operation;
    }

    @Override
    public String toString() {
        return "Product [id=" + id + ", name=" + name + "]";
    }


}
