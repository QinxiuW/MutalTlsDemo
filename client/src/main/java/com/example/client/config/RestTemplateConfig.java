package com.example.client.config;

import java.io.InputStream;
import java.security.KeyStore;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * RestTemplateConfig.
 *
 * @Description RestTemplateConfig
 * @Date 15/03/2022 12:14
 * @Created by Qinxiu Wang
 */
@Configuration
public class RestTemplateConfig {

  @Value("${key-store.path}")
  private String ketStoreCert;

  @Value("${key-store.password}")
  private String keyStorePassword;

  @Value("${key-store.type}")
  private String keyStoreType;

  /**
   * Init RestTemplate with keyStore infos.
   *
   * @return {@link RestTemplate}
   */
  @Bean
  public RestTemplate getRestTemplate() {
    RestTemplate restTemplate = new RestTemplate();
    KeyStore keyStore;
    HttpComponentsClientHttpRequestFactory requestFactory;

    try {
      keyStore = KeyStore.getInstance(keyStoreType);
      ClassPathResource classPathResource = new ClassPathResource(ketStoreCert);
      InputStream inputStream = classPathResource.getInputStream();
      keyStore.load(inputStream, keyStorePassword.toCharArray());
      SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(
          new SSLContextBuilder().loadTrustMaterial(null, new TrustSelfSignedStrategy())
              .loadKeyMaterial(keyStore, keyStorePassword.toCharArray()).build());

      HttpClient httpClient = HttpClients.custom().setSSLSocketFactory(socketFactory)
          .setMaxConnTotal(5)
          .setMaxConnPerRoute(5).build();

      requestFactory = new HttpComponentsClientHttpRequestFactory(
          httpClient);
      requestFactory.setReadTimeout(5000);
      requestFactory.setConnectTimeout(5000);

      restTemplate.setRequestFactory(requestFactory);

    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("Exception creating restTemplate with SSL", e);
    }
    return restTemplate;
  }
}

