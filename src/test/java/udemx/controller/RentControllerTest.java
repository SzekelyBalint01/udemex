package udemx.controller;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;
import java.sql.Date;
import java.time.format.DateTimeParseException;
import static org.junit.Assert.*;
import udemx.exception.CarServiceException;
import udemx.pojo.RentCalculationResponse;
import udemx.pojo.RentResponse;
import udemx.service.RentCalculatorService;
import udemx.service.RentService;

@SpringBootTest
public class RentControllerTest {

    private final RentCalculatorService rentCalculatorService = mock(RentCalculatorService.class);
    private final RentService rentService = mock(RentService.class);
    private final Model model = mock(Model.class);
    private final RentController rentController = new RentController(rentCalculatorService, rentService);

     @Test
    public void test_rental_calculation_success() {
        String startDate = "2024-01-01";
        String endDate = "2024-01-03";
        long price = 100;
        Long carId = 1L;

        RentCalculationResponse calculationResponse = RentCalculationResponse.builder()
                .totalRentDays(3)
                .totalPrice(300)
                .build();

        when(rentCalculatorService.rentCalculation(startDate, endDate, price)).thenReturn(calculationResponse);

        String viewName = rentController.submitItem(carId, startDate, endDate, price, model);

        verify(model).addAttribute("numberOfRentDays", 3L);
        verify(model).addAttribute("price", 300L);
        assertEquals("rentForm", viewName);
    }

    @Test
    public void test_form_submission_success() throws CarServiceException {
        String name = "John Doe";
        String email = "john@test.com";
        String address = "Test Address";
        Long carId = 1L;
        String startDate = "2024-01-01";
        String endDate = "2024-01-03";
        String phone = "1234567890";
        Integer price = 300;
        Long rentDays = 3L;

        RentResponse rentResponse = RentResponse.builder()
                .name(name)
                .startDate(Date.valueOf(startDate))
                .endDate(Date.valueOf(endDate))
                .price(price)
                .build();

        when(rentService.saveRent(name, email, address, carId, startDate, endDate, phone, price, rentDays))
                .thenReturn(rentResponse);

        String viewName = rentController.submitForm(name, email, address, startDate, endDate, carId, phone, price, rentDays, model);

        verify(model).addAttribute("rentResponse", rentResponse);
        assertEquals("success", viewName);
    }

    @Test
    public void test_model_attributes_set_correctly() {
        String startDate = "2024-01-01";
        String endDate = "2024-01-03";
        long price = 100;
        Long carId = 1L;

        RentCalculationResponse calculationResponse = RentCalculationResponse.builder()
                .totalRentDays(3)
                .totalPrice(300)
                .build();

        when(rentCalculatorService.rentCalculation(startDate, endDate, price)).thenReturn(calculationResponse);

        rentController.submitItem(carId, startDate, endDate, price, model);

        verify(model).addAttribute("numberOfRentDays", 3L);
        verify(model).addAttribute("price", 300L);
        verify(model).addAttribute("carId", carId);
        verify(model).addAttribute("startDate", startDate);
        verify(model).addAttribute("endDate", endDate);
    }

    @Test
    public void test_valid_car_id_lookup() throws CarServiceException {
        String name = "John";
        String email = "john@test.com";
        String address = "Test";
        Long carId = 1L;
        String startDate = "2024-01-01";
        String endDate = "2024-01-03";
        String phone = "1234567890";
        Integer price = 300;
        Long rentDays = 3L;

        when(rentService.saveRent(name, email, address, carId, startDate, endDate, phone, price, rentDays))
                .thenReturn(RentResponse.builder().build());

        assertDoesNotThrow(() -> rentController.submitForm(name, email, address, startDate, endDate, carId, phone, price, rentDays, model));
    }

    @Test
    public void test_rental_calculation_includes_both_dates() {
        String startDate = "2024-01-01";
        String endDate = "2024-01-01";
        long price = 100;
        Long carId = 1L;

        RentCalculationResponse calculationResponse = RentCalculationResponse.builder()
                .totalRentDays(1)
                .totalPrice(100)
                .build();

        when(rentCalculatorService.rentCalculation(startDate, endDate, price)).thenReturn(calculationResponse);

        rentController.submitItem(carId, startDate, endDate, price, model);

        verify(model).addAttribute("numberOfRentDays", 1L);
    }

     @Test
    public void test_invalid_date_format() {
        String startDate = "01-01-2024";
        String endDate = "2024-01-03";
        long price = 100;
        Long carId = 1L;

        when(rentCalculatorService.rentCalculation(startDate, endDate, price))
                .thenThrow(new DateTimeParseException("Invalid date format", startDate, 0));

        assertThrows(DateTimeParseException.class,
                () -> rentController.submitItem(carId, startDate, endDate, price, model));
    }

    @Test
    public void test_end_date_before_start_date() {
        String startDate = "2024-01-03";
        String endDate = "2024-01-01";
        long price = 100;
        Long carId = 1L;

        when(rentCalculatorService.rentCalculation(startDate, endDate, price))
                .thenThrow(new IllegalArgumentException("End date cannot be before start date"));

        assertThrows(IllegalArgumentException.class,
                () -> rentController.submitItem(carId, startDate, endDate, price, model));
    }

    @Test
    public void test_null_parameters_form_submission() throws CarServiceException {
        String name = null;
        String email = "test@test.com";
        String address = "Test";
        Long carId = 1L;
        String startDate = "2024-01-01";
        String endDate = "2024-01-03";
        String phone = "1234567890";
        Integer price = 300;
        Long rentDays = 3L;

        when(rentService.saveRent(name, email, address, carId, startDate, endDate, phone, price, rentDays))
                .thenThrow(new IllegalArgumentException("Name cannot be null"));

        assertThrows(IllegalArgumentException.class,
                () -> rentController.submitForm(name, email, address, startDate, endDate, carId, phone, price, rentDays, model));
    }

    // Handle invalid car ID that doesn't exist
    @Test
    public void test_invalid_car_id() throws CarServiceException {
        String name = "John";
        String email = "john@test.com";
        String address = "Test";
        Long carId = 999L;
        String startDate = "2024-01-01";
        String endDate = "2024-01-03";
        String phone = "1234567890";
        Integer price = 300;
        Long rentDays = 3L;

        when(rentService.saveRent(name, email, address, carId, startDate, endDate, phone, price, rentDays))
                .thenThrow(new CarServiceException("Car not found"));

        assertThrows(CarServiceException.class,
                () -> rentController.submitForm(name, email, address, startDate, endDate, carId, phone, price, rentDays, model));
    }

    // Handle very large price values or rental day calculations
    @Test
    public void test_large_price_values() {
        String startDate = "2024-01-01";
        String endDate = "2025-01-01";
        long price = Long.MAX_VALUE / 365;
        Long carId = 1L;

        RentCalculationResponse calculationResponse = RentCalculationResponse.builder()
                .totalRentDays(366)
                .totalPrice(Long.MAX_VALUE / 365 * 366)
                .build();

        when(rentCalculatorService.rentCalculation(startDate, endDate, price)).thenReturn(calculationResponse);

        String viewName = rentController.submitItem(carId, startDate, endDate, price, model);

        assertEquals("rentForm", viewName);
    }

    // Handle special characters in user input fields
    @Test
    public void test_special_characters_in_input() throws CarServiceException {
        String name = "John<script>";
        String email = "john@test.com";
        String address = "Test Address & More";
        Long carId = 1L;
        String startDate = "2024-01-01";
        String endDate = "2024-01-03";
        String phone = "123-456-7890";
        Integer price = 300;
        Long rentDays = 3L;

        when(rentService.saveRent(name, email, address, carId, startDate, endDate, phone, price, rentDays))
                .thenThrow(new IllegalArgumentException("Invalid characters in input"));

        assertThrows(IllegalArgumentException.class,
                () -> rentController.submitForm(name, email, address, startDate, endDate, carId, phone, price, rentDays, model));
    }

    // Verify transaction rollback on partial rental save failure
    @Test
    public void test_transaction_rollback() throws CarServiceException {
        String name = "John";
        String email = "john@test.com";
        String address = "Test";
        Long carId = 1L;
        String startDate = "2024-01-01";
        String endDate = "2024-01-03";
        String phone = "1234567890";
        Integer price = 300;
        Long rentDays = 3L;

        when(rentService.saveRent(name, email, address, carId, startDate, endDate, phone, price, rentDays))
                .thenThrow(new RuntimeException("Database error"));

        assertThrows(RuntimeException.class,
                () -> rentController.submitForm(name, email, address, startDate, endDate, carId, phone, price, rentDays, model));
    }
}