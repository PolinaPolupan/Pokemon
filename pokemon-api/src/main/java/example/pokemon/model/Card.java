package example.pokemon.model;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import example.pokemon.dto.AttackSkill;
import example.pokemon.serialize.SkillDeserializer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cards")
public class Card {
    @Id
    @GeneratedValue(generator = "UUID")
    public UUID id;

    public String stage;

    @NotBlank(message = "Name is mandatory")
    public String name;

    @Column(columnDefinition = "smallint")
    public short hp;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "evolves_from_id")
    public Card evolvesFrom;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "JSON")
    @JsonDeserialize(using = SkillDeserializer.class)
    public List<AttackSkill> attackSkills;

    public String weaknessType;
    public String resistanceType;
    public String retreatCost;
    public String gameSet;
    public String pokemonType;
    public char regulationMark;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pokemon_owner_id")
    public Student pokemonOwner;

    public String cardNumber;
}