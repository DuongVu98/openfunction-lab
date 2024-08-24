package org.tony.openhelloclient.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.tony.openhelloclient.OpenHelloClientApplication.HelloService;

@Slf4j
@Component
@RequiredArgsConstructor
public class HelloJob {

  private final HelloService helloService;

  @Scheduled(fixedRate = 3000)
  void job() {
    String message = helloService.getPerson();
    log.info("Message: {}", message);
  }
}
