package com.tg.sandbox.domain.common;

import static com.tg.sandbox.adapter.in.bot.common.usecasestrategy.command.CommandUseCaseStrategyType.START;

import com.tg.sandbox.adapter.in.bot.common.usecasestrategy.command.CommandUseCaseStrategy;
import com.tg.sandbox.adapter.in.bot.common.usecasestrategy.command.CommandUseCaseStrategyType;
import io.vavr.control.Try;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class StartCommandHandler implements CommandUseCaseStrategy {
  private final TelegramClient telegramClient;

  @Override
  public CommandUseCaseStrategyType getType() {
    return START;
  }

  @Override
  public void handle(final Update update) {
    ReplyKeyboardMarkup keyboard =
        ReplyKeyboardMarkup.builder()
            .keyboardRow(
                new KeyboardRow(
                    List.of(
                        KeyboardButton.builder().text("/talk").build(),
                        KeyboardButton.builder().text("/ping").build())))
            .resizeKeyboard(true)
            .oneTimeKeyboard(false)
            .build();

    Try.of(
            () ->
                telegramClient.execute(
                    SendMessage.builder()
                        .chatId(update.getMessage().getChatId())
                        .text("Salute, Boss! 🫡\nI'm at your service!")
                        .replyMarkup(keyboard)
                        .build()))
        .onFailure(ex -> log.error("Failed to send Telegram message. Reason: {}", ex.getMessage()));

    log.info(
        "Handled /start command update. username={}", update.getMessage().getFrom().getUserName());
  }
}
