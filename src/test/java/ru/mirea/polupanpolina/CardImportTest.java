package ru.mirea.polupanpolina;

import org.junit.Before;
import org.junit.Test;
import ru.mirea.polupanpolina.pkmn.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import com.google.common.io.Resources;

import static org.junit.Assert.assertEquals;

public class CardImportTest {

    private static final String TEST_FILE_PATH = "test_card.txt";

    private static Card card = null;

    @Before
    public void setup() throws URISyntaxException {

        URL resource =  Resources.getResource(TEST_FILE_PATH);
        Path path = Paths.get(resource.toURI());

        card = CardImport.parseCard(path.toString());

        assert card != null;
    }

    @Test
    public void testReadCardFromFile_validData() {

        // Validate the parsed data
        assertEquals(PokemonStage.STAGE1, card.getPokemonStage());
        assertEquals("Azumarill", card.getName());
        assertEquals(120, card.getHp());
        assertEquals(EnergyType.FIRE, card.getPokemonType());

        // Validate the AttackSkill
        List<AttackSkill> skills = card.getSkills();
        assertEquals(1, skills.size());
        AttackSkill skill = skills.get(0);
        assertEquals("Surf", skill.getName());
        assertEquals(90, skill.getDamage());
        assertEquals("W", skill.getCost());

        assertEquals(EnergyType.LIGHTNING, card.getWeaknessType());
        assertEquals(EnergyType.FIRE, card.getResistanceType());
        assertEquals("2", card.getRetreatCost());
        assertEquals("Sword & Shield—Fusion Strike", card.getGameSet());
        assertEquals('E', card.getRegulationMark());

        // Validate the student
        Student student = card.getPokemonOwner();
        assertEquals("Polina", student.getFirstName());
        assertEquals("Polupan", student.getSurName());
        assertEquals("Mikhailovna", student.getFamilyName());
        assertEquals("BSBO-05-23", student.getGroup());
    }

    @Test
    public void testReadCardFromFile_missingData() throws URISyntaxException {

        URL resource =  Resources.getResource("test_card_missing.txt");
        Path path = Paths.get(resource.toURI());

        card = CardImport.parseCard(path.toString());

        assert card != null;

        // Verify default values
        assertEquals(PokemonStage.BASIC, card.getPokemonStage());
        assertEquals("defaultName", card.getName());
        assertEquals(0, card.getHp());
        assertEquals(EnergyType.COLORLESS, card.getPokemonType());

        // Default skills
        List<AttackSkill> skills = card.getSkills();
        assertEquals(1, skills.size());
        AttackSkill skill = skills.get(0);
        assertEquals("defaultAttack", skill.getName());
        assertEquals(0, skill.getDamage());
        assertEquals("0", skill.getCost());

        assertEquals(EnergyType.COLORLESS, card.getWeaknessType());
        assertEquals(EnergyType.COLORLESS, card.getResistanceType());
        assertEquals("0", card.getRetreatCost());
        assertEquals("0", card.getGameSet());
        assertEquals('0', card.getRegulationMark());

        // Default student
        Student student = card.getPokemonOwner();
        assertEquals("defaultFirstName", student.getFirstName());
        assertEquals("defaultSurName", student.getSurName());
        assertEquals("defaultFamilyName", student.getFamilyName());
        assertEquals("0", student.getGroup());
    }

    @Test
    public void testParsePokemonStage() {
        assertEquals(PokemonStage.STAGE1, CardImport.parsePokemonStage("STAGE1", PokemonStage.BASIC));
        assertEquals(PokemonStage.BASIC, CardImport.parsePokemonStage("INVALID_STAGE", PokemonStage.BASIC)); // Should default to BASIC
    }

    @Test
    public void testParseInt() {
        assertEquals(100, CardImport.parseInt("100", 50));
        assertEquals(50, CardImport.parseInt("invalid", 50)); // Should default to 50
    }

    @Test
    public void testParseEnergyType() {
        assertEquals(EnergyType.FIRE, CardImport.parseEnergyType("FIRE", EnergyType.COLORLESS));
        assertEquals(EnergyType.COLORLESS, CardImport.parseEnergyType("INVALID_TYPE", EnergyType.COLORLESS)); // Should default to COLORLESS
    }

    @Test
    public void testParseChar() {
        assertEquals('A', CardImport.parseChar("A", 'Z'));
        assertEquals('Z', CardImport.parseChar("", 'Z')); // Should default to 'Z'
    }

    @Test
    public void testParseStudent() {
        // Valid input
        Student student = CardImport.parseStudent("Polupan/Polina/Mikhailovna/BSBO-05-23");

        assertEquals("Polina", student.getFirstName());
        assertEquals("Polupan", student.getSurName());
        assertEquals("Mikhailovna", student.getFamilyName());
        assertEquals("BSBO-05-23", student.getGroup());

        // Invalid input
        Student parsedStudent = CardImport.parseStudent("invalid/data");
        assertEquals("data", parsedStudent.getFirstName());
        assertEquals("invalid", parsedStudent.getSurName());
    }
}
