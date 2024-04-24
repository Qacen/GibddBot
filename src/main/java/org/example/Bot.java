package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
public class Bot extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {
        String[] id = {"5039302768","5331252448","990166938","1067999991","1483368064","5147256467","5800579525","5024420180","753397955","961554061"};
        String startText = "Здраствуйте, это анонимный бот для приёма сообщений о правонарушениях в селе Бичура\n" +
                "Просьба давать подробную информацию о правонарушених (время, место, отличительные знаки, так же возможна отправка фото)";
        //String id2 = "5331252448";
        if (update.hasMessage()){
            System.out.println(update.getMessage().getFrom().getId() +"   "+ update.getMessage().getFrom().getFirstName());
            boolean hasText = update.getMessage().hasText();
            if (hasText) {
                if (update.getMessage().getText().equals("/start")) {
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setText(startText);
                    sendMessage.setChatId(update.getMessage().getChatId());
                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                    return;
                }
            }
            boolean hasPhoto = update.getMessage().hasPhoto();
            //System.out.println(update.getMessage().getText() + " " + update.getMessage().getFrom().getId() + " " + update.getMessage().getFrom().getFirstName());

            for (int i = 0; i < id.length; i++) {
                SendMessage sendMessage = new SendMessage();
                if (hasText) {
                    sendMessage.setChatId(id[i]);
                    sendMessage.setText(update.getMessage().getText());
                }
                System.out.println("отправка сообщения: "+id[i]);
                SendPhoto sendPhoto = new SendPhoto();
                if (hasPhoto){
                    sendPhoto.setChatId(id[i]);
                    for (PhotoSize photo : update.getMessage().getPhoto()){
                        String photoId = photo.getFileId().toString();
                        sendPhoto.setPhoto(new InputFile(photoId));
                        sendPhoto.setChatId(id[i]);
                        sendPhoto.setCaption(update.getMessage().getCaption());
                    }

                }
                try {
                    if (hasText) {
                        execute(sendMessage);
                    }
                    if (hasPhoto){
                        execute(sendPhoto);
                    }
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(update.getMessage().getChatId());
            sendMessage.setText("Ваше сообщение было отправлено сотрудникам ГИБДД");
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "gibdd_Bichura_bot";
    }
    @Override
    public String getBotToken(){return "токен";}
}
