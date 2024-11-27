package udemx.service;

import org.springframework.stereotype.Service;
import udemx.pojo.RentCalculationResponse;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Service
public class RentCalculatorService {

    public RentCalculationResponse rentCalculation(String startDate, String endDate, int price) {
        int rentDays = dayCalculator(startDate, endDate);
        return RentCalculationResponse.builder()
                .totalRentDays(rentDays)
                .totalPrice(price*rentDays)
                .build();
    }

    int dayCalculator(String startDate, String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);
        return (int) (ChronoUnit.DAYS.between(start, end) + 1);  // +1 include endDtae
    }
}
