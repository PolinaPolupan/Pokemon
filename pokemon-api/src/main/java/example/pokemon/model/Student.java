package example.pokemon.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "students")
@Entity
public class Student {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    private String firstName;
    private String lastName;
    private String studentGroup;
}