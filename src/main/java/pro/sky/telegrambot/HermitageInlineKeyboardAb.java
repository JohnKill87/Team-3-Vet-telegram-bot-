package pro.sky.telegrambot;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.stereotype.Component;


@Component
public class HermitageInlineKeyboardAb {

    private final String smile_point_down = EmojiParser.parseToUnicode(":point_down:");

    public SendMessage hermitageInlineKeyboardAb(Long chat_id) {
        String text_bot = " Если ни один из вариантов не подходит, то бот может позвать волонтера "+smile_point_down+" Выберите действие "+smile_point_down;
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
}
