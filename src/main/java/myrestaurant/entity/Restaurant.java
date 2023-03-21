package peaksoft.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String name;
    private String location;
    private String restType;
    @Size(min = 1,max = 15,message = "Sorry No Vacancy...")
    private int numberOfEmployees;
    private int service;
    @OneToMany(mappedBy = "restaurant", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private List<Employees> employees;
    @OneToMany(mappedBy = "restaurant", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private List<MenuItem> menuItem;
}
