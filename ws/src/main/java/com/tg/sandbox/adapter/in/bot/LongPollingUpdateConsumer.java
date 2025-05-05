package com.tg.sandbox.adapter.in.bot;

import static com.tg.sandbox.adapter.in.bot.common.BotMessageUtils.isMessageReceived;
import static com.tg.sandbox.adapter.in.bot.common.BotMessageUtils.isMessageTypeOfCommand;
import static com.tg.sandbox.adapter.in.bot.common.rusecasestrategy.BotUseCaseStrategyType.fromValue;

import com.tg.sandbox.adapter.in.bot.common.UnsupportedCommandHandler;
import com.tg.sandbox.adapter.in.bot.common.rusecasestrategy.BotUseCaseStrategyResolver;
import com.tg.sandbox.adapter.in.bot.common.rusecasestrategy.BotUseCaseStrategyType;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class LongPollingUpdateConsumer implements LongPollingSingleThreadUpdateConsumer {

  private final BotUseCaseStrategyResolver useCaseStrategyResolver;
  private final UnsupportedCommandHandler unsupportedCommandHandler;

  @Override
  public void consume(final Update update) {
    if (isMessageReceived(update)) {
      final String messageText = update.getMessage().getText();

      if (isMessageTypeOfCommand(messageText)) {
        final BotUseCaseStrategyType type =
            Try.of(() -> fromValue(messageText))
                .onFailure(__ -> unsupportedCommandHandler.handle(update))
                .get();

        useCaseStrategyResolver.resolveAndHandle(type, update);
      } else {
        unsupportedCommandHandler.handle(update);
      }
    }
  }
}
