package ru.mirea.polupanpolina;

import ru.mirea.polupanpolina.pkmn.*;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.google.common.io.Resources;

/**
 * Utility helper class creates Card instances from a file
 * Classwork 3 (Task 1).
 */
public final class CardImport {

    private static final Logger logger =  Logger.getLogger(PkmnApplication.class.getName());

    public static Card parseCard(String path) {

        logger.log(Level.INFO, String.format("Parsing from file path: %s", path));

        // Creating an instance of InputStream
        try (InputStream is = new FileInputStream(path)) {

            // Creating an instance of Scanner
            try (Scanner sc = new Scanner(is, StandardCharsets.UTF_8.name())) {

                //Parse pokemon stage
                PokemonStage pokemonStage = parsePokemonStage(sc.nextLine(), PokemonStage.BASIC);
                // Parse pokemon name
                String name = parseString(sc.nextLine(), "defaultName");
                //Parse hp
                int hp = parseInt(sc.nextLine(), 0);
                // Parse pokemon type
                EnergyType pokemonType = parseEnergyType(sc.nextLine(), EnergyType.COLORLESS);
                // Parse parent pokemon
                String ppath =  parseString(sc.nextLine(), " ");
                Card evolvesFrom;

                try {

                    URL resource =  Resources.getResource(ppath);
                    Path path1 = Paths.get(resource.toURI());
                    evolvesFrom = parseCard(path1.toString());

                } catch (IllegalArgumentException e) { // getResource throws IllegalArgumentException
                    evolvesFrom = null;
                }

                // Parse attack skills
                List<AttackSkill> skills = parseAttackSkills(sc.nextLine());
                // Parse weakness type
                EnergyType weaknessType = parseEnergyType(sc.nextLine(), EnergyType.COLORLESS);
                // Parse resistance type
                EnergyType resistanceType = parseEnergyType(sc.nextLine(), EnergyType.COLORLESS);
                // Parse retreat cost
                String retreatCost = parseString(sc.nextLine(), "0");
                // Parse game set
                String gameSet = parseString(sc.nextLine(), "0");
                // Parse regulation mark
                char regulationMark = parseChar(sc.nextLine(), '0');
                // Parse owner
                Student pokemonOwner = parseStudent(sc.nextLine());

                Card card =  new Card(
                        pokemonStage,
                        name,
                        hp,
                        pokemonType,
                        evolvesFrom,
                        skills,
                        weaknessType,
                        resistanceType,
                        retreatCost,
                        gameSet,
                        regulationMark,
                        pokemonOwner
                );

                logger.log(Level.INFO, String.format("Instance of Card is created from file: \n %s", card));

                return card;
            } catch (URISyntaxException e) {
                // URISyntaxException handling
                logger.log(Level.SEVERE, "URISyntaxException");
                return null;
            }

        } catch (FileNotFoundException e) {
            // File is not found exception handling
            logger.log(Level.SEVERE, String.format("File path: %s \n is not found", path));
            return null;
        } catch (SecurityException | IOException e) {
            // Security exception handling
            logger.log(Level.SEVERE, String.format("File path: %s \n security violation", path));
            return null;
        }
    }

    private static List<AttackSkill> parseAttackSkills(String string) {

        List<String> strings = List.of(string.split(","));

        List<AttackSkill> attackSkills = new java.util.ArrayList<>(List.of());

        for (String s : strings) {

            List<String> params = List.of(s.split("/"));

            String name;
            String description;
            String cost;
            int damage;

            try {
                name = parseString(params.get(1).strip(), "defaultName");
            } catch (IndexOutOfBoundsException e) {
                logger.log(Level.WARNING, "IndexOutOfBoundsException in params[1], using default attack name: defaultName");
                name = "defaultAttack";
            }
            try {
                description = parseString(params.get(1).strip(),"defaultDescription" );
            } catch (IndexOutOfBoundsException e) {
                logger.log(Level.WARNING, "IndexOutOfBoundsException in params[1], using default description: defaultDescription");
                description = "defaultDescription";
            }
            try {
                cost = parseString(params.get(0).strip(), "0");
            } catch (IndexOutOfBoundsException e) {
                logger.log(Level.WARNING, "IndexOutOfBoundsException in params[0], using default attack cost: 0");
                cost = "0";
            }
            try {
                damage = parseInt(params.get(2).strip(), 0);
            } catch (IndexOutOfBoundsException e) {
                logger.log(Level.WARNING, "IndexOutOfBoundsException in params[2], using default attack damage: 0");
                damage = 0;
            }

            AttackSkill skill = new AttackSkill(name, description, cost, damage);

            attackSkills.add(skill);
        }

        return attackSkills;
    }

    static Student parseStudent(String string) {

        List<String> params = List.of(string.split("/"));

        String firstName;
        String surName;
        String familyName;
        String group;

        try {
            firstName = parseString(params.get(1).strip(), "defaultFirstName");
        } catch (IndexOutOfBoundsException e) {
            logger.log(Level.WARNING, "IndexOutOfBoundsException in params[1], using default first name: defaultFirstName");
            firstName = "defaultFirstName";
        }
        try {
            surName = parseString(params.get(0).strip(), "defaultSurName");
        } catch (IndexOutOfBoundsException e) {
            logger.log(Level.WARNING, "IndexOutOfBoundsException in params[0], using default surName: defaultSurName");
            surName = "defaultSurName";
        }
        try {
            familyName = parseString(params.get(2).strip(), "defaultFamilyName");
        } catch (IndexOutOfBoundsException e) {
            logger.log(Level.WARNING, "IndexOutOfBoundsException in params[2], using default familyName: defaultFamilyName");
            familyName = "defaultFamilyName";
        }
        try {
            group = parseString(params.get(3).strip(), "0");
        } catch (IndexOutOfBoundsException e) {
            logger.log(Level.WARNING, "IndexOutOfBoundsException in params[3], using default group: 0");
            group = "0";
        }

        return new Student(firstName, surName, familyName, group);
    }

    // Method to safely parse PokemonStage with default value
    static PokemonStage parsePokemonStage(String line, PokemonStage defaultStage) {
        try {
            return PokemonStage.valueOf(line.strip());
        } catch (Exception e) {
            logger.log(Level.WARNING, "Failed to parse PokemonStage: " + e.getMessage() + ". Using default: " + defaultStage);
            return defaultStage;
        }
    }

    // Method to safely parse String with default value
    private static String parseString(String line, String defaultValue) {
        if (line == null || line.isEmpty()) {
            logger.log(Level.WARNING, "Empty string found, using default: " + defaultValue);
            return defaultValue;
        }
        return line.strip();
    }

    // Method to safely parse int with default value
    static int parseInt(String line, int defaultValue) {
        try {
            return Integer.parseInt(line.strip());
        } catch (NumberFormatException e) {
            logger.log(Level.WARNING,"Failed to parse int: " + e.getMessage() + ". Using default: " + defaultValue);
            return defaultValue;
        }
    }

    // Method to safely parse EnergyType with default value
    static EnergyType parseEnergyType(String line, EnergyType defaultType) {
        try {
            return EnergyType.valueOf(line.strip());
        } catch (Exception e) {
            logger.log(Level.WARNING,"Failed to parse EnergyType: " + e.getMessage() + ". Using default: " + defaultType);
            return defaultType;
        }
    }

    // Method to safely parse a single char with default value
    static char parseChar(String line, char defaultValue) {
        if (line != null && !line.isEmpty()) {
            return line.strip().charAt(0);
        }
        System.err.println("Failed to parse char, using default: " + defaultValue);
        return defaultValue;
    }
}
