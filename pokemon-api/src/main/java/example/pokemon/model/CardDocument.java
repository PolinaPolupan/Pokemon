package example.pokemon.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName ="cards")
public class CardDocument {
    @Id
    @Field(type = FieldType.Keyword, name = "id")
    public String id;

    @Field(type = FieldType.Text, name = "stage")
    public String stage;

    @Field(type = FieldType.Text, name = "name")
    public String name;

    @Field(type = FieldType.Short, name = "hp")
    public short hp;

    @Field(type = FieldType.Object, name = "evolves_from")
    public CardDocument evolvesFrom;

    @Field(type = FieldType.Nested, name = "attack_skills")
    public List<AttackSkillDocument> attackSkills;

    @Field(type = FieldType.Text, name = "weakness_type")
    public String weaknessType;

    @Field(type = FieldType.Text, name = "resistance_type")
    public String resistanceType;

    @Field(type = FieldType.Text, name = "retreat_cost")
    public String retreatCost;

    @Field(type = FieldType.Text, name = "game_set")
    public String gameSet;

    @Field(type = FieldType.Text, name = "pokemon_type")
    public String pokemonType;

    @Field(type = FieldType.Keyword, name = "regulation_mark")
    public char regulationMark;

    @Field(type = FieldType.Object, name = "pokemon_owner")
    public StudentDocument pokemonOwner;

    @Field(type = FieldType.Text, name = "card_number")
    public String cardNumber;
}