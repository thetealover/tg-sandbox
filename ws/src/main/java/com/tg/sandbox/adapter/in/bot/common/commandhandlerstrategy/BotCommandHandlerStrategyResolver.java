package com.tg.sandbox.adapter.in.bot.common.commandhandlerstrategy;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class BotCommandHandlerStrategyResolver {
  private final Map<BotCommandHandlerStrategyType, BotCommandHandlerStrategy> strategies;

  public BotCommandHandlerStrategyResolver(final List<BotCommandHandlerStrategy> strategies) {
    this.strategies =
        strategies.stream().collect(toMap(BotCommandHandlerStrategy::getType, identity()));
  }

  public BotCommandHandlerStrategy resolve(final BotCommandHandlerStrategyType type) {
    return strategies.get(type);
  }

  public void resolveAndHandle(final BotCommandHandlerStrategyType type, final Update update) {
    resolve(type).handle(update);
  }
}
