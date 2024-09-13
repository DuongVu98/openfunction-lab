package org.tony.openhelloclient.service;

import org.springframework.web.service.annotation.GetExchange;

public interface HelloService {

  @GetExchange("/hello")
  String getHello();

  @GetExchange("/person")
  String getPerson();
}
