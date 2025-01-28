package example.pokemon.repository;

import example.pokemon.model.Card;
import example.pokemon.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;
import java.util.UUID;

public interface CardRepository extends PagingAndSortingRepository<Card, UUID>, JpaRepository<Card, UUID> {

    Optional<Card> findByName(String name);
    Optional<Card> findByPokemonOwner(Student owner);
}
