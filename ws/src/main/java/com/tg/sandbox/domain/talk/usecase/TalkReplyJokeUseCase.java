package com.tg.sandbox.domain.talk.usecase;

import static com.tg.sandbox.adapter.in.bot.common.usecasestrategy.callback.CallbackUseCaseStrategyType.TALK_JOKE;

import com.tg.sandbox.adapter.in.bot.common.usecasestrategy.callback.CallbackUseCaseStrategy;
import com.tg.sandbox.adapter.in.bot.common.usecasestrategy.callback.CallbackUseCaseStrategyType;
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
public class TalkReplyJokeUseCase implements CallbackUseCaseStrategy {
  private final TelegramClient telegramClient;

  @Override
  public CallbackUseCaseStrategyType getType() {
    return TALK_JOKE;
  }

  @Override
  public void handle(final Update update) {
    Try.of(
            () ->
                telegramClient.execute(
                    SendMessage.builder()
                        .chatId(update.getCallbackQuery().getMessage().getChatId())
                        .text(
                            "Why don't blind people skydive?\nIt scares the shit out of their dogs 🐶")
                        .build()))
        .onFailure(__ -> log.error("Failed to send Telegram message"));

    log.info(
        "Handled /talk/joke reply callback. username={}",
        update.getCallbackQuery().getFrom().getUserName());
  }
}
