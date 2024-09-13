package org.tony.openhelloclient.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.tony.openhelloclient.service.HelloService;

@Slf4j
@Component
@RequiredArgsConstructor
public class HelloJob {

  @Qualifier("helloServiceDapr")
  private final HelloService helloService;

  @Scheduled(fixedRate = 10000)
  void job() {
    String message = helloService.getHello();
    log.info("Message: {}", message);
  }
}
