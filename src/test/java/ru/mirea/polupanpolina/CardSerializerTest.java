package ru.mirea.polupanpolina;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.mirea.polupanpolina.pkmn.Card;
import ru.mirea.polupanpolina.pkmn.EnergyType;
import ru.mirea.polupanpolina.pkmn.PokemonStage;
import ru.mirea.polupanpolina.pkmn.Student;

import java.io.File;
import java.util.ArrayList;

import static org.junit.Assert.assertTrue;
import static ru.mirea.polupanpolina.CardExport.serializeCard;

public class CardSerializerTest {

    private final String outputDirectory = "output/";
    private final String testFileName = "test_card.crd";

    @BeforeEach
    public void setUp() {
        // Create the output directory if it doesn't exist
        File directory = new File(outputDirectory);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    @Test
    public void testSerializeCardSuccess() {
        // Create a sample card to serialize
        Card testCard = new Card(
                PokemonStage.BASIC,
                "Pikachu",
                60,
                EnergyType.LIGHTNING,
                null,
                new ArrayList<>(),
                EnergyType.FIGHTING,
                EnergyType.METAL,
                "1",
                "Base Set",
                'A',
                new Student("Ash", "Ketchum", "Trainer", "Pallet Town Group")
        );

        // Call the method to serialize the card
        serializeCard(testCard, testFileName);

        // Verify the file is created
        File outputFile = new File(outputDirectory + testFileName);
        assertTrue(outputFile.exists());
    }

    @Test
    public void testSerializeCardFailure() {

        // Create a sample card to serialize
        Card testCard = new Card(
                PokemonStage.BASIC,
                "Pikachu",
                60,
                EnergyType.LIGHTNING,
                null,
                new ArrayList<>(),
                EnergyType.FIGHTING,
                EnergyType.METAL,
                "1",
                "Base Set",
                'A',
                new Student("Ash", "Ketchum", "Trainer", "Pallet Town Group")
        );

        // Call the method to serialize the card
        serializeCard(testCard, testFileName);
    }
}
