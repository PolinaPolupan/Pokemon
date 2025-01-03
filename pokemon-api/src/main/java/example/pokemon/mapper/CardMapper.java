package example.pokemon.mapper;

import example.pokemon.dto.CardDto;
import example.pokemon.dto.EnergyType;
import example.pokemon.model.Card;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CardMapper {

    @Mapping(source = "stage", target = "pokemonStage")
    @Mapping(source = "attackSkills", target = "skills")
    @Mapping(source = "evolvesFrom", target = "evolvesFrom")
    @Mapping(source = "weaknessType", target = "weaknessType", qualifiedByName = "stringToEnergyType")
    @Mapping(source = "resistanceType", target = "resistanceType", qualifiedByName = "stringToEnergyType")
    CardDto mapToDto(Card card);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "pokemonStage", target = "stage")
    @Mapping(source = "skills", target = "attackSkills")
    @Mapping(source = "evolvesFrom", target = "evolvesFrom")
    @Mapping(source = "weaknessType", target = "weaknessType", qualifiedByName = "energyTypeToString")
    @Mapping(source = "resistanceType", target = "resistanceType", qualifiedByName = "energyTypeToString")
    Card mapDtoToCard(CardDto dto);

    @Named("stringToEnergyType")
    default EnergyType stringToEnergyType(String type) {
        return type == null ? null : EnergyType.valueOf(type.toUpperCase());
    }

    @Named("energyTypeToString")
    default String energyTypeToString(EnergyType type) {
        return type == null ? null : type.name().toLowerCase();
    }
}

