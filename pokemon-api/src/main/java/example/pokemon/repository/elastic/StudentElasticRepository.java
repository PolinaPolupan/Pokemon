package example.pokemon.repository.elastic;

import example.pokemon.model.Student;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StudentElasticRepository extends ElasticsearchRepository<Student, UUID> {

    Optional<Student> findByFirstNameAndLastName(String firstName, String lastName);
    Optional<Student> findByFirstNameAndLastNameAndStudentGroup(String firstName, String lastName, String studentGroup);
    List<Student> findByStudentGroup(String group);
}