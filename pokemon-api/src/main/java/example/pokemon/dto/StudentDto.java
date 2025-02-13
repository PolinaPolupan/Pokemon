package example.pokemon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;

/**
 * Stores the student's data.
 * Classwork 3 (UML diagrams).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto extends RepresentationModel<StudentDto> {
    private UUID id;
    private String firstName;
    private String lastName;
    private String studentGroup;
}