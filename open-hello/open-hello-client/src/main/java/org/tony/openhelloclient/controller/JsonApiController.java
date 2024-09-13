package org.tony.openhelloclient.controller;

import io.dapr.client.DaprClientBuilder;
import io.dapr.client.domain.InvokeMethodRequest;
import io.dapr.utils.TypeRef;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api")
public class JsonApiController {

  @GetMapping("/products")
  public Mono<Object> parse(HttpServletRequest request) {
    request.getRequestURI();
    var invokeRequest = new InvokeMethodRequest("parser", "parseQuery");
    invokeRequest.setBody(request.getRequestURI());

    try (var client = (new DaprClientBuilder()).build()) {
      return client.invokeMethod(invokeRequest, new TypeRef<Object>() {
          });
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public record Request(String resourceType, String identifier, boolean relationship, String relationshipType, Query queryData) {}

  public record Query(List<String> include, List<String> fields, List<String> sort, Object page, Filter filter) {}

  public record Filter(Map<String, String> like, Map<String, String> not, Map<String, String> lt, Map<String, String> lte, Map<String, String> gt, Map<String, String> gte) extends
      HashMap<String, String> {}
}
