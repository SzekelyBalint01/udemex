package udemx.controller;

import config.TestSecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;
import udemx.pojo.CarDto;
import udemx.pojo.CarResponseDto;
import udemx.service.CarSearchService;
import udemx.service.CarService;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CarController.class)
@Import(TestSecurityConfig.class)
class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CarSearchService carSearchService;

    @MockitoBean
    private CarService carService;

    byte[] photo = "test-photo".getBytes();

 CarResponseDto carResponseDto = CarResponseDto.builder()
         .id(1L)
        .name("Car 1")
        .price(10000)
        .active(true)
        .photo("photo1")
        .build();

    @Test
    void testHandleDates_success() throws Exception {

        List<CarResponseDto> cars = List.of(new CarResponseDto(1L, "Car1", "photo", true, 1000));
        when(carSearchService.availableCars("2024-12-01", "2024-12-10")).thenReturn(cars);

        mockMvc.perform(post("/search")
                        .param("startDate", "2024-12-01")
                        .param("endDate", "2024-12-10"))
                .andExpect(status().isOk())
                .andExpect(view().name("availableCarsList"))
                .andExpect(model().attribute("cars", cars))
                .andExpect(model().attribute("startDate", "2024-12-01"))
                .andExpect(model().attribute("endDate", "2024-12-10"));
    }

    @Test
    void testHandleDates_noCars() throws Exception {

        when(carSearchService.availableCars("2024-12-01", "2024-12-10")).thenReturn(Collections.emptyList());

        mockMvc.perform(post("/search")
                        .param("startDate", "2024-12-01")
                        .param("endDate", "2024-12-10"))
                .andExpect(status().isOk())
                .andExpect(view().name("availableCarsList"))
                .andExpect(model().attribute("error", "No available cars found"));
    }

    @Test
    void testDeleteCar_success() throws Exception {
        mockMvc.perform(post("/car/delete")
                        .param("carId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin"))
                .andExpect(flash().attribute("message", "Car deleted successfully."));

        verify(carService, times(1)).deleteById(1L);
    }



    @Test
    void testUploadCar_success() throws Exception {


        MockMultipartFile photoFile = new MockMultipartFile("photo", "photo.jpg", "image/jpeg", "test photo".getBytes());
        CarDto newCar = new CarDto(1L, "Car1", photo, true, 1000);
        when(carService.saveNewCar(eq("Car1"), eq(10000), any(MultipartFile.class), eq(true))).thenReturn(newCar);

        mockMvc.perform(multipart("/car/newUpload")
                        .file(photoFile)
                        .param("name", "Car1")
                        .param("price", "10000")
                        .param("active", "true"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin"));

        verify(carService, times(1)).saveNewCar(eq("Car1"), eq(10000), any(MultipartFile.class), eq(true));
    }
}
