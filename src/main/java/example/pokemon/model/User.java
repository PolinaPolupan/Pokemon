package example.pokemon.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@Entity
public class User {
    @Id
    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @Column(name = "password", nullable = false, length = 500)
    private String password;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled = true;

    @OneToMany(mappedBy = "username", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude  // Add this to prevent circular reference in toString()
    @EqualsAndHashCode.Exclude  // Add this to prevent circular reference in equals/hashCode
    private Set<Authority> authorities = new LinkedHashSet<>();

    // Helper method to manage the bidirectional relationship
    public void addAuthority(Authority authority) {
        authorities.add(authority);
        authority.setUsername(this);
    }
}