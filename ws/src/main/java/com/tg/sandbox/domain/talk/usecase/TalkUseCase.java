package com.tg.sandbox.domain.talk.usecase;

import static com.tg.sandbox.adapter.in.bot.common.usecasestrategy.command.CommandUseCaseStrategyType.TALK;

import com.tg.sandbox.adapter.in.bot.common.usecasestrategy.command.CommandUseCaseStrategy;
import com.tg.sandbox.adapter.in.bot.common.usecasestrategy.command.CommandUseCaseStrategyType;
import io.vavr.control.Try;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class TalkUseCase implements CommandUseCaseStrategy {
  private final TelegramClient telegramClient;

  @Override
  public CommandUseCaseStrategyType getType() {
    return TALK;
  }

  @Override
  public void handle(final Update update) {
    final InlineKeyboardMarkup replyKeyboard =
        InlineKeyboardMarkup.builder()
            .keyboardRow(
                new InlineKeyboardRow(
                    List.of(
                        InlineKeyboardButton.builder()
                            .text("Tell me a joke 🙃")
                            .callbackData("talk/joke")
                            .build(),
                        InlineKeyboardButton.builder()
                            .text("Give me an advice 💡")
                            .callbackData("talk/advice")
                            .build())))
            .build();

    final SendMessage message =
        SendMessage.builder()
            .chatId(update.getMessage().getChatId())
            .text("👀 I'm all ears, Boss!\nPick one:")
            .replyMarkup(replyKeyboard)
            .build();

    Try.of(() -> telegramClient.execute(message))
        .onFailure(__ -> log.error("Failed to send Telegram message"));

    log.info(
        "Handled /talk command update. username={}", update.getMessage().getFrom().getUserName());
  }
}
