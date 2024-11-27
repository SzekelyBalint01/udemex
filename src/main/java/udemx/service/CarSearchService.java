package udemx.service;

import org.springframework.stereotype.Service;
import udemx.exception.CarSearchServiceException;
import udemx.model.Car;
import udemx.pojo.CarDto;
import udemx.pojo.CarResponseDto;
import udemx.repository.CarRepository;
import java.sql.Date;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarSearchService {

    private final CarRepository carRepository;

    public CarSearchService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<CarResponseDto> availableCars (String startDate, String endDate) throws CarSearchServiceException {
        return carResponseMapper(carRepository.availableBetweenDate(Date.valueOf(startDate), Date.valueOf(endDate))
                .orElseThrow(() -> new CarSearchServiceException("Something went wrong while searching for cars")));
    }


    public List<CarResponseDto> carResponseMapper (List<Car> cars){
        return cars.stream()
                .map(car -> CarResponseDto.builder()
                        .id(car.getId())
                        .price(car.getPrice())
                        .photo(Base64.getEncoder().encodeToString(car.getPhoto()))
                        .name(car.getName())
                        .active(car.getActive())
                        .build())
                .collect(Collectors.toList());
    }
}
