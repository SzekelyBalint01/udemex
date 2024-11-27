package udemx.service;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;
import udemx.controller.RentController;
import udemx.exception.CarServiceException;
import udemx.pojo.RentCalculationResponse;
import udemx.pojo.RentResponse;

import java.sql.Date;
import java.time.format.DateTimeParseException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@SpringBootTest(
        classes = {
                RentCalculationResponse.class,
        }
)
public class ServiceTest {

    private final RentCalculatorService rentCalculatorService = mock(RentCalculatorService.class);
    private final RentService rentService = mock(RentService.class);
    private final Model model = mock(Model.class);
    private final RentController rentController = new RentController(rentCalculatorService, rentService);


    @Test
    public void test_valid_dates_and_price_calculation() {
        Model model = mock(Model.class);

        when(rentCalculatorService.rentCalculation("2023-01-01", "2023-01-03", 100))
                .thenReturn(RentCalculationResponse.builder()
                        .totalRentDays(3L)
                        .totalPrice(300)
                        .build());

        rentController.submitItem(1L, "2023-01-01", "2023-01-03", 100, model);

        verify(model).addAttribute("numberOfRentDays", 3L);
        verify(model).addAttribute("price", 300);
    }

    /*
    @Test
    public void test_integration_with_calculator_service() {
        Model model = mock(Model.class);

        rentController.submitItem(1L, "2023-01-01", "2023-01-02", 100, model);

        verify(rentCalculatorService).rentCalculation("2023-01-01", "2023-01-02", 100);
    }
    */


    @Test
    public void test_extremely_large_price() {
        Model model = mock(Model.class);
        int largePrice = Integer.MAX_VALUE;

        when(rentCalculatorService.rentCalculation(anyString(), anyString(), eq(largePrice)))
                .thenReturn(RentCalculationResponse.builder()
                        .totalRentDays(1L)
                        .totalPrice(largePrice)
                        .build());

        rentController.submitItem(1L, "2023-01-01", "2023-01-01", largePrice, model);

        verify(model).addAttribute("price", largePrice);
    }

    @Test
    public void test_null_parameters() {
        Model model = mock(Model.class);

        assertThrows(NullPointerException.class, () ->
                rentController.submitItem(null, null, null, 100, model));
    }

    @Test
    public void test_model_attributes_match_inputs() {
        Model model = mock(Model.class);

        String startDate = "2023-12-01";
        String endDate = "2023-12-05";
        Long carId = 555L;
        int price = 150;

        when(rentCalculatorService.rentCalculation(startDate, endDate, price))
                .thenReturn(RentCalculationResponse.builder()
                        .totalRentDays(5L)
                        .totalPrice(750)
                        .build());

        rentController.submitItem(carId, startDate, endDate, price, model);

        verify(model).addAttribute("carId", carId);
        verify(model).addAttribute("startDate", startDate);
        verify(model).addAttribute("endDate", endDate);
        verify(model, times(1)).addAttribute(eq("startDate"), eq(startDate));
        verify(model, times(1)).addAttribute(eq("endDate"), eq(endDate));
    }


    @Test
    public void test_rental_calculation_success() {
        String startDate = "2024-01-01";
        String endDate = "2024-01-03";
        int price = 100;
        Long carId = 1L;

        RentCalculationResponse calculationResponse = RentCalculationResponse.builder()
                .totalRentDays(3)
                .totalPrice(300)
                .build();

        when(rentCalculatorService.rentCalculation(startDate, endDate, price)).thenReturn(calculationResponse);

        String viewName = rentController.submitItem(carId, startDate, endDate, price, model);

        verify(model).addAttribute("numberOfRentDays", 3L);
        verify(model).addAttribute("price", 300);
        assertEquals("rentForm", viewName);
    }

    @Test
    public void test_form_submission_success() throws CarServiceException {
        String name = "John Doe";
        String email = "john@test.com";
        String address = "Test Address";
        long carId = 1L;
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
    public void test_valid_car_id_lookup() throws CarServiceException {
        String name = "John";
        String email = "john@test.com";
        String address = "Test";
        long carId = 1L;
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
        int price = 100;
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
    public void test_null_parameters_form_submission() throws CarServiceException {
        String email = "test@test.com";
        String address = "Test";
        long carId = 1L;
        String startDate = "2024-01-01";
        String endDate = "2024-01-03";
        String phone = "1234567890";
        Integer price = 300;
        Long rentDays = 3L;

        when(rentService.saveRent(null, email, address, carId, startDate, endDate, phone, price, rentDays))
                .thenThrow(new IllegalArgumentException("Name cannot be null"));

        assertThrows(IllegalArgumentException.class,
                () -> rentController.submitForm(null, email, address, startDate, endDate, carId, phone, price, rentDays, model));
    }

    @Test
    public void test_invalid_car_id() throws CarServiceException {
        String name = "John";
        String email = "john@test.com";
        String address = "Test";
        long carId = 999L;
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

    @Test
    public void test_large_price_values() {
        String startDate = "2024-01-01";
        String endDate = "2025-01-01";
        int price = Integer.MAX_VALUE / 365;
        Long carId = 1L;

        RentCalculationResponse calculationResponse = RentCalculationResponse.builder()
                .totalRentDays(366)
                .totalPrice(Integer.MAX_VALUE / 365 * 366)
                .build();

        when(rentCalculatorService.rentCalculation(startDate, endDate, price)).thenReturn(calculationResponse);

        String viewName = rentController.submitItem(carId, startDate, endDate, price, model);

        assertEquals("rentForm", viewName);
    }

    @Test
    public void test_special_characters_in_input() throws CarServiceException {
        String name = "John<script>";
        String email = "john@test.com";
        String address = "Test Address & More";
        long carId = 1L;
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

    @Test
    public void test_transaction_rollback() throws CarServiceException {
        String name = "John";
        String email = "john@test.com";
        String address = "Test";
        long carId = 1L;
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

    @Test
    public void test_model_attributes_set_correctly() {
        Model model = mock(Model.class);

        RentCalculationResponse response = RentCalculationResponse.builder()
                .totalRentDays(3L)
                .totalPrice(300)
                .build();

        when(rentCalculatorService.rentCalculation(any(), any(), anyInt()))
                .thenReturn(response);

        rentController.submitItem(1L, "2023-01-01", "2023-01-03", 100, model);

        verify(model).addAttribute("numberOfRentDays", 3L);
        verify(model).addAttribute("price", 300);
        verify(model).addAttribute("carId", 1L);
        verify(model).addAttribute("startDate", "2023-01-01");
        verify(model).addAttribute("endDate", "2023-01-03");
    }

    @Test
    public void test_returns_correct_view_name() {

        Model model = mock(Model.class);

        when(rentCalculatorService.rentCalculation(any(), any(), anyInt()))
                .thenReturn(RentCalculationResponse.builder().build());

        String viewName = rentController.submitItem(1L, "2023-01-01", "2023-01-02", 100, model);

        assertEquals("rentForm", viewName);
    }

    /*
    @Test
    public void test_integration_with_rent_calculator_service() {
        Model model = mock(Model.class);

        rentController.submitItem(1L, "2023-01-01", "2023-01-02", 100, model);

        verify(rentCalculatorService).rentCalculation("2023-01-01", "2023-01-02", 100);
    }
*/


    @Test
    public void test_valid_car_id_processing() {

        Model model = mock(Model.class);
        Long carId = 12345L;

        when(rentCalculatorService.rentCalculation(any(), any(), anyInt()))
                .thenReturn(RentCalculationResponse.builder().build());

        rentController.submitItem(carId, "2023-01-01", "2023-01-02", 100, model);

        verify(model).addAttribute("carId", carId);
    }

    @Test
    public void test_invalid_date_format() {

        Model model = mock(Model.class);

        when(rentCalculatorService.rentCalculation("invalid-date", "2023-01-02", 100))
                .thenThrow(new DateTimeParseException("Invalid date format", "invalid-date", 0));

        assertThrows(DateTimeParseException.class, () ->
                rentController.submitItem(1L, "invalid-date", "2023-01-02", 100, model));
    }

    @Test
    public void test_negative_price_values() {

        Model model = mock(Model.class);

        when(rentCalculatorService.rentCalculation(any(), any(), eq(-100)))
                .thenThrow(new IllegalArgumentException("Price cannot be negative"));

        assertThrows(IllegalArgumentException.class, () ->
                rentController.submitItem(1L, "2023-01-01", "2023-01-02", -100, model));
    }

    @Test
    public void test_end_date_before_start_date() {
        Model model = mock(Model.class);

        when(rentCalculatorService.rentCalculation("2023-01-02", "2023-01-01", 100))
                .thenThrow(new IllegalArgumentException("End date cannot be before start date"));

        assertThrows(IllegalArgumentException.class, () ->
                rentController.submitItem(1L, "2023-01-02", "2023-01-01", 100, model));
    }

    @Test
    public void test_extremely_large_price_values() {

        Model model = mock(Model.class);
        int largePrice = Integer.MAX_VALUE;

        RentCalculationResponse response = RentCalculationResponse.builder()
                .totalRentDays(1L)
                .totalPrice(largePrice)
                .build();

        when(rentCalculatorService.rentCalculation(any(), any(), eq(largePrice)))
                .thenReturn(response);

        String result = rentController.submitItem(1L, "2023-01-01", "2023-01-01", largePrice, model);
        verify(model).addAttribute("price", largePrice);
        assertEquals("rentForm", result);
    }

    @Test
    public void test_null_request_parameters() {
        Model model = mock(Model.class);

        assertThrows(NullPointerException.class, () ->
                rentController.submitItem(null, null, null, 100, model));
    }

    @Test
    public void test_empty_string_dates() {
        Model model = mock(Model.class);

        when(rentCalculatorService.rentCalculation("", "", 100))
                .thenThrow(new DateTimeParseException("Empty date string", "", 0));

        assertThrows(DateTimeParseException.class, () ->
                rentController.submitItem(1L, "", "", 100, model));
    }

    @Test
    public void test_model_attributes_match_input_parameters() {
        Model model = mock(Model.class);

        String startDate = "2023-01-01";
        String endDate = "2023-01-03";
        Long carId = 999L;

        RentCalculationResponse response = RentCalculationResponse.builder()
                .totalRentDays(3L)
                .totalPrice(300)
                .build();

        when(rentCalculatorService.rentCalculation(startDate, endDate, 100))
                .thenReturn(response);

        rentController.submitItem(carId, startDate, endDate, 100, model);

        verify(model).addAttribute(eq("startDate"), eq(startDate));
        verify(model).addAttribute(eq("endDate"), eq(endDate));
        verify(model).addAttribute(eq("carId"), eq(carId));
    }


}