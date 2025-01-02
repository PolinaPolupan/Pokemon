package example.pokemon.service;

import example.pokemon.dto.CardDto;
import example.pokemon.dto.StudentDto;
import example.pokemon.exception.CardNotFoundException;
import example.pokemon.exception.DuplicateCardException;
import example.pokemon.exception.StudentNotFoundException;
import example.pokemon.mapper.CardMapper;
import example.pokemon.model.Card;
import example.pokemon.model.Student;
import example.pokemon.repository.CardRepository;
import example.pokemon.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final StudentRepository studentRepository;
    private final CardMapper cardMapper;

    public void save(CardDto card) {
        Optional<Card> existingCard = cardRepository.findByName(card.getName());
        existingCard.ifPresent(c ->
            { throw new DuplicateCardException("A card with the same name already exists."); }
        );

        cardRepository.save(cardMapper.mapDtoToCard(card));
    }

    public List<CardDto> getAll() {
        return cardRepository.findAll()
                .stream()
                .map(cardMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public CardDto getByName(String name) {
        Card card = cardRepository.findByName(name).orElseThrow(
            () -> { throw new CardNotFoundException("Card not found"); }
        );
        return cardMapper.mapToDto(card);
    }

    public CardDto getByOwner(StudentDto owner) {
        Student student = studentRepository.findByFirstNameAndLastNameAndSurNameAndGroup(
            owner.getFirstName(),
            owner.getLastName(),
            owner.getSurName(),
            owner.getGroup()).orElseThrow(
            () -> { throw new StudentNotFoundException("Student not found"); }
        );

        Card card = cardRepository.findByPokemonOwner(student).orElseThrow(
            () -> { throw new CardNotFoundException("Card not found"); }
        );

        return cardMapper.mapToDto(card);
    }

    public CardDto getById(UUID id) {
        Card card = cardRepository.findById(id).orElseThrow(
                () -> { throw new CardNotFoundException("Card not found"); }
        );
        return cardMapper.mapToDto(card);
    }
}
