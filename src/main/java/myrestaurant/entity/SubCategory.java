package myrestaurant.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SubCategory category = (SubCategory) o;
        return getId() != null && Objects.equals(getId(), category.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
