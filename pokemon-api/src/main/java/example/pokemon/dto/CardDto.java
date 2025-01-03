package example.pokemon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Class indicates the given pokemon card.
 * Classwork 3 (UML diagrams).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardDto {
    private PokemonStage pokemonStage;
    private String cardNumber;
    private String name;
    private int hp;
    private EnergyType pokemonType;
    private CardDto evolvesFrom;
    private List<AttackSkill> skills;
    private EnergyType weaknessType;
    private EnergyType resistanceType;
    private String retreatCost;
    private String gameSet;
    private char regulationMark;
    private StudentDto pokemonOwner;
}
