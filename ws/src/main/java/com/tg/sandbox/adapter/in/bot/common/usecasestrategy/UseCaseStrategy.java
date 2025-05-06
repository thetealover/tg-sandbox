package com.tg.sandbox.adapter.in.bot.common.usecasestrategy;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface UseCaseStrategy {
  void handle(Update update);
}
