package udemx.pojo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Builder
@Getter
@Setter
public class RentResponse {
    Date startDate;
    Date endDate;
    String name;
    int price;
}
