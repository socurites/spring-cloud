package com.socurites.cloud.client.error;

import feign.Response;
import feign.codec.ErrorDecoder;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class OrderClientErrorDecoder implements ErrorDecoder {
  @Override
  public Exception decode(String methodKey, Response response) {
    if ( HttpStatus.BAD_REQUEST == HttpStatus.valueOf(response.status())) {
    } else if ( HttpStatus.NOT_FOUND == HttpStatus.valueOf(response.status())) {
      if (methodKey.contains("findFalseByUserId")) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Order service %s not reachable", methodKey));
      }
    }
    return new Exception(response.reason());
  }
}
