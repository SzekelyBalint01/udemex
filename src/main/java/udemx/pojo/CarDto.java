package udemx.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarDto implements Serializable {
    private Long id;
    private String name;
    private byte[] photo;
    private Boolean active;
    private long price;
}