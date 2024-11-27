package udemx.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import udemx.exception.CarServiceException;
import udemx.model.Car;
import udemx.pojo.CarDto;
import udemx.repository.CarRepository;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarService {

    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Transactional
    public CarDto saveNewCar(String name, int price, MultipartFile photo, Boolean active) throws IOException {

        Car carforSave = Car.builder()
                .name(name)
                .price(price)
                .photo(photo.getBytes())
                .active(active)
                .build();

        return carMapper(carRepository.save(carforSave));
    }

    public Car findById(long id) throws CarServiceException {
        return carRepository.findById(id).orElseThrow(()->new CarServiceException("Car not find with this id {}", id));
    }

    public List<CarDto> getAllCars() {
        return carListMapper(carRepository.findAll());
    }

    public List<CarDto> carListMapper(List<Car> cars){
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


    public CarDto carMapper(Car car){
        return CarDto.builder()
                .id(car.getId())
                .price(car.getPrice())
                .photo(car.getPhoto())
                .active(car.getActive())
                .name(car.getName())
                .build();
    }
}
