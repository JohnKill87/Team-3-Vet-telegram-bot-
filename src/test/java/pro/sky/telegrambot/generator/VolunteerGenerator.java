package pro.sky.telegrambot.generator;

import pro.sky.telegrambot.model.Volunteer;

public class VolunteerGenerator {

    public static final Long ID = 5L;
    public static final String NAME = "Erik";
    public static final String PHONENUMBER = "+8800553535";
    public static final Shelter SHELTER = new Shelter("Vagro", "Vatran", "Vano", "+89000");

    public static Volunteer getVolunteer() {
        return new Volunteer(
                5L,
                "Erik",
                "+8800553535",
                new Shelter("Vagro", "Vatran", "Vano", "+89000"));
    }
}
