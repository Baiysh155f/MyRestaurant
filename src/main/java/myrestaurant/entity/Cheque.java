package myrestaurant.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Restorant
 * 2023
 * macbook_pro
 **/
@Entity
@Table(name = "cheques")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Cheque {
    @Id
    @SequenceGenerator(name = "cheque_id_gen", sequenceName = "cheque_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "cheque_id_gen", strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotNull
    private BigDecimal priceAverage;
    @NotNull
    private LocalDate createAt;
    @ManyToOne
    private Employees employees;
    @ManyToMany(mappedBy = "cheques", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private List<MenuItem> menuItem = new ArrayList<>();
}
