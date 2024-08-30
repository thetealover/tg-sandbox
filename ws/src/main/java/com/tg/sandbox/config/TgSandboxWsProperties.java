package com.tg.sandbox.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "ws")
public class TgSandboxWsProperties {
  private BotProperties bot;

  @Data
  public static class BotProperties {
    private String username;
    private String token;
  }
}
