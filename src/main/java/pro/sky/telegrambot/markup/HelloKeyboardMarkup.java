package pro.sky.telegrambot.markup;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.stereotype.Component;



@Component
public class HelloKeyboardMarkup {

    private final String smile_point_down = EmojiParser.parseToUnicode(":point_down:");
    private final String smile_check_mark = EmojiParser.parseToUnicode(":white_check_mark:");

    public SendMessage hermitageInlineKeyboardAb(Long chat_id) {
        String text_bot = " Если ни один из вариантов не подходит, то бот может позвать волонтера " + smile_point_down + " Выберите действие " + smile_point_down;
        SendMessage message = new SendMessage(chat_id, text_bot);

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();

        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton("Узнать информацию о приюте");
        inlineKeyboardButton1.callbackData("КНОПКА_1");

        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton("Как взять животное из приюта");
        inlineKeyboardButton2.callbackData("КНОПКА_2");

        InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton("Прислать отчет о питомце");
        inlineKeyboardButton3.callbackData("КНОПКА_3");

        InlineKeyboardButton inlineKeyboardButton4 = new InlineKeyboardButton("Позвать волонтера");
        inlineKeyboardButton4.callbackData("КНОПКА_4");

        markupInline.addRow(inlineKeyboardButton1);
        markupInline.addRow(inlineKeyboardButton2);
        markupInline.addRow(inlineKeyboardButton3);
        markupInline.addRow(inlineKeyboardButton4);

        message.replyMarkup(markupInline);

        return message;
    }
    public SendMessage boardMarkup(Long chat_id) {

        KeyboardButton keyboardButton = new KeyboardButton(smile_check_mark + " Узнать информацию о приюте");

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup(keyboardButton);

        KeyboardButton keyboardButton2 = new KeyboardButton(smile_check_mark+" Как взять животное из приюта");
        keyboardMarkup.addRow(keyboardButton2);
        KeyboardButton keyboardButton3 = new KeyboardButton(smile_check_mark+" Прислать отчет о питомце");
        keyboardMarkup.addRow(keyboardButton3);
        KeyboardButton keyboardButton4 = new KeyboardButton(smile_check_mark+" Позвать волонтера");


        keyboardMarkup.addRow(keyboardButton4);
        keyboardMarkup.resizeKeyboard(true);
        keyboardMarkup.oneTimeKeyboard(false);

        String text_bot = " Если ни один из вариантов не подходит, то бот может позвать волонтера " + smile_point_down + " Выберите действие в меню" + smile_point_down;
        SendMessage sendMessage = new SendMessage(chat_id, text_bot);
        sendMessage.replyMarkup(keyboardMarkup);

        return sendMessage;
    }
}
