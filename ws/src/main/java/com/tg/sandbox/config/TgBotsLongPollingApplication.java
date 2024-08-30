package com.tg.sandbox.config;

import io.vavr.control.Try;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;

@Getter
@Component
public class TgBotsLongPollingApplication {
  private TelegramBotsLongPollingApplication context;

  @PostConstruct
  public void init() {
    this.context = new TelegramBotsLongPollingApplication();
  }

  @PreDestroy
  public void destroy() {
    Try.run(() -> this.context.close());
  }
}
