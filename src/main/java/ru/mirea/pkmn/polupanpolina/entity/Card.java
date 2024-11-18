package ru.mirea.pkmn.polupanpolina.entity;

import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
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

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "smallint", nullable = false)
    private short hp;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "evolves_from")
    private Card evolvesFrom;

    @Column(name = "game_set", nullable = false)
    private String gameSet;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "pokemon_owner")
    private Student pokemonOwner;

    @Column(nullable = false)
    private String stage;

    @Column(name = "retreat_cost", nullable = false)
    private String retreatCost;

    @Column(name = "weakness_type", nullable = false)
    private String weaknessType;

    @Column(name = "resistance_type", nullable = false)
    private String resistanceType;

    @Type(JsonType.class)
    @Column(name = "attack_skills", columnDefinition = "json")
    private List<AttackSkill> attackSkills;

    @Column(name = "pokemon_type", nullable = false)
    private String pokemonType;

    @Column(name = "regulation_mark")
    private char regulationMark;

    @Column(name = "card_number")
    private String cardNumber;
}
