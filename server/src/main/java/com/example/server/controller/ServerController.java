package com.example.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * serverController.
 *
 * @Description serverController
 * @Date 15/03/2022 12:02
 * @Created by Qinxiu Wang
 */
@RestController
@RequestMapping("/server")
public class ServerController {

  /**
   * get Info.
   *
   * @return {@link ResponseEntity} with {@code String} as data.
   */
  @GetMapping(value = "/hello", produces = "application/json")
  public ResponseEntity<String> getInfo() {
    String info = "Getting infos resource from Server";
    System.out.println(info);
    return ResponseEntity.ok(info);
  }
}
