package example.pokemon.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.UUID;


@Document(indexName ="students")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "students")
@Entity
public class Student {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Field(type = FieldType.Text)
    @NotBlank(message = "Name is mandatory")
    private String firstName;

    @Field(type = FieldType.Text)
    @NotBlank(message = "Last name is mandatory")
    private String lastName;

    @Field(type = FieldType.Text)
    @NotBlank(message = "Group is mandatory")
    private String studentGroup;
}