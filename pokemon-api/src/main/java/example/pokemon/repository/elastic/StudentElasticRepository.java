package example.pokemon.repository.elastic;


import example.pokemon.model.StudentDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentElasticRepository extends ElasticsearchRepository<StudentDocument, String> {

    Optional<StudentDocument> findByFirstNameAndLastName(String firstName, String lastName);
    List<StudentDocument> findByStudentGroup(String group);
}