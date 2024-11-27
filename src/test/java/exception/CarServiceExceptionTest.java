package exception;

import org.junit.jupiter.api.Test;
import udemx.exception.CarServiceException;

import static org.junit.jupiter.api.Assertions.*;

public class CarServiceExceptionTest {

    @Test
    public void testCarServiceExceptionWithMessage() {
        String message = "Error occurred in the car service";
        CarServiceException exception = new CarServiceException(message);

        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
    }
}
