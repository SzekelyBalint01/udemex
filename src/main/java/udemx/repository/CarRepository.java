package udemx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import udemx.model.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
}
