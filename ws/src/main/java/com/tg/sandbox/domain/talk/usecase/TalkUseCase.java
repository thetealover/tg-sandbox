package com.tg.sandbox.domain.talk.usecase;

import static com.tg.sandbox.adapter.in.bot.common.rusecasestrategy.BotUseCaseStrategyType.TALK;

import com.tg.sandbox.adapter.in.bot.common.rusecasestrategy.BotUseCaseStrategy;
import com.tg.sandbox.adapter.in.bot.common.rusecasestrategy.BotUseCaseStrategyType;
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
public class TalkUseCase implements BotUseCaseStrategy {
  private final TelegramClient telegramClient;

  @Override
  public BotUseCaseStrategyType getType() {
    return TALK;
  }

  @Override
  public void handle(final Update update) {
    Try.of(
            () ->
                telegramClient.execute(
                    SendMessage.builder()
                        .chatId(update.getMessage().getChatId())
                        .text("🦜 You wanna talk?")
                        .build()))
        .onFailure(__ -> log.error("Failed to send Telegram message"));

    log.info("Handled talk command update. user={}", update.getMessage().getFrom().getUserName());
  }
}
