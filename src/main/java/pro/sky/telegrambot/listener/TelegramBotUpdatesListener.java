package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import com.vdurmont.emoji.EmojiParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.markup.CatShelterMarkup;
import pro.sky.telegrambot.markup.DogShelterMarkup;
import pro.sky.telegrambot.markup.HelloKeyboardMarkup;
import pro.sky.telegrambot.markup.ShelterKeyboardMarkup;
import pro.sky.telegrambot.repository.ShelterRepository;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    private final DogShelterMarkup dogShelterMarkup;

    private final CatShelterMarkup catShelterMarkup;

    private final ShelterKeyboardMarkup shelterKeyboardMarkup;
    private boolean isWaitNumber = false;
    @Autowired
    public TelegramBotUpdatesListener(DogShelterMarkup dogShelterMarkup, CatShelterMarkup catShelterMarkup, ShelterRepository shelterRepository, ShelterKeyboardMarkup shelterKeyboardMarkup) {
        this.dogShelterMarkup = dogShelterMarkup;
        this.catShelterMarkup = catShelterMarkup;
        this.shelterKeyboardMarkup = shelterKeyboardMarkup;
    }

    @Autowired
    private TelegramBot telegramBot;

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        String smile_dog = EmojiParser.parseToUnicode(":dog:");
        String smile_cat = EmojiParser.parseToUnicode(":cat2:");
        String smile_arrow = EmojiParser.parseToUnicode(":arrow_left:");

        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            String textMessage = update.message().text();
            Long chat_id = update.message().chat().id();


            if (textMessage != null && textMessage.equals("/start") || textMessage.equals(smile_arrow + " В начало")) {
                executeSendMessage(HelloKeyboardMarkup.boardMarkupCatAndDog(chat_id));

            } else if (textMessage.equals(smile_cat + " Приют для кошек")) {
                executeSendMessage(CatShelterMarkup.catBoardMarkup(chat_id));

            } else if (textMessage.equals(smile_cat + " Узнать информацию о приюте")) {
                executeSendMessage(CatShelterMarkup.catShelterBoardMarkup(chat_id));
            } else if (textMessage.equals(smile_cat + "Рассказать о приюте")) {
                executeSendMessage(catShelterMarkup.catShelterStory(chat_id));
            } else if (textMessage.equals(smile_cat + "Расписание работы")) {
                executeSendMessage(catShelterMarkup.getCatWorkScheduleFromDB(chat_id));
            } else if (textMessage.equals(smile_cat + "Адрес приюта")) {
                executeSendMessage(catShelterMarkup.getCatAddressFromDB(chat_id));
            } else if (textMessage.equals(smile_cat + "Схема проезда")) {
                executePhoto(catShelterMarkup.getCatDrivingDirections(chat_id));
            } else if (textMessage.equals(smile_cat + "Оформление пропуска")) {
                executeSendMessage(catShelterMarkup.getCatShelterPhoneNumberSecurityFromDB(chat_id));
            } else if (textMessage.equals(smile_cat + "Техника безопасности")) {
                executeSendMessage(catShelterMarkup.getCatShelterSafetyPrecautionsSecurityFromDB(chat_id));
            } else if (textMessage.equals(smile_cat + "Оставить номер телефона") || textMessage.equals(smile_dog + "Оставить номер телефона")) {
                executeSendMessage(shelterKeyboardMarkup.contactSelection(chat_id));
                isWaitNumber = true;
            } else if (textMessage.matches("^\\+\\d{1,}-\\d{3}-\\d{3}-\\d{2}-\\d{2}$") && isWaitNumber) {
                executeSendMessage(shelterKeyboardMarkup.saveUserContact(chat_id, update));
                isWaitNumber = false;
            } else if (textMessage.equals(smile_cat + " Связаться с волонтером")) {
                executeSendMessage(catShelterMarkup.getVolunteersCatShelter(chat_id));


            } else if (textMessage.equals(smile_dog + " Приют для собак")) {
                executeSendMessage(DogShelterMarkup.dogBoardMarkup(chat_id));

            } else if (textMessage.equals(smile_dog + " Узнать информацию о приюте")) {
                executeSendMessage(DogShelterMarkup.dogShelterBoardMarkup(chat_id));
            } else if (textMessage.equals(smile_dog + "Рассказать о приюте")) {
                executeSendMessage(dogShelterMarkup.dogShelterStory(chat_id));
            } else if (textMessage.equals(smile_dog + "Расписание работы")) {
                executeSendMessage(dogShelterMarkup.getDogWorkScheduleFromDB(chat_id));
            } else if (textMessage.equals(smile_dog + "Адрес приюта")) {
                executeSendMessage(dogShelterMarkup.getDogAddressFromDB(chat_id));
            } else if (textMessage.equals(smile_dog + "Схема проезда")) {
                executePhoto(dogShelterMarkup.getDogDrivingDirections(chat_id));
            } else if (textMessage.equals(smile_dog + "Оформление пропуска")) {
                executeSendMessage(dogShelterMarkup.getDogShelterPhoneNumberSecurityFromDB(chat_id));
            } else if (textMessage.equals(smile_dog + "Техника безопасности")) {
                executeSendMessage(dogShelterMarkup.getDogShelterSafetyPrecautionsSecurityFromDB(chat_id));
            } else if (textMessage.equals(smile_dog + " Связаться c волонтером")) {
                executeSendMessage(dogShelterMarkup.getVolunteersDogShelter(chat_id));

            } else {
                executeSendMessage(new SendMessage(chat_id, "Не понял Вас."));
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
    private void executeSendMessage(SendMessage sendMessage) {
        telegramBot.execute(sendMessage);
    }
    private void executePhoto(SendPhoto sendPhoto) {
        telegramBot.execute(sendPhoto);
    }
}
