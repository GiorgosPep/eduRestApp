package gr.aueb.cf.eduapp.model;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Changelog;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "capabilities")
public class Capability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    @ManyToMany(mappedBy = "capabilities", fetch = FetchType.LAZY)
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.PACKAGE)
    private Set<Role> roles = new HashSet<>();

    public Set<Role> getAllRoles() {
        return Set.copyOf(roles);
    }

}
