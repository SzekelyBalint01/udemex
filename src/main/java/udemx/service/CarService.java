package udemx.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import udemx.exception.CarServiceException;
import udemx.model.Car;
import udemx.pojo.CarDto;
import udemx.repository.CarRepository;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<CarDto> getAllCars() {
        return carMapper(carRepository.findAll());
    }

    public List<CarDto> carMapper (List<Car> cars){
        return cars.stream()
                .map(car -> new CarDto(
                        car.getId(),
                        car.getName(),
                        car.getPhoto(),
                        car.getActive(),
                        car.getPrice()
                ))
                .collect(Collectors.toList());
    }
}
