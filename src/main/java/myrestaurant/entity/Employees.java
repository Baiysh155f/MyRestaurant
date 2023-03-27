package myrestaurant.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import myrestaurant.enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Restorant
 * 2023
 * macbook_pro
 **/
@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class Employees implements UserDetails {
    @Id
    @SequenceGenerator(name = "emp_id_gen", sequenceName = "emp_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "emp_id_gen", strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotEmpty
    @Size(max = 32)
    private String firstName;
    @NotEmpty
    @Size(max = 32)
    private String lastName;
    private LocalDate dateOfBirth;
    @Column(unique = true)
    private String email;
    @NotEmpty
    @Size(min = 4)
    private String password;
    @NotEmpty
    @Size(min = 13, max = 13)
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private Role role;
    @NotNull
    private int experience;
    private boolean accepted;
    @OneToMany(mappedBy = "employees", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private List<Cheque> cheque = new ArrayList<>();

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public String getPassword() {
        return this.password;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
