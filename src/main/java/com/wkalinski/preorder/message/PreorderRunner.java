package com.wkalinski.preorder.message;

import java.util.concurrent.TimeUnit;

import com.wkalinski.preorder.PreorderApplication;
import com.wkalinski.preorder.service.PreorderReceiver;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component
public class PreorderRunner implements CommandLineRunner {

  private final RabbitTemplate rabbitTemplate;
  private final PreorderReceiver receiver;

  public PreorderRunner(PreorderReceiver receiver, RabbitTemplate rabbitTemplate) {
    this.receiver = receiver;
    this.rabbitTemplate = rabbitTemplate;
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("Sending message...");
    rabbitTemplate.convertAndSend(PreorderApplication.topicExchangeName,
            "com.wkalinski.preorder",
            "Hello from RabbitMQ!");
    receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
  }

}

