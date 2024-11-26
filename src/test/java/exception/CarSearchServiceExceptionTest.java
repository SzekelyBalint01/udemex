package exception;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import udemx.exception.CarSearchServiceException;
import udemx.model.Car;
import udemx.pojo.CarDto;
import udemx.repository.CarRepository;
import udemx.service.CarSearchService;

import java.sql.Date;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CarSearchServiceExceptionTest {

    @Mock
    private CarRepository carRepository;

    private CarSearchService carSearchService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        carSearchService = new CarSearchService(carRepository);
    }

    @Test
    public void testSearchForAvailableCar() throws CarSearchServiceException {
        when(carRepository.availableBetweenDate(Date.valueOf("2024-01-01"), Date.valueOf("2024-01-02")))
                .thenReturn(Optional.of(Collections.singletonList(new Car())));

        List<CarDto> cars = carSearchService.availableCars("2024-01-01", "2024-01-02");
        assertFalse(cars.isEmpty());
    }
}
