package example.pokemon.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Card {
    private Long id;
    private String name;
    private short hp;
    private String gameSet;
    private Student pokemonOwner;
    private String stage;
    private String retreatCost;
    private String weaknessType;
    private String resistanceType;
    private String pokemonType;
    private char regulationMark;
    private String cardNumber;
}
