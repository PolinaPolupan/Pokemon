package example.pokemon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;
import java.util.UUID;

/**
 * Class indicates the given pokemon card.
 * Classwork 3 (UML diagrams).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SchemaMapping(typeName = "Card")
public class CardDto {
    private UUID id;
    private PokemonStage stage;
    private String cardNumber;
    private String name;
    private int hp;
    private EnergyType pokemonType;
    private UUID evolvesFromId;
    private List<AttackSkill> attackSkills;
    private EnergyType weaknessType;
    private EnergyType resistanceType;
    private String retreatCost;
    private String gameSet;
    private char regulationMark;
    private StudentDto pokemonOwner;
}
