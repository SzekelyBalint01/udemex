package udemx.pojo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class RentCalculationResponse {
    int totalPrice;
    long totalRentDays;

    public static RentCalculationResponse create(int totalRentDays, long totalPrice) {
        return new RentCalculationResponse(totalRentDays, totalPrice);
    }
}
