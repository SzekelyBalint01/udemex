package udemx.service;

import org.springframework.stereotype.Service;
import udemx.exception.CarSearchServiceException;
import udemx.model.Car;
import udemx.pojo.CarDto;
import udemx.repository.CarRepository;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarSearchService {

    private final CarRepository carRepository;

    public CarSearchService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<CarDto> availableCars (String startDate, String endDate) throws CarSearchServiceException {
        return carMapper(carRepository.availableBetweenDate(Date.valueOf(startDate), Date.valueOf(endDate))
                .orElseThrow(() -> new CarSearchServiceException("Something went wrong while searching for cars")));
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
