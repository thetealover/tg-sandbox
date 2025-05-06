package com.tg.sandbox.adapter.in.bot.common.usecasestrategy.callback;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CallbackUseCaseStrategyType {
  TALK_JOKE("talk/joke"),
  TALK_ADVICE("talk/advice"),
  ;

  private final String value;

  public static CallbackUseCaseStrategyType fromValue(final String value) {
    for (final CallbackUseCaseStrategyType type : CallbackUseCaseStrategyType.values()) {
      if (type.getValue().equals(value)) {
        return type;
      }
    }
    throw new IllegalArgumentException("Unknown callback value: %s".formatted(value));
  }
}
