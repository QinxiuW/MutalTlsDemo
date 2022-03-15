package com.example.client.controller;

import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * ClientController.
 *
 * @Description ClientController
 * @Date 15/03/2022 12:13
 * @Created by Qinxiu Wang
 */

@RestController
@RequestMapping(value = "/client")
public class ClientController {

  @Value("${endpoint.ms-service}")
  private String msEndpoint;

  private final RestTemplate restTemplate;

  public ClientController(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  /**
   * get mutual tls server data.
   *
   * @return {@link ResponseEntity} with {@code String} as data
   */
  @GetMapping(value = "/infos", produces = "application/json")
  public ResponseEntity<String> getMsData() {
    try {
      String serverResponse = restTemplate.getForObject(new URI(msEndpoint), String.class);
      return ResponseEntity.ok("From Client: " + serverResponse);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
  }
}
