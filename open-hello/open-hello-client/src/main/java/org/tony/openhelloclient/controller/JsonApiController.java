package org.tony.openhelloclient.controller;

import io.dapr.client.DaprClientBuilder;
import io.dapr.client.domain.HttpExtension;
import io.dapr.client.domain.InvokeMethodRequest;
import io.dapr.utils.TypeRef;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api")
public class JsonApiController {

  @GetMapping("/products")
  public ResponseEntity<Request> parse(HttpServletRequest request) {
    request.getRequestURI();
    log.info("Request URI: {}", request.getRequestURI());
    log.info("Request URL: {}", request.getRequestURL());

    String fullURL = request.getRequestURL().toString() + "?" + request.getQueryString();

    var invokeRequest = new InvokeMethodRequest("my-query-parser", "parse");
    invokeRequest.setBody("""
        {"url": "%s"}
        """.formatted(fullURL));
    invokeRequest.setHttpExtension(HttpExtension.GET);

    try (var client = (new DaprClientBuilder()).build()) {
      return client.invokeMethod(invokeRequest, new TypeRef<Request>() {
          })
          .log()
          .map(ResponseEntity::ok)
          .block();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public record Request(String resourceType, String identifier, boolean relationship,
                        String relationshipType, Query queryData) {
  }

  public record Query(List<String> include, Map<String, List<String>> fields, List<String> sort, Object page,
                      Filter filter) {
  }

  @Data
  @EqualsAndHashCode(callSuper = true)
  public static class Filter extends HashMap<String, String> {
    private Object like;
    private Object not;
    private Object lt;
    private Object lte;
    private Object gt;
    private Object gte;
  }
}
