package ru.mirea.polupanpolina;

import ru.mirea.polupanpolina.pkmn.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class CardImport {

    private String filePath;

    public CardImport(String filePath) {
        this.filePath = filePath;
    }

    public void serializeCard(Card card) throws IOException {

        File outputFile = new File(filePath);

        FileOutputStream fileOutputStream = new FileOutputStream(outputFile);

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

        objectOutputStream.writeObject(card);
    }

    public Card deserializeCard(String path) throws IOException, ClassNotFoundException {

        // Declaring and initializing the string with
        // custom path of a file

        // Creating an instance of Inputstream
        InputStream is = new FileInputStream(path);

        // Try block to check for exceptions
        try (Scanner sc = new Scanner(
                is, StandardCharsets.UTF_8.name())) {

            // It holds true till there is single element
            // left in the object with usage of hasNext()
            // method

            PokemonStage pokemonStage = PokemonStage.valueOf(sc.nextLine());

            String name = sc.nextLine();

            System.out.println(name);

            int hp = Integer.parseInt(sc.nextLine());

            System.out.println(hp);

            EnergyType pokemonType = EnergyType.valueOf(sc.nextLine());

            System.out.println(pokemonType);

            Card evolvesFrom = null;

            String ppath = sc.nextLine();

            if (!Objects.equals(ppath, "-")) {
                evolvesFrom = deserializeCard(ppath);
            }

            List<AttackSkill> skills = processAttackSkills(sc.nextLine());

            EnergyType weaknessType = EnergyType.valueOf(sc.nextLine());

            EnergyType resistanceType = EnergyType.valueOf(sc.nextLine());

            String retreatCost = sc.nextLine();

            String gameSet = sc.nextLine();

            char regulationMark = sc.nextLine().charAt(0);

            Student pokemonOwner = processStudent(sc.nextLine());

            Card card = new Card(
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

            return card;
        }
    }

    private List<AttackSkill> processAttackSkills(String string) {

        List<String> strings = List.of(string.split(","));

        List<AttackSkill> attackSkills = new java.util.ArrayList<>(List.of());

        for (int i = 0; i < strings.size(); i++) {

            List<String> params = List.of(strings.get(i).split("/"));

            String name = params.get(1);
            String description = params.get(1);
            String cost = params.get(0);
            int damage = Integer.parseInt(params.get(2));

            AttackSkill skill = new AttackSkill(name, description, cost, damage);

            attackSkills.add(skill);
        }

        return attackSkills;
    }

    private Student processStudent(String string) {

        List<String> params = List.of(string.split("/"));

        System.out.println(params);

        String firstName = params.get(0);
        String surName = params.get(1);
        String familyName = params.get(2);
        String group = params.get(3);

        Student student = new Student(firstName, surName, familyName, group);

        return student;
    }
}
