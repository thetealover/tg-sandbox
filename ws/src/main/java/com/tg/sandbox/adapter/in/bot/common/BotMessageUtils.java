package com.tg.sandbox.adapter.in.bot.common;

import static lombok.AccessLevel.PRIVATE;

import lombok.NoArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;

@NoArgsConstructor(access = PRIVATE)
public class BotMessageUtils {
  public static boolean isMessageTypeOfCommand(final String messageText) {
    return messageText.startsWith("/");
  }

  public static boolean isMessageReceived(final Update update) {
    return update.hasMessage() && update.getMessage().hasText();
  }
}
