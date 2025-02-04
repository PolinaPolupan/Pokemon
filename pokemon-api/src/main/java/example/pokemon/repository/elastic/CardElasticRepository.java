package example.pokemon.repository.elastic;

import example.pokemon.model.Card;
import example.pokemon.model.Student;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CardElasticRepository extends ElasticsearchRepository<Card, UUID> {

    Optional<Card> findByName(String name);
    Optional<Card> findByPokemonOwner(Student owner);
}