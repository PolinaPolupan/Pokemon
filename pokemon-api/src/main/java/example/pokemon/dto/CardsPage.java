package example.pokemon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardsPage extends RepresentationModel<CardsPage> {
    List<CardDto> cards;
    int page;
    int pages;
    long total;
}
