package example.pokemon.controller;

import example.pokemon.dto.StudentDto;
import example.pokemon.dto.StudentsPage;
import example.pokemon.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/students")
@AllArgsConstructor
public class StudentController {

    private final StudentService service;

    @GetMapping
    public ResponseEntity<StudentsPage> getAllStudents(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        Pageable paging = PageRequest.of(page, size);
        return ResponseEntity.ok(service.getAll(paging));
    }

    @GetMapping("/group/{group}")
    public ResponseEntity<List<StudentDto>> getByStudentGroup(@PathVariable String group) {
        return ResponseEntity.ok(service.getByStudentGroup(group));
    }

    @GetMapping("/search")
    public ResponseEntity<StudentDto> getByFirstNameLastName(
            @RequestParam String firstName,
            @RequestParam String lastName
    ) {
        return ResponseEntity.ok(service.getByFirstNameLastName(firstName, lastName));
    }

    @PostMapping
    public ResponseEntity<Void> createStudent(@RequestBody StudentDto student) {
        service.save(student);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}