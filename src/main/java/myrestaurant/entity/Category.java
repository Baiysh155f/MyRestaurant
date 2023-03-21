package myrestaurant.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Restaurant
 * 2023
 * macbook_pro
 **/
@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
public class Category {
    @Id
    @SequenceGenerator(name = "category_id_gen",sequenceName = "category_id_seq",allocationSize = 1)
    @GeneratedValue(generator = "category_id_gen",strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotEmpty
    private String name;
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<SubCategory> subCategory = new ArrayList<>();
}
