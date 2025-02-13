package example.pokemon.controller;

import example.pokemon.dto.CardImageResponse;
import example.pokemon.dto.CardsPage;
import example.pokemon.service.CardService;
import example.pokemon.dto.CardDto;
import example.pokemon.service.PokemonTcgService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequestMapping("api/v1/cards")
@AllArgsConstructor
public class CardController {

    private final CardService service;
    private final PokemonTcgService pokemonTcgService;

    @GetMapping
    public ResponseEntity<CardsPage> getAllCards(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        Pageable paging = PageRequest.of(page, size);
        CardsPage cards = service.getAll(paging);

        for (CardDto card: cards.getCards()) {
            addLinksToCard(card);
        }

        Link next = linkTo(methodOn(CardController.class).getAllCards(page + 1, size)).withRel("next");
        cards.add(next);
        Link previous = linkTo(methodOn(CardController.class).getAllCards(page - 1, size)).withRel("previous");
        cards.add(previous);

        return ResponseEntity.ok(cards);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardDto> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(addLinksToCard(service.getById(id)));
    }

    @GetMapping("/search")
    public ResponseEntity<CardDto> getByName(@RequestParam String name) {
        return ResponseEntity.ok(addLinksToCard(service.getByName(name)));
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<CardDto> getByOwner(@PathVariable UUID ownerId) {
        return ResponseEntity.ok(addLinksToCard(service.getByOwner(ownerId)));
    }

    @PostMapping
    public ResponseEntity<Void> createCard(@RequestBody CardDto card) {
        service.save(card);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/image")
    public ResponseEntity<CardImageResponse> getCardImage(@RequestParam String name) {
        return ResponseEntity.ok(pokemonTcgService.fetchCardImageByName(name));
    }

    private CardDto addLinksToCard(CardDto card) {
        Link selfLink = linkTo(methodOn(CardController.class).getById(card.getId())).withSelfRel();
        card.add(selfLink);

        if (card.getPokemonOwner() != null) {
            Link ownerLink = linkTo(methodOn(StudentController.class).getById(card.getPokemonOwner().getId())).withRel("owner");
            card.add(ownerLink);
        }

        if (card.getEvolvesFromId() != null) {
            Link evolvesFromLink = linkTo(methodOn(CardController.class).getById(card.getEvolvesFromId())).withRel("evolvesFrom");
            card.add(evolvesFromLink);
        }

        return card;
    }
}
