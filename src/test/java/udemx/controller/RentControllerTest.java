package udemx.controller;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.ui.Model;
import udemx.exception.CarServiceException;
import udemx.pojo.RentCalculationResponse;
import udemx.pojo.RentResponse;
import udemx.service.RentCalculatorService;
import udemx.service.RentService;

import java.sql.Date;
import java.time.format.DateTimeParseException;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class RentControllerTest {


    @Test
    public void test_rental_calculation_success() {
        RentCalculatorService calculatorService = mock(RentCalculatorService.class);
        RentService rentService = mock(RentService.class);
        RentController controller = new RentController(calculatorService, rentService, null);

        String startDate = "2023-01-01";
        String endDate = "2023-01-03";
        long price = 100;

        RentCalculationResponse response = RentCalculationResponse.builder()
                .totalRentDays(3)
                .totalPrice(300)
                .build();

        when(calculatorService.rentCalculation(startDate, endDate, price)).thenReturn(response);

        Model model = mock(Model.class);
        String viewName = controller.submitItem(1L, startDate, endDate, price, model);

        verify(model).addAttribute("price", 300L);
        verify(model).addAttribute("numberOfRentDays", 3L);
        assertEquals("rentForm", viewName);
    }

    @Test
    public void test_form_submission_success() throws CarServiceException {
        RentCalculatorService calculatorService = mock(RentCalculatorService.class);
        RentService rentService = mock(RentService.class);
        RentController controller = new RentController(calculatorService, rentService, null);

        RentResponse rentResponse = RentResponse.builder()
                .name("John")
                .startDate(Date.valueOf("2023-01-01"))
                .endDate(Date.valueOf("2023-01-03"))
                .price(300)
                .build();

        when(rentService.saveRent(anyString(), anyString(), anyString(), anyLong(),
                anyString(), anyString(), anyString(), anyInt(), anyLong())).thenReturn(rentResponse);

        Model model = mock(Model.class);
        String viewName = controller.submitForm("John", "john@email.com", "Address",
                "2023-01-01", "2023-01-03", 1L, "1234567890", 300, 3L, model);

        verify(model).addAttribute("rentResponse", rentResponse);
        assertEquals("success", viewName);
    }

    // Model attributes are correctly set after rental calculation
    @Test
    public void test_model_attributes_set_correctly() {
        RentCalculatorService calculatorService = mock(RentCalculatorService.class);
        RentService rentService = mock(RentService.class);
        RentController controller = new RentController(calculatorService, rentService, null);

        RentCalculationResponse response = RentCalculationResponse.builder()
                .totalRentDays(5)
                .totalPrice(500)
                .build();

        when(calculatorService.rentCalculation(anyString(), anyString(), anyLong())).thenReturn(response);

        Model model = mock(Model.class);
        controller.submitItem(1L, "2023-01-01", "2023-01-05", 100L, model);

        verify(model).addAttribute("numberOfRentDays", 5L);
        verify(model).addAttribute("price", 500L);
        verify(model).addAttribute("carId", 1L);
        verify(model).addAttribute("startDate", "2023-01-01");
        verify(model).addAttribute("endDate", "2023-01-05");
    }

    @Test
    public void test_view_names_returned() throws CarServiceException {
        RentCalculatorService calculatorService = mock(RentCalculatorService.class);
        RentService rentService = mock(RentService.class);
        RentController controller = new RentController(calculatorService, rentService, null);

        when(calculatorService.rentCalculation(anyString(), anyString(), anyLong()))
                .thenReturn(RentCalculationResponse.builder().build());

        when(rentService.saveRent(anyString(), anyString(), anyString(), anyLong(),
                anyString(), anyString(), anyString(), anyInt(), anyLong()))
                .thenReturn(RentResponse.builder().build());

        Model model = mock(Model.class);

        String rentFormView = controller.submitItem(1L, "2023-01-01", "2023-01-03", 100L, model);
        String successView = controller.submitForm("name", "email", "address",
                "2023-01-01", "2023-01-03", 1L, "phone", 100, 3L, model);

        assertEquals("rentForm", rentFormView);
        assertEquals("success", successView);
    }

    @Test
    public void test_invalid_date_format() {
        RentCalculatorService calculatorService = new RentCalculatorService();
        RentService rentService = mock(RentService.class);
        RentController controller = new RentController(calculatorService, rentService, null);

        Model model = mock(Model.class);

        assertThrows(DateTimeParseException.class, () ->
                controller.submitItem(1L, "2023/01/01", "2023-01-03", 100L, model));
    }

    @Test
    public void test_negative_price() {
        RentCalculatorService calculatorService = new RentCalculatorService();
        RentService rentService = mock(RentService.class);
        RentController controller = new RentController(calculatorService, rentService, null);

        Model model = mock(Model.class);
        controller.submitItem(1L, "2023-01-01", "2023-01-03", -100L, model);

        ArgumentCaptor<Long> priceCaptor = ArgumentCaptor.forClass(Long.class);
        verify(model).addAttribute(eq("price"), priceCaptor.capture());

        assertTrue(priceCaptor.getValue() < 0);
    }

    @Test
    public void test_end_date_before_start_date() {
        RentCalculatorService calculatorService = new RentCalculatorService();
        RentService rentService = mock(RentService.class);
        RentController controller = new RentController(calculatorService, rentService, null);

        Model model = mock(Model.class);
        String startDate = "2023-01-03";
        String endDate = "2023-01-01";

        controller.submitItem(1L, startDate, endDate, 100L, model);

        ArgumentCaptor<Long> daysCaptor = ArgumentCaptor.forClass(Long.class);
        verify(model).addAttribute(eq("numberOfRentDays"), daysCaptor.capture());

        assertTrue(daysCaptor.getValue() < 0);
    }

    @Test
    public void test_invalid_car_id() throws CarServiceException {
        RentCalculatorService calculatorService = mock(RentCalculatorService.class);
        RentService rentService = mock(RentService.class);
        RentController controller = new RentController(calculatorService, rentService, null);

        when(rentService.saveRent(anyString(), anyString(), anyString(), eq(-1L),
                anyString(), anyString(), anyString(), anyInt(), anyLong()))
                .thenThrow(new CarServiceException("Invalid car ID"));

        Model model = mock(Model.class);

        assertThrows(CarServiceException.class, () ->
                controller.submitForm("name", "email", "address",
                        "2023-01-01", "2023-01-03", -1L, "phone", 100, 3L, model));
    }

    @Test
    public void test_invalid_email_format() throws CarServiceException {
        RentCalculatorService calculatorService = mock(RentCalculatorService.class);
        RentService rentService = mock(RentService.class);
        RentController controller = new RentController(calculatorService, rentService, null);

        when(rentService.saveRent(anyString(), eq("invalid-email"), anyString(), anyLong(),
                anyString(), anyString(), anyString(), anyInt(), anyLong()))
                .thenThrow(new IllegalArgumentException("Invalid email format"));

        Model model = mock(Model.class);

        assertThrows(IllegalArgumentException.class, () ->
                controller.submitForm("name", "invalid-email", "address",
                        "2023-01-01", "2023-01-03", 1L, "phone", 100, 3L, model));
    }

}