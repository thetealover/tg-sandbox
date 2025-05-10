package com.tg.sandbox.adapter.in.bot;

import static com.tg.sandbox.adapter.in.bot.common.BotMessageUtils.isMessageReceived;

import com.tg.sandbox.adapter.in.bot.common.usecasestrategy.callback.CallbackUseCaseStrategyResolver;
import com.tg.sandbox.adapter.in.bot.common.usecasestrategy.callback.CallbackUseCaseStrategyType;
import com.tg.sandbox.adapter.in.bot.common.usecasestrategy.command.CommandUseCaseStrategyResolver;
import com.tg.sandbox.adapter.in.bot.common.usecasestrategy.command.CommandUseCaseStrategyType;
import com.tg.sandbox.domain.common.CallbackQueryAcknowledgementNotifier;
import com.tg.sandbox.domain.common.UnsupportedCommandHandler;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class LongPollingUpdateConsumer implements LongPollingSingleThreadUpdateConsumer {

  private final CallbackQueryAcknowledgementNotifier callbackQueryAcknowledgementNotifier;
  private final UnsupportedCommandHandler unsupportedCommandHandler;

  private final CommandUseCaseStrategyResolver useCaseStrategyResolver;
  private final CallbackUseCaseStrategyResolver callbackUseCaseStrategyResolver;

  @Override
  public void consume(final Update update) {
    if (update.hasCallbackQuery()) {
      callbackQueryAcknowledgementNotifier.notify(update.getCallbackQuery().getId());
      handleCallback(update);
    }
    if (isMessageReceived(update)) {
      if (update.getMessage().isCommand()) {
        handleCommand(update);
      } else {
        unsupportedCommandHandler.handle(update);
      }
    }
  }

  private void handleCallback(final Update update) {
    final CallbackUseCaseStrategyType callbackType =
        Try.of(() -> CallbackUseCaseStrategyType.fromValue(update.getCallbackQuery().getData()))
            .onFailure(__ -> unsupportedCommandHandler.handle(update))
            .get();

    callbackUseCaseStrategyResolver.resolveAndHandle(callbackType, update);
  }

  private void handleCommand(final Update update) {
    final CommandUseCaseStrategyType type =
        Try.of(() -> CommandUseCaseStrategyType.fromValue(update.getMessage().getText()))
            .onFailure(__ -> unsupportedCommandHandler.handle(update))
            .get();

    useCaseStrategyResolver.resolveAndHandle(type, update);
  }
}
