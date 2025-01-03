package example.pokemon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class indicates the attack skill of given pokemon.
 * Classwork 3 (UML diagrams);
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttackSkill {
    private String name;
    private String description;
    private String cost;
    private int damage;
}