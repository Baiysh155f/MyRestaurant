package peaksoft.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * Restorant
 * 2023
 * macbook_pro
 **/
@Getter@Setter
@NoArgsConstructor
@Entity
@Table(name = "menu_items")
public class MenuItem {
    @Id
    @SequenceGenerator(name = "menu_id_gen",sequenceName = "menu_id_seq",allocationSize = 1)
    @GeneratedValue(generator = "menu_id_gen",strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String images;
    private BigDecimal price;
    private String description;
    private boolean isVegetarian;
    @OneToOne
    private StopList stopList;
    @ManyToOne
    private SubCategory subCategory;
    @ManyToMany(cascade = {CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
    private List<Cheque> cheque;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "cheques_id")
    private Cheque cheques;

}
