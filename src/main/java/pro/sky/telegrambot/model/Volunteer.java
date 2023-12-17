package pro.sky.telegrambot.model;

import pro.sky.telegrambot.model.shelter.Shelter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "volunteers")
public class Volunteer extends Person{

    public Volunteer() {

    }

    public Volunteer(Long id, String firstName, String phoneNumber, Shelter shelter) {
        super(id, firstName, phoneNumber, shelter);
    }

}
