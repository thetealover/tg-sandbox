package com.tg.sandbox.adapter.in.bot.common.usecasestrategy.command;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CommandUseCaseStrategyType {
  START("/start"),
  PING("/ping"),
  TALK("/talk"),
  ;

  private final String value;

  public static CommandUseCaseStrategyType fromValue(final String value) {
    for (final CommandUseCaseStrategyType type : CommandUseCaseStrategyType.values()) {
      if (type.getValue().equals(value)) {
        return type;
      }
    }
    throw new IllegalArgumentException("Unknown command value: %s".formatted(value));
  }
}
