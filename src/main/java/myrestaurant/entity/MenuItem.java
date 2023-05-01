package myrestaurant.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Restorant
 * 2023
 * macbook_pro
 **/
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "menu_items")
@EqualsAndHashCode
public class MenuItem {
    @Id
    @SequenceGenerator(
            name = "menuItem_id_gen",
            sequenceName = "menuItem_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "menuItem_id_gen"
    )
    private Long id;
    @NotEmpty
    @Size(max = 32)
    private String name;
    @NotEmpty
    private String images;
    @NotNull
    private BigDecimal price;
    @NotEmpty
    private String description;
    private boolean isVegetarian;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private Restaurant restaurant;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(name = "menu_items_cheques",
            joinColumns = @JoinColumn(name = "menu_item_id"),
            inverseJoinColumns = @JoinColumn(name = "cheques_id"))
    private List<Cheque> cheques = new ArrayList<>();

    @OneToMany(mappedBy = "menuItem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StopList> stopList = new ArrayList<>();

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "subcategory_id")
    private SubCategory subCategory;

    public MenuItem(Long id, String name, String images, BigDecimal price, String description, boolean vegetarian) {
    }
}
