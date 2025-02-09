package example.pokemon.repository.elastic;

import example.pokemon.model.CardDocument;
import example.pokemon.model.Student;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CardElasticRepository extends ElasticsearchRepository<CardDocument, String> {

    Optional<CardDocument> findByName(String name);
    Optional<CardDocument> findByPokemonOwner(Student owner);
}