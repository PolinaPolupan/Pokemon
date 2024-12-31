package example.pokemon.controller;

import example.pokemon.model.Student;
import example.pokemon.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/students")
@AllArgsConstructor
public class StudentController {

    private final StudentRepository repository;

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.status(OK)
                .body(repository.findAll());
    }

    @PostMapping
    public ResponseEntity<Void> createStudent(@RequestBody Student student) {
        repository.save(student);
        return new ResponseEntity<>(CREATED);
    }
}
