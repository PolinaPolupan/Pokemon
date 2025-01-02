package example.pokemon.repository;

import example.pokemon.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<Student, UUID> {

    Optional<Student> findByFirstNameAndLastName(String firstName, String lastName);
    List<Student> findByFirstNameAndLastNameAndSurName(String firstName, String lastName, String surName);
    Optional<Student> findByFirstNameAndLastNameAndSurNameAndGroup(String firstName, String lastName, String surName, String group);
    List<Student> findByGroup(String group);
}
