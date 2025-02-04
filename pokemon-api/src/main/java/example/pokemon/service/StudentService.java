package example.pokemon.service;

import example.pokemon.dto.*;
import example.pokemon.exception.DuplicateStudentException;
import example.pokemon.exception.StudentNotFoundException;
import example.pokemon.mapper.StudentMapper;
import example.pokemon.model.Student;
import example.pokemon.repository.jpa.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StudentService {

    private final StudentRepository repository;
    private final StudentMapper mapper;

    public void save(StudentDto student) {
        // Check if a student already exists with the same firstName and lastName
        Optional<Student> existingStudent = repository.findByFirstNameAndLastName(
                student.getFirstName(),
                student.getLastName()
        );

        existingStudent.ifPresent(s -> {
            throw new DuplicateStudentException("A student with the same name already exists.");
        });

        repository.save(mapper.mapDtoToStudent(student));
    }

    public StudentsPage getAll(Pageable page) {
        Page<Student> pagedResult = repository.findAll(page);
        List<StudentDto> students = pagedResult
                .getContent()
                .stream()
                .map(mapper::mapToDto)
                .toList();

        StudentsPage response = new StudentsPage();
        response.setPage(pagedResult.getNumber());
        response.setTotal(pagedResult.getTotalElements());
        response.setPages(pagedResult.getTotalPages());
        response.setStudents(students);

        return response;
    }

    public List<StudentDto> getByStudentGroup(String group) {
        return repository.findByStudentGroup(group)
                .stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
    }

    public StudentDto getByFirstNameLastName(String firstName, String lastName) {

        Student student = repository.findByFirstNameAndLastName(firstName, lastName)
            .orElseThrow(() -> { throw new StudentNotFoundException("Student not found"); }
        );

        return mapper.mapToDto(student);
    }
}
