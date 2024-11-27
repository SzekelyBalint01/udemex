package udemx.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import udemx.service.CarService;
import udemx.service.ReservationService;



@Controller
public class AdminController {

    private final CarService carService;

    private final ReservationService reservationService;

    public AdminController(CarService carService, ReservationService reservationService) {
        this.carService = carService;
        this.reservationService = reservationService;
    }

    @GetMapping("/admin")
    public String admin(Model model) {

        model.addAttribute("reservations",  reservationService.getAllReservations());
        model.addAttribute("cars", carService.getAllCars());

        return "adminPage";
    }

}
