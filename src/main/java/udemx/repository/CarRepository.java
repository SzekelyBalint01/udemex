package udemx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import udemx.model.Car;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    @Query("SELECT c FROM Car c WHERE c.id NOT IN (SELECT r.car.id FROM Reservation r WHERE r.startDate <= :endDate AND r.endDate >= :startDate) AND c.active = TRUE")
    Optional<List<Car>> availableBetweenDate(Date startDate, Date endDate);
}

