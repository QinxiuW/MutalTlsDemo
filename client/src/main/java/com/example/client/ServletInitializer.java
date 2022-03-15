package com.example.client;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * ServletInitializer.
 *
 * @Description ServletInitializer
 * @Date 15/03/2022 12:14
 * @Created by Qinxiu Wang
 */
public class ServletInitializer extends SpringBootServletInitializer {

  /**
   * ServletInitializer for .war.
   *
   * @param application {@link SpringApplicationBuilder}
   * @return {@link SpringApplicationBuilder}
   */
  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(ClientApplication.class);
  }
}
