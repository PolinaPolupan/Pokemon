package example.pokemon.controller;

import example.pokemon.dto.CardImageResponse;
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

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("api/v1/cards")
@AllArgsConstructor
public class CardController {

    private final CardService service;
    private final PokemonTcgService pokemonTcgService;

    @GetMapping
    public ResponseEntity<List<CardDto>> getAllCards(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        Pageable paging = PageRequest.of(page, size);
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.getAll(paging));
    }

    @GetMapping("/{name}")
    public ResponseEntity<CardDto> getByName(@PathVariable String name) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.getByName(name));
    }

    @GetMapping("/owner")
    public ResponseEntity<CardDto> getByOwner(@RequestBody StudentDto owner) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.getByOwner(owner));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<CardDto> getById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<Void> createCard(@RequestBody CardDto card) {
        service.save(card);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/card-image")
    public ResponseEntity<CardImageResponse> getCardImage(@RequestParam String name) {
        CardImageResponse response = pokemonTcgService.fetchCardImageByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
