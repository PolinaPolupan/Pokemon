package example.pokemon.model;

import example.pokemon.dto.AttackSkill;
import jakarta.persistence.*;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;


@Document(indexName ="cards")
public class CardDocument {

    @Id
    @Field(type = FieldType.Text)
    public String id;

    @Field(type = FieldType.Text)
    public String stage;

    @Field(type = FieldType.Text)
    public String name;

    @Field(type = FieldType.Short)
    public short hp;

    @Field(type = FieldType.Object)
    public Card evolvesFrom;

    @Field(type = FieldType.Nested)
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

    @Field(type = FieldType.Byte)
    public char regulationMark;

    @Field(type = FieldType.Object)
    public Student pokemonOwner;

    @Field(type = FieldType.Text)
    public String cardNumber;
}