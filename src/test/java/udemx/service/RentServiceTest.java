package udemx.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import udemx.exception.CarServiceException;
import udemx.model.Car;
import udemx.model.Reservation;
import udemx.model.User;
import udemx.pojo.RentResponse;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RentServiceTest {

    private UserService userService;
    private CarService carService;
    private ReservationService reservationService;
    private RentService rentService;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        carService = mock(CarService.class);
        reservationService = mock(ReservationService.class);
        rentService = new RentService(reservationService, userService, carService);
    }

    @Test
    void testSaveRent() throws CarServiceException {

        String name = "John Doe";
        String email = "john.doe@example.com";
        String address = "123 Main Street";
        long carId = 1L;
        String startDate = "2024-12-01";
        String endDate = "2024-12-10";
        String phone = "1234567890";
        Integer price = 500;
        Long rentDays = 10L;

        User mockUser = User.builder()
                .id(1L)
                .name(name)
                .email(email)
                .address(address)
                .phone(phone)
                .build();

        byte[] photo = "test-photo".getBytes();
        Car car = Car.builder()
                .id(1L)
                .name("CarName1")
                .price(10000)
                .active(true)
                .photo(photo)
                .build();

        Reservation mockReservation = Reservation.builder()
                .id(1L)
                .user(mockUser)
                .car(car)
                .startDate(Date.valueOf(startDate))
                .endDate(Date.valueOf(endDate))
                .rentDays(rentDays)
                .price(price)
                .build();


        when(userService.saveUser(any(User.class))).thenReturn(mockUser);
        when(carService.findById(carId)).thenReturn(car);
        when(reservationService.save(any(Reservation.class))).thenReturn(mockReservation);


        RentResponse response = rentService.saveRent(name, email, address, carId, startDate, endDate, phone, price, rentDays);


        assertEquals(name, response.getName());
        assertEquals(Date.valueOf(startDate), response.getStartDate());
        assertEquals(Date.valueOf(endDate), response.getEndDate());
        assertEquals(price, response.getPrice());


        verify(userService, times(1)).saveUser(any(User.class));
        verify(carService, times(1)).findById(carId);
        verify(reservationService, times(1)).save(any(Reservation.class));
    }
}
