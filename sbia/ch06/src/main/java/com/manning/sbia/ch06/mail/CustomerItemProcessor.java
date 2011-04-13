/**
 * Copyright (c) 2010 FullSIX
 *
 * $Id$
 */
package com.manning.sbia.ch06.mail;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.mail.SimpleMailMessage;

/**
 * @author bazoud
 */
public class CustomerItemProcessor implements ItemProcessor<Customer, SimpleMailMessage> {
  @Override
  public SimpleMailMessage process(Customer item) throws Exception {
    SimpleMailMessage msg = new SimpleMailMessage();
    msg.setFrom("springbatchinaction@test.com");
    msg.setTo(item.getEmail());
    msg.setSubject("Welcome message !!");
    msg.setText("Hello " + item.getName());
    return msg;
  }
}
