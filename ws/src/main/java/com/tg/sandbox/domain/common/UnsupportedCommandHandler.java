package com.tg.sandbox.domain.common;

import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class UnsupportedCommandHandler {
  private static final String UNSUPPORTED_COMMAND_RESPONSE = "❌ Unsupported command";
  private final TelegramClient telegramClient;

  public void handle(final Update update) {
    final SendMessage sendMessage =
        SendMessage.builder()
            .chatId(update.getMessage().getChatId())
            .text(UNSUPPORTED_COMMAND_RESPONSE)
            .build();

    Try.run(() -> telegramClient.execute(sendMessage))
        .onFailure(__ -> log.error("Failed to send Telegram message"));

    log.info(
        "Handled unsupported command request. username={}",
        update.getMessage().getFrom().getUserName());
  }
}
