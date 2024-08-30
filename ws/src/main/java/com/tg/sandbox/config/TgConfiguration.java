package com.tg.sandbox.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Configuration
@RequiredArgsConstructor
public class TgConfiguration {
  private final TgSandboxWsProperties properties;

  @Bean
  public TelegramClient telegramClient() {
    return new OkHttpTelegramClient(properties.getBot().getToken());
  }
}
