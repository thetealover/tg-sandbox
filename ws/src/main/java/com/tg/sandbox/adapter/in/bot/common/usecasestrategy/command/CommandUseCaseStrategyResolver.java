package com.tg.sandbox.adapter.in.bot.common.usecasestrategy.command;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class CommandUseCaseStrategyResolver {
  private final Map<CommandUseCaseStrategyType, CommandUseCaseStrategy> strategies;

  public CommandUseCaseStrategyResolver(final List<CommandUseCaseStrategy> strategies) {
    this.strategies =
        strategies.stream().collect(toMap(CommandUseCaseStrategy::getType, identity()));

    if (strategies.size() != CommandUseCaseStrategyType.values().length) {
      throw new IllegalStateException(
          "Bot command use-case strategy count does not match the types count");
    }
  }

  public CommandUseCaseStrategy resolve(final CommandUseCaseStrategyType type) {
    return strategies.get(type);
  }

  public void resolveAndHandle(final CommandUseCaseStrategyType type, final Update update) {
    resolve(type).handle(update);
  }
}
