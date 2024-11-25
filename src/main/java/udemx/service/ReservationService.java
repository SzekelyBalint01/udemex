package udemx.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import udemx.exception.ReservationServiceException;
import udemx.model.Reservation;
import udemx.repository.ReservationRepository;

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
}
