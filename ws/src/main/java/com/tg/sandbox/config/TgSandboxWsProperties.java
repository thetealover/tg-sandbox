package com.tg.sandbox.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "ws")
public class TgSandboxWsProperties {
  private String username;
  private String token;
}
