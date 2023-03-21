package myrestaurant.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Restorant
 * 2023
 * macbook_pro
 **/
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "stop_lists")
public class StopList {
    @Id
    @SequenceGenerator(name = "stop_id_gen",sequenceName = "stop_id_seq",allocationSize = 1)
    @GeneratedValue(generator = "stop_id_gen",strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotEmpty
    private String reason;
    @NotEmpty
    private LocalDate date;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private MenuItem menuItem;
}
