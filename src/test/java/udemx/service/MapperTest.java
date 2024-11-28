package udemx.service;

import org.junit.jupiter.api.Test;
import udemx.model.Car;
import udemx.pojo.CarDto;
import udemx.pojo.CarResponseDto;

import java.util.Base64;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MappersTest {

    private final Mappers mappers = new Mappers();

    @Test
    void testCarResponseMapper_singleCar() {

        byte[] photo = "test-photo".getBytes();
        Car car = Car.builder()
                .id(1L)
                .name("CarName1")
                .price(10000)
                .active(true)
                .photo(photo)
                .build();

        CarResponseDto result = mappers.carResponseMapper(car);

        assertEquals(car.getId(), result.getId());
        assertEquals(car.getName(), result.getName());
        assertEquals(car.getPrice(), result.getPrice());
        assertEquals(car.getActive(), result.getActive());
        assertEquals(Base64.getEncoder().encodeToString(photo), result.getPhoto());
    }

    @Test
    void testCarResponseMapper_listOfCars() {

        byte[] photo1 = "test-photo1".getBytes();
        byte[] photo2 = "test-photo2".getBytes();
        List<Car> cars = List.of(
                Car.builder()
                        .id(1L)
                        .name("CarName1")
                        .price(10000)
                        .active(true)
                        .photo(photo1)
                        .build(),Car.builder()
                        .id(1L)

                        .name("CarName2")
                        .price(10000)
                        .active(true)
                        .photo(photo2)
                        .build()
        );


        List<CarResponseDto> results = mappers.carResponseMapper(cars);


        assertEquals(2, results.size());
        assertEquals(Base64.getEncoder().encodeToString(photo1), results.get(0).getPhoto());
        assertEquals(Base64.getEncoder().encodeToString(photo2), results.get(1).getPhoto());
        assertEquals(cars.get(0).getId(), results.get(0).getId());
        assertEquals(cars.get(1).getId(), results.get(1).getId());
    }

    @Test
    void testCarMapper() {
        byte[] photo = "test-photo".getBytes();
        Car car = Car.builder()
                .id(1L)
                .name("CarName1")
                .price(10000)
                .active(true)
                .photo(photo)
                .build();

        CarDto result = mappers.carMapper(car);

        assertEquals(car.getId(), result.getId());
        assertEquals(car.getName(), result.getName());
        assertEquals(car.getPrice(), result.getPrice());
        assertEquals(car.getActive(), result.getActive());
        assertArrayEquals(car.getPhoto(), result.getPhoto());
    }
}
