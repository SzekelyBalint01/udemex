package udemx.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import udemx.exception.CarServiceException;
import udemx.model.Car;
import udemx.pojo.CarDto;
import udemx.pojo.CarResponseDto;
import udemx.repository.CarRepository;

import java.io.IOException;
import java.util.List;

@Service
public class CarService extends Mappers {

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


    public CarDto saveCar(Long carId, int price, MultipartFile photo, Boolean active, String name) throws IOException {
        return carMapper(carRepository.save(Car.builder()
                        .id(carId)
                        .name(name)
                        .active(active)
                        .photo(photo.getBytes())
                        .price(price)
                .build()));
    }

    public Car findById(long id) throws CarServiceException {
        return carRepository.findById(id).orElseThrow(()->new CarServiceException("Car not find with this id: " + id));
    }

    public List<CarResponseDto> getAllCars() {
        return carResponseMapper(carRepository.findAll());
    }
}
