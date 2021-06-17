package org.example.client.configuration;

import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.example.client.interceptor.ClientInterceptor;
import org.springframework.context.annotation.Bean;

@Slf4j
public class Configuration {

  @Bean
  public RequestInterceptor requestInterceptor() {
    return new ClientInterceptor();
  }


}