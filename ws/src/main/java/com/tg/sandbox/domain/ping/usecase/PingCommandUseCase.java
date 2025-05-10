package com.tg.sandbox.domain.ping.usecase;

import static com.tg.sandbox.adapter.in.bot.common.usecasestrategy.command.CommandUseCaseStrategyType.PING;
import static java.time.format.DateTimeFormatter.ofPattern;

import com.tg.sandbox.adapter.in.bot.common.usecasestrategy.command.CommandUseCaseStrategy;
import com.tg.sandbox.adapter.in.bot.common.usecasestrategy.command.CommandUseCaseStrategyType;
import io.vavr.control.Try;
import java.time.ZonedDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.TimeZones;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class PingCommandUseCase implements CommandUseCaseStrategy {
  private static final String PING_DATE_FORMAT = "dd/MM/yyyy hh:mm:ss a O";
  private final TelegramClient telegramClient;

  @Override
  public CommandUseCaseStrategyType getType() {
    return PING;
  }

  @Override
  public void handle(final Update update) {
    final String response =
        "📢 ping \n⏱️Time: \n%s\n%s"
            .formatted(
                ZonedDateTime.now().format(ofPattern(PING_DATE_FORMAT)),
                ZonedDateTime.now(TimeZones.GMT.toZoneId())
                    .format(ofPattern("dd/MM/yyyy hh:mm:ss a O")));

    final SendMessage sendMessage =
        SendMessage.builder().chatId(update.getMessage().getChatId()).text(response).build();

    Try.of(() -> telegramClient.execute(sendMessage))
        .onFailure(ex -> log.error("Failed to send Telegram message. Reason: {}", ex.getMessage()));

    log.info(
        "Handled /ping command update. username={}", update.getMessage().getFrom().getUserName());
  }
}
