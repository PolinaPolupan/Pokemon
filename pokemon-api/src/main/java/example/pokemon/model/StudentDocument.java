package example.pokemon.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName ="students")
public class StudentDocument {

    @Id
    @Field(type = FieldType.Text)
    private String id;

    @Field(type = FieldType.Text, name = "first_name")
    private String firstName;

    @Field(type = FieldType.Text, name = "last_name")
    private String lastName;

    @Field(type = FieldType.Text, name = "student_group")
    private String studentGroup;
}
