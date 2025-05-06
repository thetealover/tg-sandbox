package com.tg.sandbox.adapter.in.bot.common.usecasestrategy.command;

import com.tg.sandbox.adapter.in.bot.common.usecasestrategy.UseCaseStrategy;

public interface CommandUseCaseStrategy extends UseCaseStrategy {
  CommandUseCaseStrategyType getType();
}
