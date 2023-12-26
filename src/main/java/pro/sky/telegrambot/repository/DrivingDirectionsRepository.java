package pro.sky.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.telegrambot.model.shelter.DrivingDirections;

public interface DrivingDirectionsRepository extends JpaRepository<DrivingDirections,Long> {
}
