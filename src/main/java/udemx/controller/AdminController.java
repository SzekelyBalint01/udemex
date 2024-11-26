package udemx.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import udemx.model.Car;
import udemx.model.Reservation;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {

    @GetMapping("/admin")
    public String admin(Model model) {

        List<Reservation> reservations = new ArrayList<Reservation>();
        List<Car> cars = new ArrayList<>();

        model.addAttribute("reservations", reservations);
        model.addAttribute("cars", cars);

        return "adminPage";
    }

}
