package com.tg.sandbox.adapter.in.bot.common.commandhandlerstrategy;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface BotCommandHandlerStrategy {
  BotCommandHandlerStrategyType getType();

  void handle(Update update);
}
