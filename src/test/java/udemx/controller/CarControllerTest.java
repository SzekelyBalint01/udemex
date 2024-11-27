package udemx.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import udemx.pojo.CarDto;
import udemx.pojo.CarResponseDto;
import udemx.service.CarSearchService;
import udemx.service.CarService;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class CarControllerTest {

    private MockMvc mockMvc;
    private CarSearchService carSearchService;

    @BeforeEach
    void setup() {
        carSearchService = mock(CarSearchService.class);
        CarService carService = mock(CarService.class);
        CarController carController = new CarController(carSearchService, carService);
        mockMvc = MockMvcBuilders.standaloneSetup(carController).build();
    }

    @Test
    public void testHandleDates_withAvailableCars() throws Exception {
        List<CarResponseDto> cars = List.of(new CarResponseDto(1L, "Test Car", null, true, 100L));

        when(carSearchService.availableCars("2023-01-01", "2023-01-02")).thenReturn(cars);

        mockMvc.perform(post("/search")
                        .param("startDate", "2023-01-01")
                        .param("endDate", "2023-01-02"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("cars"))
                .andExpect(model().attribute("cars", cars))
                .andExpect(view().name("availableCarsList"));
    }

    @Test
    public void testHandleDates_noAvailableCars() throws Exception {
        when(carSearchService.availableCars("2023-01-01", "2023-01-02")).thenReturn(List.of());

        mockMvc.perform(post("/search")
                        .param("startDate", "2023-01-01")
                        .param("endDate", "2023-01-02"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("error"))
                .andExpect(model().attribute("error", "No available cars found"))
                .andExpect(view().name("availableCarsList"));
    }

    @Test
    public void testHandleDates_serviceException() throws Exception {
        when(carSearchService.availableCars("2023-01-01", "2023-01-02"))
                .thenThrow(new RuntimeException("Service error"));

        mockMvc.perform(post("/search")
                        .param("startDate", "2023-01-01")
                        .param("endDate", "2023-01-02"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("error"))
                .andExpect(model().attribute("error", "An unexpected error occurred"))
                .andExpect(view().name("availableCarsList"));
    }
}
