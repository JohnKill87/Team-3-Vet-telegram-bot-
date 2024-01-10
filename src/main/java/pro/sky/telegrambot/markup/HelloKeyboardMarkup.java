package pro.sky.telegrambot.markup;

import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.stereotype.Component;

@Component
public class HelloKeyboardMarkup {

    public static SendMessage boardMarkupCatAndDog(Long chat_id) {
        String smile_dog = EmojiParser.parseToUnicode(":dog:");
        String smile_cat = EmojiParser.parseToUnicode(":cat2:");

        KeyboardButton keyboardButtonCat = new KeyboardButton(smile_cat + " Приют для кошек");
        KeyboardButton keyboardButtonDog = new KeyboardButton(smile_dog + " Приют для собак");

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup(keyboardButtonCat);

        keyboardMarkup.addRow(keyboardButtonDog);

        keyboardMarkup.resizeKeyboard(true);
        String text_bot = "Привет! Я бот который помогает найти бездомным животным своих любящих хозяев. Выберите в меню нужный вам приют.";

        SendMessage sendMessage = new SendMessage(chat_id, text_bot);
        sendMessage.replyMarkup(keyboardMarkup);

        return sendMessage;
    }
}
