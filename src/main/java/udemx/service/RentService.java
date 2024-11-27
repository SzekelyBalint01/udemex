package udemx.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import udemx.exception.CarServiceException;
import udemx.model.Car;
import udemx.model.Reservation;
import udemx.model.User;
import udemx.pojo.CarDto;
import udemx.pojo.RentResponse;
import udemx.repository.CarRepository;
import udemx.repository.ReservationRepository;
import udemx.repository.UserRepository;

import java.sql.Date;

@Service
public class RentService {

    private  final ReservationService reservationService;
    private final UserService userService;
    private final CarService carService;

    public RentService(ReservationRepository reservationRepository, UserRepository userRepository, RentCalculatorService rentCalculatorService, CarRepository carRepository, ReservationService reservationService, UserService userService, CarService carService) {
        this.reservationService = reservationService;
        this.userService = userService;
        this.carService = carService;
    }

    @Transactional
    public RentResponse saveRent(String name,  String email,  String address, long carId, String startDate, String endDate, String phone, Integer price, Long rentDays) throws CarServiceException {

        User user = userService.saveUser(User.builder()
                .name(name)
                .email(email)
                .address(address)
                .phone(phone)
                .build());

        Car car = carService.findById(carId);

        Reservation reservation = reservationService.save(Reservation.builder()
                        .user(user)
                        .car(car)
                        .startDate(Date.valueOf(startDate))
                        .endDate(Date.valueOf(endDate))
                        .rentDays(rentDays)
                        .price(price)
                        .build());

        return RentResponse.builder()
                .name(user.getName())
                .startDate(reservation.getStartDate())
                .endDate(reservation.getEndDate())
                .price(reservation.getPrice())
                .build();
    }



}
