package com.manning.sbia.ch07;

import java.io.Serializable;

/**
 * @author bazoud
 * @version $Id$
 */
public class Product implements Serializable {
    private String id;
    private String name;
    private String description;
    private float price;
    private String transactionType;

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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    @Override
    public String toString() {
        return "Product [id=" + id + ", name=" + name + "]";
    }


}
