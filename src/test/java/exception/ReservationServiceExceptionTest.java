package exception;
import org.junit.jupiter.api.Test;
import udemx.exception.ReservationServiceException;

import static org.junit.jupiter.api.Assertions.*;

public class ReservationServiceExceptionTest {

  @Test
  public void testExceptionWithMessage() {
    String message = "Error in reservation service";
    ReservationServiceException exception = new ReservationServiceException(message);

    assertNotNull(exception);
    assertEquals(message, exception.getMessage());
  }

  @Test
  public void testExceptionWithMessageAndCause() {
    String message = "Error with cause";
    Throwable cause = new Throwable("Cause of the error");
    ReservationServiceException exception = new ReservationServiceException(message, cause);

    assertNotNull(exception);
    assertEquals(message, exception.getMessage());
    assertEquals(cause, exception.getCause());
  }

}
