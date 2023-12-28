package pro.sky.telegrambot.generator;

import pro.sky.telegrambot.model.Volunteer;
import pro.sky.telegrambot.model.shelter.Shelter;

public class VolunteerGenerator {

    public static final Long ID = 5L;
    public static final String FIRSTNAME = "Erik";
    public static final String PHONENUMBER = "+8800553535";
    public static final Shelter SHELTER = new Shelter(
            "9:00 - 21:00", "ул. Воровского д.4", "+8800553535", "Проверка документов");

    public static Volunteer getVolunteer() {
        return new Volunteer(
                5L,
                "Erik",
                "+8800553535",
                new Shelter("9:00 - 21:00", "ул. Воровского д.4", "+8800553535", "Проверка документов"));
    }
}
