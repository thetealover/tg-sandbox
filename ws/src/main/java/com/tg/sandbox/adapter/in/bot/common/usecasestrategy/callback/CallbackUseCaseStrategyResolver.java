package com.tg.sandbox.adapter.in.bot.common.usecasestrategy.callback;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class CallbackUseCaseStrategyResolver {
  private final Map<CallbackUseCaseStrategyType, CallbackUseCaseStrategy> strategies;

  public CallbackUseCaseStrategyResolver(final List<CallbackUseCaseStrategy> strategies) {
    this.strategies =
        strategies.stream().collect(toMap(CallbackUseCaseStrategy::getType, identity()));

    if (strategies.size() != CallbackUseCaseStrategyType.values().length) {
      throw new IllegalStateException(
          "Bot callback use-case strategy count does not match the types count");
    }
  }

  public CallbackUseCaseStrategy resolve(final CallbackUseCaseStrategyType type) {
    return strategies.get(type);
  }

  public void resolveAndHandle(final CallbackUseCaseStrategyType type, final Update update) {
    resolve(type).handle(update);
  }
}
