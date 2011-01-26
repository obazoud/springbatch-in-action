/**
 * 
 */
package com.manning.sbia.ch07.advanced;


import org.springframework.batch.support.annotation.Classifier;

import com.manning.sbia.ch07.Product;

/**
 * @author bazoud
 * 
 */
public class ProductRouterClassifier {
    @Classifier
    public String classify(Product classifiable) {
        return classifiable.getOperation();
    }
}
