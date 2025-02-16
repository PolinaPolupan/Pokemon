package example.pokemon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardInput {
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
    private UUID pokemonOwnerId;
}
