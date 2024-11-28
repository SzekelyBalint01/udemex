package udemx.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import udemx.exception.CarSearchServiceException;
import udemx.exception.CarServiceException;
import udemx.pojo.CarDto;
import udemx.pojo.CarResponseDto;
import udemx.service.CarSearchService;
import udemx.service.CarService;
import udemx.service.Mappers;

import java.io.IOException;
import java.util.List;

@Controller
public class CarController extends Mappers {

    private final CarSearchService carSearchService;

    private final CarService carService;

    private final Logger logger = LoggerFactory.getLogger(CarController.class);

    public CarController(CarSearchService carSearchService, CarService carService) {
        this.carSearchService = carSearchService;
        this.carService = carService;
    }

    @PostMapping("/search")
    public String handleDates(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate, Model model) {
        try {
            List<CarResponseDto> cars = carSearchService.availableCars(startDate, endDate);

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

    @PostMapping("/car/update")
    public String updateCar(@RequestParam("name") String name,
                            @RequestParam("photo") MultipartFile photoFile,
                            @RequestParam("active") Boolean active,
                            @RequestParam("price") Integer price,
                            @RequestParam("carId") Long carId, Model model) {
        try {
            carService.saveCar(carId, price, photoFile, active, name);
        } catch (IOException e) {
            model.addAttribute("error", "Something went wrong, while saving the car");
            logger.error("Error during car update", e);
            return "redirect:/admin";
        }
        model.addAttribute("message", "Car updated successfully");
        return "redirect:/admin";
    }


    @GetMapping("/car/new")
    public String newCarForm() {
        return "newCar";
    }


    @GetMapping("/car/edit")
    public String editCar(@RequestParam(value = "id", required = false) Long id, Model model) {

        CarResponseDto car;
            try {
                car = carResponseMapper(carService.findById(id));
            } catch (CarServiceException e) {
                model.addAttribute("error", e.getMessage());
                logger.error("Error during car edit", e);
                return "editCar";
            }

            model.addAttribute("car", car);
            return "editCar";
    }

    @PostMapping("/car/newUpload")
    public String uploadCar(@RequestParam("name") String name,
                            @RequestParam("photo") MultipartFile photoFile,
                            @RequestParam("active") Boolean active,
                            @RequestParam("price") Integer price, Model model) {
        CarDto car;

        try {
            car = carService.saveNewCar(name, price, photoFile, active);
            //Later for pop up notification for about new car details
            model.addAttribute("newCar", car);
        } catch (Exception e) {
            logger.error("Error during car upload", e);
            model.addAttribute("error", "Something went wrong, while saving the car");
            return "redirect:/admin";
        }
        return "redirect:/admin";
    }

}
