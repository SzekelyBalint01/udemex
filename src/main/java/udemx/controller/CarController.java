package udemx.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import udemx.exception.CarSearchServiceException;
import udemx.pojo.CarDto;
import udemx.service.CarSearchService;

import java.util.List;

@Controller
public class CarController {

    private final CarSearchService carSearchService;

    private final Logger logger = LoggerFactory.getLogger(CarController.class);

    public CarController(CarSearchService carSearchService) {
        this.carSearchService = carSearchService;
    }

    @PostMapping("/search")
    public String handleDates(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate, Model model) {
        try {
            List<CarDto> cars = carSearchService.availableCars(startDate, endDate);

            if (cars.isEmpty()) {
                model.addAttribute("error", "No available cars found");
                return "availableCarsList";
            }

            model.addAttribute("cars", cars);

        } catch (Exception e) {
            String errorMessage = (e instanceof CarSearchServiceException) ? e.getMessage() : "An unexpected error occurred";
            model.addAttribute("error", errorMessage);
            logger.error("Error during car availability search", e);
            return "availableCarsList";
        }

        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);

        logger.info("Dates recived successfully {} - {}", startDate, endDate);

        return "availableCarsList";
    }
}
