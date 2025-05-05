package com.tg.sandbox.adapter.in.bot.common.rusecasestrategy;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface BotUseCaseStrategy {
  BotUseCaseStrategyType getType();

  void handle(Update update);
}
