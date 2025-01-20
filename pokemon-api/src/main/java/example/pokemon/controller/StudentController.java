package example.pokemon.controller;

import example.pokemon.dto.GetStudentRequest;
import example.pokemon.dto.StudentDto;
import example.pokemon.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/students")
@AllArgsConstructor
public class StudentController {

    private final StudentService service;

    @GetMapping("/all")
    public ResponseEntity<List<StudentDto>> getAllStudents() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.getAll());
    }

    @GetMapping("/{group}")
    public ResponseEntity<List<StudentDto>> getByStudentGroup(@PathVariable String group) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.getByStudentGroup(group));
    }

    @GetMapping
    public ResponseEntity<StudentDto> getByFirstNameLastName(@RequestBody GetStudentRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.getByFirstNameLastName(request));
    }

    @PostMapping
    public ResponseEntity<Void> createStudent(@RequestBody StudentDto student) {
        service.save(student);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
