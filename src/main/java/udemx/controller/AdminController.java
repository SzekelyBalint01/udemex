package udemx.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import udemx.model.Reservation;
import udemx.pojo.CarDto;
import udemx.pojo.CarResponseDto;
import udemx.pojo.ReservationDto;
import udemx.service.CarService;
import udemx.service.ReservationService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Controller
public class AdminController {

    private final CarService carService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ReservationService reservationService;

    public AdminController(CarService carService, ReservationService reservationService) {
        this.carService = carService;
        this.reservationService = reservationService;
    }

    @GetMapping("/admin")
    public String admin(Model model) {

        List<ReservationDto> reservations = Optional.ofNullable(reservationService.getAllReservations())
                .orElse(Collections.emptyList());

        logger.debug("Reservations: {}", reservations);

        List<CarResponseDto> cars = Optional.ofNullable(carService.getAllCars())
                .orElse(Collections.emptyList());

        logger.debug("Cars: {}", cars);

        model.addAttribute("reservations", reservations);
        model.addAttribute("cars", cars);

        return "adminPage";
    }

}
