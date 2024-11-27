package udemx.service;

import org.junit.jupiter.api.Test;
import udemx.pojo.RentCalculationResponse;

import static org.junit.jupiter.api.Assertions.*;

public class RentCalculatorServiceTest {

    private final RentCalculatorService rentCalculatorService = new RentCalculatorService();

    @Test
    public void testRentCalculation() {
        String startDate = "2024-11-01";
        String endDate = "2024-11-05";
        int pricePerDay = 100;

        RentCalculationResponse response = rentCalculatorService.rentCalculation(startDate, endDate, pricePerDay);

        assertEquals(5, response.getTotalRentDays(), "The total rent days should be 5.");
        assertEquals(500, response.getTotalPrice(), "The total price should be 500.");
    }

    @Test
    public void testDayCalculator() {
        String startDate = "2024-11-01";
        String endDate = "2024-11-05";

        int rentDays = rentCalculatorService.dayCalculator(startDate, endDate);

        assertEquals(5, rentDays, "The rent days calculation is incorrect.");
    }
}
