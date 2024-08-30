package com.tg.sandbox.domain.ping.usecase;

import com.tg.sandbox.adapter.in.bot.common.BotCommandHandlerStrategy;
import com.tg.sandbox.adapter.in.bot.common.BotCommandHandlerStrategyType;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.time.LocalDateTime;

import static com.tg.sandbox.adapter.in.bot.common.BotCommandHandlerStrategyType.PING;
import static java.time.format.DateTimeFormatter.ofPattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class PingCommandHandler implements BotCommandHandlerStrategy {
  private final TelegramClient telegramClient;

  @Override
  public BotCommandHandlerStrategyType getType() {
    return PING;
  }

  @Override
  public void handle(final Update update) {
    final String response =
        "📢 ping %s".formatted(LocalDateTime.now().format(ofPattern("dd/MM/yyyy hh:mm:ss a")));

    final SendMessage sendMessage =
        SendMessage.builder().chatId(update.getMessage().getChatId()).text(response).build();

    Try.of(() -> telegramClient.execute(sendMessage))
        .onFailure(throwable -> log.error("Failed to send Telegram message"));

    log.info("Handled ping command request. user={}", update.getMessage().getFrom().getUserName());
  }
}
