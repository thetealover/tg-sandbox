package com.tg.sandbox.adapter.in.bot.common.rusecasestrategy;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class BotUseCaseStrategyResolver {
  private final Map<BotUseCaseStrategyType, BotUseCaseStrategy> strategies;

  public BotUseCaseStrategyResolver(final List<BotUseCaseStrategy> strategies) {
    this.strategies = strategies.stream().collect(toMap(BotUseCaseStrategy::getType, identity()));

    if (strategies.size() != BotUseCaseStrategyType.values().length) {
      throw new IllegalStateException("Bot use-case strategy count does not match the types count");
    }
  }

  public BotUseCaseStrategy resolve(final BotUseCaseStrategyType type) {
    return strategies.get(type);
  }

  public void resolveAndHandle(final BotUseCaseStrategyType type, final Update update) {
    resolve(type).handle(update);
  }
}
