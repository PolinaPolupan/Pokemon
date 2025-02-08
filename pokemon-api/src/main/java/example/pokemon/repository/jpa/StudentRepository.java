package example.pokemon.repository.jpa;

import example.pokemon.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;


public interface StudentRepository extends JpaRepository<Student, UUID> {

    Optional<Student> findByFirstNameAndLastName(String firstName, String lastName);
    Optional<Student> findByFirstNameAndLastNameAndStudentGroup(String firstName, String lastName, String studentGroup);
}
