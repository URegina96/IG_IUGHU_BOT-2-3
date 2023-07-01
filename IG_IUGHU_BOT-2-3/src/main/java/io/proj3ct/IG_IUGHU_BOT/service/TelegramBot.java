package io.proj3ct.IG_IUGHU_BOT.service;


import io.proj3ct.IG_IUGHU_BOT.config.BotConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {


    final BotConfig config;

    static final String HELP_TEXT="Бот для практической работы со Spring.\n\n" +
           "Варианты команд: \n\n" +
            "Введите /start, чтобы увидеть приветственное сообщение\n\n " +
            "Введите /mydata, чтобы просмотреть сохраненные данные о себе ( на данный момент функция недоступна) \n\n" +
            "Введите /help, чтобы снова увидеть это сообщение";

    public TelegramBot(BotConfig config) {

        this.config = config;
        List<BotCommand> listofCommands = new ArrayList<>();
        listofCommands.add(new BotCommand("/start", "получить приветственное сообщение"));
        listofCommands.add(new BotCommand("/mydata", "сохранить ваши данные"));
        listofCommands.add(new BotCommand("/deletedata", "удалить мои данные"));
        listofCommands.add(new BotCommand("/help", "информация как использовать этого бота"));
        listofCommands.add(new BotCommand("/settings", "установить свои предпочтения"));
        try {
            this.execute(new SetMyCommands(listofCommands, new BotCommandScopeDefault(), null));

        } catch (TelegramApiException e) {
            log.error("Ошибка настройки списка команд бота: " + e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {


        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();


            switch (messageText) {
                case "/start":
                    startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    break;

                case "/help":

                    sendMessage(chatId, HELP_TEXT);
                    break;


                default:
                    sendMessage(chatId, "Извините, команда не распознана");
            }
        }
    }

    private void startCommandReceived(long chatId, String name) {

        String answer = "Привет, " + name + ", рад встрече!";
        log.info("Ответил пользователю " + name);


        sendMessage(chatId, answer);

    }

    private void sendMessage(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Возникла ошибка: " + e.getMessage());
        }
    }
}


