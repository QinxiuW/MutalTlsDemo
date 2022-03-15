package com.example.server;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * ServletInitializer.
 *
 * @Description ServletInitializer
 * @Date 15/03/2022 12:02
 * @Created by Qinxiu Wang
 */
public class ServletInitializer extends SpringBootServletInitializer {

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(ServerApplication.class);
  }

}
