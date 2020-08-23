package ua.home.dima;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

public class Bot extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {
        String userMessage = update.getMessage().getText();
        System.out.println("text from user: " + userMessage);

        String answerForUser = "";
        try {
            answerForUser = Weather.getWeatherByCityName(userMessage);
        } catch (IOException e) {
            answerForUser = "there is no such city";
            e.printStackTrace();
        }

        sendMessage(update, answerForUser);
    }

    public synchronized void sendMessage(Update update, String message) {
        SendMessage messageForUser = new SendMessage();
        messageForUser.enableMarkdown(true);
        messageForUser.setChatId(update.getMessage().getChatId().toString());
        messageForUser.setText(message);
        try {
            execute(messageForUser);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "komarovdmytrobot";
    }

    @Override
    public String getBotToken() {
        return "1285041001:AAGs6aq2M9sjKrPXZAOeYGDqsWJUCpFC2aY";
    }
}
