package udemx.controller;

import config.TestSecurityConfig;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import udemx.model.Car;
import udemx.model.User;
import udemx.pojo.CarResponseDto;
import udemx.pojo.ReservationDto;
import udemx.service.CarService;
import udemx.service.ReservationService;

import java.sql.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminController.class)
@Import(TestSecurityConfig.class)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CarService carService;

    @MockitoBean
    private ReservationService reservationService;

    @Test
    void testAdminPage() throws Exception {

        byte[] photo = "test-photo".getBytes();
        Car car = Car.builder()
                .id(1L)
                .name("CarName1")
                .price(10000)
                .active(true)
                .photo(photo)
                .build();

        User user = User.builder()
                .id(1L)
                .name("John Doe")
                .email("john.doe@example.com")
                .build();
        List<ReservationDto> mockReservations = List.of(
                ReservationDto.builder()
                        .id(1L)
                        .user(user)
                        .car(car)
                        .endDate(Date.valueOf("2024-12-10"))
                        .rentDays(500L)
                        .startDate(Date.valueOf("2024-12-01"))
                        .build(),
                ReservationDto.builder()
                        .id(2L)
                        .user(user)
                        .car(car)
                        .endDate(Date.valueOf("2024-12-20"))
                        .rentDays(300L)
                        .startDate(Date.valueOf("2024-12-15"))
                        .build()
        );
        List<CarResponseDto> mockCars = List.of(
                CarResponseDto.builder()
                        .id(1L)
                        .name("Car 1")
                        .price(10000)
                        .active(true)
                        .photo("photo1")
                        .build(),
                CarResponseDto.builder()
                        .id(2L)
                        .name("Car 2")
                        .price(15000)
                        .active(true)
                        .photo("photo2")
                        .build()
        );

        when(reservationService.getAllReservations()).thenReturn(mockReservations);
        when(carService.getAllCars()).thenReturn(mockCars);

        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(view().name("adminPage"))
                .andExpect(model().attribute("reservations", hasSize(2)))
                .andExpect(model().attribute("cars", hasSize(2)))
                .andExpect(model().attribute("reservations", is(mockReservations)))
                .andExpect(model().attribute("cars", is(mockCars)));

        Mockito.verify(reservationService, Mockito.times(1)).getAllReservations();
        Mockito.verify(carService, Mockito.times(1)).getAllCars();
    }
}
