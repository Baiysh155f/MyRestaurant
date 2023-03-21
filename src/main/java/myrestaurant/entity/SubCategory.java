package myrestaurant.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Restorant
 * 2023
 * macbook_pro
 **/
@Entity
@Table(name = "sub_categories")
@Getter
@Setter
@NoArgsConstructor
public class SubCategory implements Comparable<SubCategory> {
    @Id
    @SequenceGenerator(name = "sub_id_gen", sequenceName = "sub_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "sub_id_gen", strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotEmpty
    @Size(max = 32)
    private String name;
    @OneToMany(mappedBy = "subCategory", cascade = CascadeType.ALL)
    private List<MenuItem> menuItem = new ArrayList<>();
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private Category category;

    @Override
    public int compareTo(SubCategory o) {
        return o.getName().compareTo(name);
    }
}
