package udemx.exception;

public class CarSearchServiceException extends Exception {
    public CarSearchServiceException(String message) { super(message); }
    public CarSearchServiceException(String message, Throwable cause) { super(message, cause); }
    public CarSearchServiceException(Throwable cause) { super(cause); }
}
