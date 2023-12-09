package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.vdurmont.emoji.EmojiParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.HermitageInlineKeyboardAb;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    private Long chat_id;
    @Autowired
    private HermitageInlineKeyboardAb hermitageInlineKeyboardAb;
    @Autowired
    private TelegramBot telegramBot;

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        String smile_dog = EmojiParser.parseToUnicode(":dog:");
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            chat_id = update.message().chat().id();
            String command = "/menu";
            if (update.message().text().equals("/start")) {
                telegramBot.execute(new SendMessage(chat_id,"Привет! Я бот который помогает найти бездомным животным своих любящих хозяев"
                        +smile_dog+
                        " Для доступа в меню введи команду: "+command));
            } else if (update.message().text().equals(command)) {
                telegramBot.execute(hermitageInlineKeyboardAb.hermitageInlineKeyboardAb(chat_id));
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
