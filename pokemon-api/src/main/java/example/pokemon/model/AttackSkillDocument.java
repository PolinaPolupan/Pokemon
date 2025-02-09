package example.pokemon.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttackSkillDocument {
    @Field(type = FieldType.Text)
    public String name;

    @Field(type = FieldType.Text)
    public String description;

    @Field(type = FieldType.Integer)
    public int damage;

    @Field(type = FieldType.Nested)
    public List<String> energyTypes;
}
