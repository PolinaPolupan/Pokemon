package example.pokemon.controller;

import example.pokemon.dto.StudentDto;
import example.pokemon.dto.StudentsPage;
import example.pokemon.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
        StudentsPage students = service.getAll(paging);

        for (StudentDto studentDto: students.getStudents()) {
            addLinksToStudent(studentDto);
        }

        Link next = linkTo(methodOn(StudentController.class).getAllStudents(page + 1, size)).withRel("next");
        students.add(next);
        Link previous = linkTo(methodOn(StudentController.class).getAllStudents(page - 1, size)).withRel("previous");
        students.add(previous);

        return ResponseEntity.ok(students);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(addLinksToStudent(service.getById(id)));
    }

    @GetMapping("/group/{group}")
    public ResponseEntity<List<StudentDto>> getByStudentGroup(@PathVariable String group) {
        List<StudentDto> students = service.getByStudentGroup(group);

        for (StudentDto studentDto: students) {
            addLinksToStudent(studentDto);
        }

        return ResponseEntity.ok(students);
    }

    @GetMapping("/search")
    public ResponseEntity<StudentDto> getByFirstNameLastName(
            @RequestParam String firstName,
            @RequestParam String lastName
    ) {
        return ResponseEntity.ok(addLinksToStudent(service.getByFirstNameLastName(firstName, lastName)));
    }

    @PostMapping
    public ResponseEntity<Void> createStudent(@RequestBody StudentDto student) {
        service.save(student);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    private StudentDto addLinksToStudent(StudentDto student) {
        Link selfLink = linkTo(methodOn(StudentController.class).getById(student.getId())).withSelfRel();
        student.add(selfLink);

        return student;
    }
}