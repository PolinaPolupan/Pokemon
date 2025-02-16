package example.pokemon.controller;

import example.pokemon.dto.CardDto;
import example.pokemon.dto.CardInput;
import example.pokemon.dto.CardsPage;
import example.pokemon.service.CardService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.UUID;


@Controller
@AllArgsConstructor
public class CardController {

    private final CardService service;

    @QueryMapping
    public CardsPage getAllCards(
            @Argument int page,
            @Argument int size
    ) {
        Pageable paging = PageRequest.of(page, size);
        return service.getAll(paging);
    }

    @SchemaMapping(typeName = "Card")
    public CardDto evolvesFrom(CardDto card) {
        return card.getEvolvesFromId() != null ? service.getById(card.getEvolvesFromId()) : null;
    }

    @QueryMapping
    public CardDto getCardById(@Argument UUID id) {
        return service.getById(id);
    }

    @QueryMapping
    public CardDto getCardByName(@Argument String name) {
        return service.getByName(name);
    }

    @QueryMapping
    public CardDto getCardByOwner(@Argument UUID ownerId) {
        return service.getByOwner(ownerId);
    }


    public void createCard(@Argument CardInput card) {
        service.save(card);
    }

//    @GetMapping
//    public ResponseEntity<CardImageResponse> getCardImage(@RequestParam String name) {
//        return ResponseEntity.ok(pokemonTcgService.fetchCardImageByName(name));
//    }
}