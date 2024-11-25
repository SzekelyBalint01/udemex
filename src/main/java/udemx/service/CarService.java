package udemx.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import udemx.exception.CarServiceException;
import udemx.model.Car;
import udemx.repository.CarRepository;

@Service
public class CarService {

    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Transactional
    public Car save(Car car) {
        return carRepository.save(car);
    }

    public Car findById(long id) throws CarServiceException {
        return carRepository.findById(id).orElseThrow(()->new CarServiceException("Car not find with this id {}", id));
    }
}
