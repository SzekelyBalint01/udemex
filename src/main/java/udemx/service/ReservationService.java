package udemx.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import udemx.exception.ReservationServiceException;
import udemx.model.Car;
import udemx.model.Reservation;
import udemx.pojo.CarDto;
import udemx.pojo.ReservationDto;
import udemx.repository.ReservationRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Transactional
    public Reservation save(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public Reservation findById(long id) throws ReservationServiceException {
        return reservationRepository.findById(id).orElseThrow(()->new ReservationServiceException("No Reservation found with this id: {}",id));
    }

    public List<ReservationDto> getAllReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        return reservationMapper(reservations);
    }

    public List<ReservationDto> reservationMapper (List<Reservation> reservations){
        return reservations.stream()
                .map(reservation -> ReservationDto.builder()
                        .id(reservation.getId())
                        .startDate(reservation.getStartDate())
                        .endDate(reservation.getEndDate())
                        .rentDays(reservation.getRentDays())
                        .price(reservation.getPrice())
                        .car(reservation.getCar())
                        .user(reservation.getUser())
                        .build())
                .collect(Collectors.toList());
    }
}
