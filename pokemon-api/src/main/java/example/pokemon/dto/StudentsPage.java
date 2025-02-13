package example.pokemon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentsPage extends RepresentationModel<StudentsPage> {
    List<StudentDto> students;
    int page;
    int pages;
    long total;
}
