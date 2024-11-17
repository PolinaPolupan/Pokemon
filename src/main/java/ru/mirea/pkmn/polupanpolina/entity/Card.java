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
    @Column(columnDefinition = "smallint")
    private short hp;
    @Column(name = "game_set")
    private String gameSet;
    @Column(name = "retreat_cost")
    private String retreatCost;
    @Column(name = "regulation_mark")
    private char regulationMark;
    @Column(name = "card_number")
    private String cardNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pokemon_owner")
    private Student pokemonOwner;

    @OneToOne
    @JoinColumn(name = "evolves_from")
    private Card evolvesFrom;

    @Column(name = "attack_skills", columnDefinition = "json")
    private List<AttackSkill> attackSkills;
}
