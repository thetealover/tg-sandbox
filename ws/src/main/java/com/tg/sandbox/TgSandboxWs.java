package com.tg.sandbox;

import com.tg.sandbox.config.TgSandboxWsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;

@SpringBootApplication
@EnableConfigurationProperties(TgSandboxWsProperties.class)
public class TgSandboxWs {

  public static void main(String[] args) {
    SpringApplication.run(TgSandboxWs.class, args);

    // Prevent the application from shutting down
    synchronized (TgSandboxWs.class) {
      try {
        TgSandboxWs.class.wait();
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }
  }
}
