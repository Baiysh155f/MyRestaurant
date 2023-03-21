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
@Getter
@Setter
@Entity
@Table(name = "restaurants")
@NoArgsConstructor
public class Restaurant {
    @Id
    @SequenceGenerator(name = "res_id_gen",sequenceName = "res_id_seq",allocationSize = 1)
    @GeneratedValue(generator = "res_id_gen",strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotEmpty
    @Size(max = 32)
    private String name;
    @NotEmpty
    private String location;
    @NotEmpty
    private String restType;
    @NotEmpty
    @Size(max = 15,min = 1)
    private int numberOfEmployees;
    @NotEmpty
    private int service;
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Employees> employees = new ArrayList<>();
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<MenuItem> menuItem = new ArrayList<>();
}
