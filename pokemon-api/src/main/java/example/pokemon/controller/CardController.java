package example.pokemon.controller;

import example.pokemon.dto.CardImageResponse;
import example.pokemon.dto.CardsPage;
import example.pokemon.model.CardDocument;
import example.pokemon.service.CardService;
import example.pokemon.dto.CardDto;
import example.pokemon.dto.StudentDto;
import example.pokemon.service.PokemonTcgService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


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
        return ResponseEntity.ok(service.getAll(paging));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardDto> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<CardDto> getByName(@RequestParam String name) {
        return ResponseEntity.ok(service.getByName(name));
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<CardDto> getByOwner(@PathVariable UUID ownerId) {
        return ResponseEntity.ok(service.getByOwner(ownerId));
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
}
