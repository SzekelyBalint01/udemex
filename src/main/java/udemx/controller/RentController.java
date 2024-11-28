package udemx.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import udemx.exception.CarServiceException;
import udemx.pojo.RentCalculationResponse;
import udemx.service.RentCalculatorService;
import udemx.service.RentService;

import java.util.Arrays;

@Controller
public class RentController {

    private final RentCalculatorService rentCalculatorService;
    private final RentService rentService;

    public RentController(RentCalculatorService rentCalculatorService, RentService rentService) {
        this.rentCalculatorService = rentCalculatorService;
        this.rentService = rentService;
    }

    @PostMapping("/submitCar")
    public String submitItem(@RequestParam Long carId, @RequestParam String startDate, @RequestParam String endDate, @RequestParam int price , Model model) {

        RentCalculationResponse rentCalculationResponse = rentCalculatorService.rentCalculation(startDate, endDate, price);

        model.addAttribute("numberOfRentDays", rentCalculationResponse.getTotalRentDays());
        model.addAttribute("price", rentCalculationResponse.getTotalPrice());
        model.addAttribute("carId", carId);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);

        return "rentForm";
    }

    @PostMapping("/finishRent")
    public String submitForm(@RequestParam String name, @RequestParam String email, @RequestParam String address,
                                   @RequestParam String startDate, @RequestParam String endDate, @RequestParam Long carId,
                                   @RequestParam String phone, @RequestParam Integer price, Long rentDays, Model model){

        try {
            model.addAttribute("rentResponse", rentService.saveRent(name, email, address, carId, startDate,endDate, phone, price, rentDays));
        } catch (CarServiceException e) {

            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("errorStackTrace", Arrays.toString(e.getStackTrace()));

            return "error";
        }

        return "success";
    }
}
