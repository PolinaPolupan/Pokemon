package example.pokemon.controller;

import example.pokemon.dto.StudentsPage;
import example.pokemon.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;


@Controller
@AllArgsConstructor
public class StudentController {

    private final StudentService service;

    @QueryMapping
    public StudentsPage getAllStudents(
            @Argument int page,
            @Argument int size
    ) {
        Pageable paging = PageRequest.of(page, size);
        return service.getAll(paging);
    }
}
