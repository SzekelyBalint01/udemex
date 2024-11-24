package udemx.service;

import org.springframework.stereotype.Service;
import udemx.repository.CarRepository;
import udemx.repository.ReservationRepository;
import udemx.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarSearchService {

    private final CarRepository carRepository;
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;

    public CarSearchService(CarRepository carRepository, ReservationRepository reservationRepository, UserRepository userRepository) {
        this.carRepository = carRepository;
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
    }

    public List<String> availableCars (String startDate, String endDate) {

        List<String> availableCars = new ArrayList<>();

        return availableCars;
    }

}
