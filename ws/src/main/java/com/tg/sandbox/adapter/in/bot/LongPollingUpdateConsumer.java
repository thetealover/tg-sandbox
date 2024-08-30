package com.tg.sandbox.adapter.in.bot;

import static com.tg.sandbox.adapter.in.bot.common.BotCommandHandlerStrategyType.fromValue;

import com.tg.sandbox.adapter.in.bot.common.BotCommandHandlerStrategyResolver;
import com.tg.sandbox.adapter.in.bot.common.BotCommandHandlerStrategyType;
import com.tg.sandbox.adapter.in.bot.common.UnsupportedCommandHandler;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Component
@RequiredArgsConstructor
public class LongPollingUpdateConsumer implements LongPollingSingleThreadUpdateConsumer {
  private final BotCommandHandlerStrategyResolver commandHandlerStrategyResolver;
  private final UnsupportedCommandHandler unsupportedCommandHandler;

  @Override
  public void consume(final Update update) {
    if (messageReceived(update)) {
      final String messageText = update.getMessage().getText();

      if (isTypeOfCommand(messageText)) {
        final BotCommandHandlerStrategyType type =
            Try.of(() -> fromValue(messageText))
                .onFailure(__ -> unsupportedCommandHandler.handle(update))
                .get();

        commandHandlerStrategyResolver.resolveAndHandle(type, update);
      }
    }
  }

  private static boolean isTypeOfCommand(final String messageText) {
    return messageText.startsWith("/");
  }

  private static boolean messageReceived(final Update update) {
    return update.hasMessage() && update.getMessage().hasText();
  }
}
