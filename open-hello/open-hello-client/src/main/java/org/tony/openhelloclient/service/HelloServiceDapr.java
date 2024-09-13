package org.tony.openhelloclient.service;

import io.dapr.client.DaprClientBuilder;
import io.dapr.client.domain.HttpExtension;
import io.dapr.client.domain.InvokeMethodRequest;
import io.dapr.utils.TypeRef;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service("helloServiceDapr")
public class HelloServiceDapr implements HelloService {

  @Override
  public String getHello() {

    var request = new InvokeMethodRequest("my-hello-2-entry", "hello");
    request.setHttpExtension(HttpExtension.GET);
    request.setContentType("application/json");

    try (var client = (new DaprClientBuilder()).build()) {
      return client.invokeMethod(request, new TypeRef<byte[]>() {
          })
          .map(String::new)
          .log()
          .block();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public String getPerson() {
    return "";
  }
}
