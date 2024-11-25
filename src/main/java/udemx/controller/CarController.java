package udemx.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import udemx.exception.CarSearchServiceException;
import udemx.service.CarSearchService;

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
            model.addAttribute("cars", carSearchService.availableCars(startDate, endDate));
        } catch (CarSearchServiceException e) {
            model.addAttribute("error", e.getMessage());
            logger.warn("Something went wrong while searching for cars :{}", e.getMessage());
            return "availableCarsList";
        }
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);

        logger.info("Dates recived successfully {} - {}", startDate, endDate);

        return "availableCarsList";
    }

}
