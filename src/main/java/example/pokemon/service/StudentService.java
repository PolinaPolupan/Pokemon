package example.pokemon.service;

import example.pokemon.dto.GetStudentRequest;
import example.pokemon.dto.StudentDto;
import example.pokemon.exception.DuplicateStudentException;
import example.pokemon.exception.StudentNotFoundException;
import example.pokemon.mapper.StudentMapper;
import example.pokemon.model.Student;
import example.pokemon.repository.StudentRepository;
import lombok.AllArgsConstructor;
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

    public List<StudentDto> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
    }

    public List<StudentDto> getByStudentGroup(String group) {
        return repository.findByGroup(group)
                .stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
    }

    public StudentDto getByFirstNameLastNameAndSurName(GetStudentRequest request) {

        List<Student> students = repository.findByFirstNameAndLastNameAndSurName(
                request.getFirstName(),
                request.getLastName(),
                request.getSurName()
        );

        if (students.isEmpty()) {
            throw new StudentNotFoundException("Student not found");
        } else if (students.size() > 1) {
            throw new DuplicateStudentException("Multiple students found with the same name");
        }

        return mapper.mapToDto(students.get(0));
    }
}
