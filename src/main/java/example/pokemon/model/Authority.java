package example.pokemon.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;

@Entity
@Table(name = "authorities")
@Data
@NoArgsConstructor
@ToString(exclude = "username")
@EqualsAndHashCode(exclude = "username")
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "authority", nullable = false, length = 50)
    private String authority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username", nullable = false)
    private User username;

    @Column(name = "created_date", nullable = false)
    private Instant createdDate;

    public Authority(String authority) {
        this.authority = authority;
        this.createdDate = Instant.now();
    }
}