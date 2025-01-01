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
    Optional<Student> findByFirstNameAndLastNameAndSurName(String firstName, String lastName, String surName);
    List<Student> findByGroup(String group);
}
