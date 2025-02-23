package example.pokemon.service;

import example.pokemon.dto.CardInput;
import example.pokemon.dto.CardsPage;
import example.pokemon.exception.CardNotFoundException;
import example.pokemon.exception.StudentNotFoundException;
import example.pokemon.dto.CardDto;
import example.pokemon.exception.DuplicateCardException;
import example.pokemon.mapper.CardMapper;
import example.pokemon.model.Card;
import example.pokemon.model.CardDocument;
import example.pokemon.model.Student;
import example.pokemon.repository.elastic.CardElasticRepository;
import example.pokemon.repository.jpa.CardRepository;
import example.pokemon.repository.jpa.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final CardElasticRepository cardElasticRepository;
    private final StudentRepository studentRepository;
    private final CardMapper cardMapper;

    public void save(CardInput card) {
        Optional<Card> existingCard = cardRepository.findByName(card.getName());
        existingCard.ifPresent(c ->
            { throw new DuplicateCardException("A card with the same name already exists."); }
        );

        if (card.getPokemonOwnerId() == null) {
            throw new StudentNotFoundException("Invalid null student");
        }

        Student student = studentRepository.findById(card.getPokemonOwnerId()).orElseThrow(
                () -> { throw new StudentNotFoundException("Student not found"); }
        );

        studentRepository.findByFirstNameAndLastNameAndStudentGroup(
                student.getFirstName(),
                student.getLastName(),
                student.getStudentGroup()).ifPresent(c ->
                { throw new DuplicateCardException("A card with the same owner already exists."); }
        );

        cardRepository.save(cardMapper.mapInputToCard(card));
    }

    public CardsPage getAll(Pageable page) {
        Page<Card> pagedResult = cardRepository.findAll(page);
        List<CardDto> cards = pagedResult
                .getContent()
                .stream()
                .map(cardMapper::mapToDto)
                .toList();

        CardsPage response = new CardsPage();
        response.setPage(pagedResult.getNumber());
        response.setTotal(pagedResult.getTotalElements());
        response.setPages(pagedResult.getTotalPages());
        response.setCards(cards);

        return response;
    }

    public CardDto getByName(String name) {
        CardDocument card = cardElasticRepository.findByName(name).orElseThrow(
            () -> { throw new CardNotFoundException("Card not found"); }
        );
        return cardMapper.mapToDto(card);
    }

    public CardDto getByOwner(UUID ownerId) {
        Student student = studentRepository.findById(ownerId).orElseThrow(
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
