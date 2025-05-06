package com.tg.sandbox.adapter.in.bot.common.usecasestrategy.callback;

import com.tg.sandbox.adapter.in.bot.common.usecasestrategy.UseCaseStrategy;

public interface CallbackUseCaseStrategy extends UseCaseStrategy {
  CallbackUseCaseStrategyType getType();
}
