package com.tg.sandbox.domain.ping.usecase;

import static com.tg.sandbox.adapter.in.bot.common.usecasestrategy.command.CommandUseCaseStrategyType.PING;
import static java.time.format.DateTimeFormatter.ofPattern;

import com.tg.sandbox.adapter.in.bot.common.usecasestrategy.command.CommandUseCaseStrategy;
import com.tg.sandbox.adapter.in.bot.common.usecasestrategy.command.CommandUseCaseStrategyType;
import io.vavr.control.Try;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class PingCommandUseCase implements CommandUseCaseStrategy {
  private final TelegramClient telegramClient;

  @Override
  public CommandUseCaseStrategyType getType() {
    return PING;
  }

  @Override
  public void handle(final Update update) {
    final String response =
        "📢 ping \n⏱️ %s".formatted(LocalDateTime.now().format(ofPattern("dd/MM/yyyy hh:mm:ss a")));

    final SendMessage sendMessage =
        SendMessage.builder().chatId(update.getMessage().getChatId()).text(response).build();

    Try.of(() -> telegramClient.execute(sendMessage))
        .onFailure(__ -> log.error("Failed to send Telegram message"));

    log.info(
        "Handled /ping command update. username={}", update.getMessage().getFrom().getUserName());
  }
}
