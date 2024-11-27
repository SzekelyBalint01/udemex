package udemx.service;

import udemx.model.Car;
import udemx.pojo.CarDto;
import udemx.pojo.CarResponseDto;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

public class Mappers {

    public List<CarResponseDto> carResponseMapper (List<Car> cars){
        return cars.stream()
                .map(car -> CarResponseDto.builder()
                        .id(car.getId())
                        .price(car.getPrice())
                        .photo(Base64.getEncoder().encodeToString(car.getPhoto()))
                        .name(car.getName())
                        .active(car.getActive())
                        .build())
                .collect(Collectors.toList());
    }

    public List<CarDto> carListMapper(List<Car> cars){
        return cars.stream()
                .map(car -> new CarDto(
                        car.getId(),
                        car.getName(),
                        car.getPhoto(),
                        car.getActive(),
                        car.getPrice()
                ))
                .collect(Collectors.toList());
    }


    public CarDto carMapper(Car car){
        return CarDto.builder()
                .id(car.getId())
                .price(car.getPrice())
                .photo(car.getPhoto())
                .active(car.getActive())
                .name(car.getName())
                .build();
    }
}
