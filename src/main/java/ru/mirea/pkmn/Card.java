package ru.mirea.pkmn;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * Class indicates the given pokemon card.
 * Classwork 3 (UML diagrams).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Card implements Serializable {

    private PokemonStage pokemonStage;

    private String number;

    private String name;

    private int hp;

    private EnergyType pokemonType;

    private Card evolvesFrom;

    private List<AttackSkill> skills;

    private EnergyType weaknessType;

    private EnergyType resistanceType;

    private String retreatCost;

    private String gameSet;

    private char regulationMark;

    private Student pokemonOwner;


    public ru.mirea.pkmn.polupanpolina.entity.Card toEntity() {
        // Generate a UUID based on some unique field combination
        UUID cardUuid = UUID.nameUUIDFromBytes((name + number).getBytes());

        // Convert the 'evolvesFrom' field if it's not null
        ru.mirea.pkmn.polupanpolina.entity.Card evolvesFromEntity = evolvesFrom != null ? evolvesFrom.toEntity() : null;

        // Create and return the entity object
        return new ru.mirea.pkmn.polupanpolina.entity.Card(
                cardUuid,                     // id
                this.name,                    // name
                (short) this.hp,              // hp (cast int to short)
                this.gameSet,                 // gameSet
                this.retreatCost,             // retreatCost
                this.regulationMark,          // regulationMark
                this.number,                  // cardNumber
                this.pokemonOwner.toEntity(),            // pokemonOwner
                evolvesFromEntity,            // evolvesFrom
                this.skills                   // attackSkills
        );
    }
}
