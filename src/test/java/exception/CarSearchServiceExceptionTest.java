package exception;

import org.junit.Test;
import udemx.exception.CarSearchServiceException;

import static org.junit.Assert.*;

public class CarSearchServiceExceptionTest {

    @Test
    public void testCarSearchServiceExceptionWithMessage() {
        String message = "Error occurred while searching for car";
        CarSearchServiceException exception = new CarSearchServiceException(message);

        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
    }
}
