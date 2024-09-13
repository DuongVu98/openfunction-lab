package org.tony.openhelloclient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import org.tony.openhelloclient.service.HelloService;

@Slf4j
@EnableScheduling
@SpringBootApplication
@RequiredArgsConstructor
public class OpenHelloClientApplication {

  public static void main(String[] args) {
    SpringApplication.run(OpenHelloClientApplication.class, args);
  }

  @Value("${host.url}")
  private String host;

  @Bean
  WebClient webClient() {
    return WebClient.builder()
        .baseUrl(host)
        .build();
  }

  @Bean
  HttpServiceProxyFactory createProxyFactory(WebClient webClient){
    return HttpServiceProxyFactory.builderFor(WebClientAdapter.create(webClient)).build();
  }

  @Bean("helloServiceHttp")
  public HelloService helloService(HttpServiceProxyFactory proxyFactory){
    return proxyFactory.createClient(HelloService.class);
  }
}
