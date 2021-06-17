package org.example;

import org.example.client.request.ThirdPartyClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(clients = ThirdPartyClient.class)
public class Application {
  public static void main(final String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
