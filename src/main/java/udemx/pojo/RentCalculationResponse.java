package udemx.pojo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class RentCalculationResponse {
    long totalPrice;
    long totalRentDays;
}
