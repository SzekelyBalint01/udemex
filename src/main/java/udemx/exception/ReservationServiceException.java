package udemx.exception;

public class ReservationServiceException extends Exception {

    public ReservationServiceException(String message) { super(message); }
    public ReservationServiceException(String message, Throwable cause) { super(message, cause); }

    public ReservationServiceException(String message, long id) {
    }
}
