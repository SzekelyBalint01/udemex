package udemx.exception;


public class CarServiceException extends Exception {
    public CarServiceException(String message) { super(message); }
    public CarServiceException(String message, Throwable cause) { super(message, cause); }
    public CarServiceException(Throwable cause) { super(cause); }

    public CarServiceException(String message, long id) {
        super(message);
    }
}
