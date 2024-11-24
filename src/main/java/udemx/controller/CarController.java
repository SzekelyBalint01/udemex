package udemx.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import udemx.service.CarSearchService;



@Controller
public class CarController {

    private final CarSearchService carSearchService;

    public CarController(CarSearchService carSearchService) {
        this.carSearchService = carSearchService;
    }

    @PostMapping("/search")
    public String handleDates(@RequestParam("startDate") String startDate,
                              @RequestParam("endDate") String endDate,
                              Model model) {



        model.addAttribute("items", carSearchService.availableCars(startDate, endDate));
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);

        return "availableCarsList";
    }
}
