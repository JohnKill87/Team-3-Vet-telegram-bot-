package pro.sky.telegrambot.markup;

import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.repository.ShelterRepository;

import javax.ws.rs.NotFoundException;

@Component
public class ShelterKeyboardMarkup {

    private final ShelterRepository shelterRepository;

    @Autowired
    public ShelterKeyboardMarkup(ShelterRepository shelterRepository) {
        this.shelterRepository = shelterRepository;
    }

    public static SendMessage shelterBoardMarkup(Long chat_id) {

        KeyboardButton keyboardButton = new KeyboardButton("Рассказать о приюте");
        KeyboardButton keyboardButton2 = new KeyboardButton("Расписание работы");
        KeyboardButton keyboardButton3 = new KeyboardButton("Адрес приюта");

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup(keyboardButton, keyboardButton2, keyboardButton3);

        KeyboardButton keyboardButton4 = new KeyboardButton("Схема проезда");
        KeyboardButton keyboardButton5 = new KeyboardButton("Оформление пропуска");
        KeyboardButton keyboardButton6 = new KeyboardButton("Оставить номер телефона");
        keyboardMarkup.addRow(keyboardButton5, keyboardButton4);

        keyboardMarkup.addRow(keyboardButton6);


        KeyboardButton keyboardButton7 = new KeyboardButton("Техника безопасности");
        KeyboardButton keyboardButton8 = new KeyboardButton("Связаться с волонтером");
        keyboardMarkup.addRow(keyboardButton7, keyboardButton8);

        keyboardMarkup.resizeKeyboard(true);
        keyboardMarkup.oneTimeKeyboard(false);

        String text_bot = "Выберите какую информацию вы хотели бы узнать. Если ни один из вариантов не подходит, то бот может позвать волонтера.";
        SendMessage sendMessage = new SendMessage(chat_id, text_bot);
        sendMessage.replyMarkup(keyboardMarkup);

        return sendMessage;
    }

    public SendMessage getShelterWorkScheduleFromDB(Long chat_id,Long id) {
        String workSchedule = shelterRepository.findById(id).orElseThrow(() -> new NotFoundException("Shelter is empty from DB")).getWorkSchedule();
        return new SendMessage(chat_id, workSchedule);
    }

    public SendMessage getShelterAddressFromDB(Long chat_id,Long id) {
        String address = shelterRepository.findById(id).orElseThrow(() -> new NotFoundException("Shelter is empty from DB")).getAddress();
        return new SendMessage(chat_id, address);
    }
}
