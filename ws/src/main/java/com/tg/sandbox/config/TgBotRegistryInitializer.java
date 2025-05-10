package com.tg.sandbox.config;

import io.vavr.control.Try;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;

@Component
@RequiredArgsConstructor
public class TgBotRegistryInitializer {
  private final List<LongPollingUpdateConsumer> updateConsumers;
  private final TgSandboxWsProperties properties;
  private TelegramBotsLongPollingApplication longPollingApplication;

  @PostConstruct
  public void init() {
    this.longPollingApplication = new TelegramBotsLongPollingApplication();

    updateConsumers.forEach(this::registerLongPollingUpdateConsumerBot);
  }

  @PreDestroy
  public void destroy() {
    Try.run(() -> this.longPollingApplication.close());
  }

  public void registerLongPollingUpdateConsumerBot(final LongPollingUpdateConsumer updateConsumer) {
    Try.run(
        () ->
            this.longPollingApplication.registerBot(
                properties.getBot().getToken(), updateConsumer));
  }
}
