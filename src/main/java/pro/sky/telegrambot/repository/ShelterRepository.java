package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.model.shelter.Shelter;

import java.util.Optional;

public interface ShelterRepository extends JpaRepository<Shelter, Long> {
}
