package udemx.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarDto{
    private Long id;
    private String name;
    private byte[] photo;
    private Boolean active;
    private long price;
}