package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.vdurmont.emoji.EmojiParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.markup.HelloKeyboardMarkup;
import pro.sky.telegrambot.markup.ShelterKeyboardMarkup;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    private final String smile_check_mark = EmojiParser.parseToUnicode(":white_check_mark:");
    private Long chat_id;
    @Autowired
    private HelloKeyboardMarkup helloKeyboardAb;
    @Autowired
    private TelegramBot telegramBot;

    @Autowired
    private ShelterKeyboardMarkup shelterKeyboardMarkup;

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        String smile_dog = EmojiParser.parseToUnicode(":dog:");
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            String textMessage = update.message().text();
            chat_id = update.message().chat().id();
            String command = "/menu";

            if (update.message().text().equals("/start")) {
                telegramBot.execute(new SendMessage(chat_id,"Привет! Я бот который помогает найти бездомным животным своих любящих хозяев"
                        +smile_dog+
                        " Для доступа в меню введи команду: "+command));
            } else if (update.message().text().equals(command)) {
                telegramBot.execute(helloKeyboardAb.boardMarkup(chat_id));
            } else if (textMessage.equals(smile_check_mark+" Узнать информацию о приюте")) {
                telegramBot.execute(ShelterKeyboardMarkup.shelterBoardMarkup(chat_id));
            } else if (textMessage.equals("Рассказать о приюте")) {
                telegramBot.execute(new SendMessage(chat_id, "'Рассказ'"));
            } else if (textMessage.equals("Расписание работы")) {
                telegramBot.execute(shelterKeyboardMarkup.getShelterWorkScheduleFromDB(chat_id,1L));
            } else if (textMessage.equals("Адрес приюта")) {
                telegramBot.execute(shelterKeyboardMarkup.getShelterAddressFromDB(chat_id, 1L));
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
