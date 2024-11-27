package udemx.controller;

import config.TestSecurityConfig;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import udemx.pojo.RentCalculationResponse;
import udemx.pojo.RentResponse;
import udemx.service.RentCalculatorService;
import udemx.service.RentService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RentController.class)
@Import(TestSecurityConfig.class)
public class RentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RentCalculatorService rentCalculatorService;

    @MockitoBean
    private RentService rentService;

    @Test
    public void testSubmitItem() throws Exception {

        RentCalculationResponse mockResponse = RentCalculationResponse.create(500, 500L);

        when(rentCalculatorService.rentCalculation("2024-01-01", "2024-01-02", 100)).thenReturn(mockResponse);

        mockMvc.perform(post("/submitCar")
                        .param("carId", "1")
                        .param("startDate", "2024-01-01")
                        .param("endDate", "2024-01-02")
                        .param("price", "100"))
                .andExpect(status().isOk())
                .andExpect(view().name("rentForm"))
                .andExpect(model().attributeExists("numberOfRentDays", "price", "carId", "startDate", "endDate"))
                .andExpect(model().attribute("carId", 1L))
                .andExpect(model().attribute("startDate", "2024-01-01"))
                .andExpect(model().attribute("endDate", "2024-01-02"))
                .andExpect(model().attribute("price", 500));
    }

    @Test
    public void testSubmitForm() throws Exception {

        RentCalculationResponse mockResponse = RentCalculationResponse.create(500, 500L);

        RentResponse mockRentResponse = RentResponse.builder().build();

        when(rentService.saveRent(Mockito.anyString(),Mockito.anyString(),Mockito.anyString(),Mockito.anyLong(),Mockito.anyString(),Mockito.anyString(),Mockito.anyString(),Mockito.anyInt(),Mockito.anyLong()))
                .thenReturn(mockRentResponse);

        mockMvc.perform(post("/finishRent")
                        .param("name", "John Doe")
                        .param("email", "john.doe@example.com")
                        .param("address", "123 Main St")
                        .param("startDate", "2024-01-01")
                        .param("endDate", "2024-01-02")
                        .param("carId", "1")
                        .param("phone", "123-456-7890")
                        .param("price", "100")
                        .param("rentDays", "500"))
                .andExpect(status().isOk())
                .andExpect(view().name("success"))
                .andExpect(model().attributeExists("rentResponse"))
                .andExpect(model().attribute("rentResponse", mockRentResponse));
    }
}
