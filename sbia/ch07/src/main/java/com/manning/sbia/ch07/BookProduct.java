package com.manning.sbia.ch07;

/**
 * @author bazoud
 * @version $Id$
 */
public class BookProduct extends Product {
    private String publisher;

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublisher() {
        return this.publisher;
    }
}
