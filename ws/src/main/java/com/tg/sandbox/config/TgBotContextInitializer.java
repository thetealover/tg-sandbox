package com.tg.sandbox.config;

import io.vavr.control.Try;
import jakarta.annotation.PostConstruct;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;

@Component
@RequiredArgsConstructor
public class TgBotContextInitializer {
  private final List<LongPollingUpdateConsumer> updateConsumers;
  private final TgBotsLongPollingApplication application;
  private final TgSandboxWsProperties properties;

  @PostConstruct
  public void init() {
    for (final LongPollingUpdateConsumer consumer : updateConsumers) {
      registerLongPollingUpdateConsumerBot(consumer);
    }
  }

  public void registerLongPollingUpdateConsumerBot(final LongPollingUpdateConsumer updateConsumer) {
    Try.run(
        () -> application.getContext().registerBot(properties.getBot().getToken(), updateConsumer));
  }
}
