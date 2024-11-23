package com.tg.sandbox.adapter.in.bot.common.commandhandlerstrategy;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BotCommandHandlerStrategyType {
  PING("/ping"),
  ;

  private final String value;

  public static BotCommandHandlerStrategyType fromValue(final String value) {
    for (final BotCommandHandlerStrategyType type : BotCommandHandlerStrategyType.values()) {
      if (type.getValue().equals(value)) {
        return type;
      }
    }
    throw new IllegalArgumentException("Unknown command value: %s".formatted(value));
  }
}
