package example.pokemon.controller;

import example.pokemon.service.CardService;
import example.pokemon.dto.CardDto;
import example.pokemon.dto.StudentDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("api/v1/cards")
@AllArgsConstructor
public class CardController {

    private final CardService service;

    @GetMapping
    public ResponseEntity<List<CardDto>> getAllCards() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.getAll());
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
}
