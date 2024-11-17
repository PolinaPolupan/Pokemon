package ru.mirea.pkmn.polupanpolina.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mirea.pkmn.AttackSkill;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "card")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Card {
    @Id
    private UUID id;

    private String name;
    private int hp;
    private String gameSet;
    private String retreatCost;
    private String regulationMark;
    private String number;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pokemon_owner")
    private Student pokemonOwner;

    @OneToOne
    @JoinColumn(name = "evolves_from")
    private Card evolvesFrom;

    @ElementCollection
    @Column(name = "attack_skills", columnDefinition = "json")
    private List<AttackSkill> skills;
}
