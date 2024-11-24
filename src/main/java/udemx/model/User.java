package udemx.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "cars")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 255)
    private String name;

    @Column(name = "email", length = 255)
    private String email;

    @Column(name = "address", length = 255)
    private String address;

    @Column(name = "phone", length = 17)
    private String phone;

    @Column(name = "rent_days")
    private Integer rentDays;

    @Column(name = "price")
    private Integer price;

    @OneToMany(mappedBy = "user")
    private List<Reservation> reservations;
}
