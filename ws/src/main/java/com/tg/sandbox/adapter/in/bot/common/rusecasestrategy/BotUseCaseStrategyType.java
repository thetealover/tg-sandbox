package com.tg.sandbox.adapter.in.bot.common.rusecasestrategy;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BotUseCaseStrategyType {
  PING("/ping"),
  TALK("/talk"),
  ;

  private final String value;

  public static BotUseCaseStrategyType fromValue(final String value) {
    for (final BotUseCaseStrategyType type : BotUseCaseStrategyType.values()) {
      if (type.getValue().equals(value)) {
        return type;
      }
    }
    throw new IllegalArgumentException("Unknown command value: %s".formatted(value));
  }
}
