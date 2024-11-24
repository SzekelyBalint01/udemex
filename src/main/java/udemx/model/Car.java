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
public class Car implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Lob
    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "active",nullable = false)
    private Boolean active = true;

    @OneToMany(mappedBy = "car")
    private List<Reservation> reservations;

}