package com.tg.sandbox.domain.common;

import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class CallbackQueryAcknowledgementNotifier {
  private final TelegramClient telegramClient;

  public void notify(final String callbackQueryId) {
    Try.of(
            () ->
                telegramClient.execute(
                    AnswerCallbackQuery.builder()
                        .callbackQueryId(callbackQueryId)
                        .text("Got it, Boss!")
                        .showAlert(false)
                        .build()))
        .onFailure(
            ex ->
                log.error(
                    "Failed to send Telegram answer callback query. Reason: {}", ex.getMessage()));
  }
}
