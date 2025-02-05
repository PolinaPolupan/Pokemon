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
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;
import java.util.UUID;


@Document(indexName = "cards")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cards")
public class Card {
    @Id
    @GeneratedValue(generator = "UUID")
    public UUID id;

    @Field(type = FieldType.Text)
    public String stage;

    @Field(type = FieldType.Text)
    @NotBlank(message = "Name is mandatory")
    public String name;

    @Field(type = FieldType.Short)
    @Column(columnDefinition = "smallint")
    public short hp;

    @Field(type = FieldType.Object)
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "evolves_from_id")
    public Card evolvesFrom;

    @Field(type = FieldType.Nested)
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "JSON")
    @JsonDeserialize(using = SkillDeserializer.class)
    public List<AttackSkill> attackSkills;

    @Field(type = FieldType.Text)
    public String weaknessType;

    @Field(type = FieldType.Text)
    public String resistanceType;

    @Field(type = FieldType.Text)
    public String retreatCost;

    @Field(type = FieldType.Text)
    public String gameSet;

    @Field(type = FieldType.Text)
    public String pokemonType;

    @Field(type = FieldType.Keyword)
    public char regulationMark;

    @Field(type = FieldType.Object)
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pokemon_owner_id")
    public Student pokemonOwner;

    @Field(type = FieldType.Text)
    public String cardNumber;
}