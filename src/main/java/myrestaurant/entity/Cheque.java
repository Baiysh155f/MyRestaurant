package peaksoft.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
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
public class Cheque {
    @Id
    @SequenceGenerator(name = "cheque_id_gen",sequenceName = "cheque_id_seq")
    @GeneratedValue(generator = "cheque_id_gen",strategy = GenerationType.SEQUENCE)
    private Long id;
    private BigDecimal priceAverage;
    private LocalDate createAt;
    @ManyToOne
    private Employees employees;
    @OneToMany(mappedBy = "cheques", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private List<MenuItem> menuItem;
}
