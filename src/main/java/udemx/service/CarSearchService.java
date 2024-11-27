package udemx.service;

import org.springframework.stereotype.Service;
import udemx.exception.CarSearchServiceException;
import udemx.pojo.CarResponseDto;
import udemx.repository.CarRepository;
import java.sql.Date;
import java.util.List;

@Service
public class CarSearchService extends Mappers {

    private final CarRepository carRepository;

    public CarSearchService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<CarResponseDto> availableCars (String startDate, String endDate) throws CarSearchServiceException {
        return carResponseMapper(carRepository.availableBetweenDate(Date.valueOf(startDate), Date.valueOf(endDate))
                .orElseThrow(() -> new CarSearchServiceException("Something went wrong while searching for cars")));
    }
}
