package udemx.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarResponseDto {
    private Long id;
    private String name;
    private String photo;
    private Boolean active;
    private long price;
}
