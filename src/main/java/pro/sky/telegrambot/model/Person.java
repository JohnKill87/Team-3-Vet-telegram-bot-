package pro.sky.telegrambot.model;

import pro.sky.telegrambot.model.shelter.Shelter;

import javax.persistence.*;
import java.util.Objects;

@MappedSuperclass
public abstract class Person {

//    Абстрактный класс для работы с данными пользователей и волонтеров
    @Id
    private Long id;
    private String firstName;
    private String phoneNumber;

    @JoinColumn(name = "shelter_id")
    @ManyToOne
    private Shelter shelter;

    public Person() {
    }

    public Person(Long id, String firstName, String phoneNumber, Shelter shelter) {
        this.id = id;
        this.firstName = firstName;
        this.phoneNumber = phoneNumber;
        this.shelter = shelter;
    }

    public Person(String firstName, String phoneNumber) {
        this.firstName = firstName;
        this.phoneNumber = phoneNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Shelter getShelter() {
        return shelter;
    }

    public void setShelter(Shelter shelter) {
        this.shelter = shelter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id) && Objects.equals(firstName, person.firstName) && Objects.equals(phoneNumber, person.phoneNumber) && Objects.equals(shelter, person.shelter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, phoneNumber, shelter);
    }
}
