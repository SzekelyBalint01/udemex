package udemx.pojo;

import lombok.*;
import udemx.model.Car;
import udemx.model.User;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ReservationDto {

    private Long id;

    private Date startDate;

    private Date endDate;

    private Long rentDays;

    private Integer price;

    private Car car;

    private User user;

}
