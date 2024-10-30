package ru.mirea.pkmn.polupanpolina.web.jdbc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.postgresql.util.PGobject;
import org.springframework.beans.factory.annotation.Autowired;
import ru.mirea.pkmn.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseServiceImpl implements DatabaseService {

    private final Connection connection;

    private final Logger logger;

    @Autowired
    DatabaseServiceImpl(Connection connection, Logger logger) {

        this.connection = connection;
        this.logger = logger;
    }

    @Override
    public Card getCardFromDatabase(String cardName) {
        // Реализовать получение данных о карте из БД

        Card card = null;

        try {

            PreparedStatement ps = connection.prepareStatement("SELECT * FROM card WHERE card.\"name\" = ?");

            ps.setString(1, cardName);

            ResultSet res = ps.executeQuery();

            res.next();
            String name = res.getString("name");
            int hp = res.getInt("hp");
            UUID evolvesFromId = UUID.fromString(res.getString("evolves_from"));
            String gameSet = res.getString("game_set");
            UUID ownerId = UUID.fromString(res.getString("pokemon_owner"));
            Card evolvesFrom = getCardFromDatabase(evolvesFromId);
            Student owner = getStudentFromDatabase(ownerId);
            String stage = res.getString("stage");
            String retreatCost = res.getString("retreat_cost");
            String weaknessType = res.getString("weakness_type");
            String resistanceType = res.getString("resistance_type");

            String json = res.getString("attack_skills");

            ObjectMapper mapper = new ObjectMapper();
            List<AttackSkill> attackSkills = mapper.readValue(json, new TypeReference<List<AttackSkill>>() {});

            for (AttackSkill attackSkill : attackSkills) {
                System.out.println(attackSkill);
            }

            String pokemonType = res.getString("pokemon_type");
            String regulationMark = res.getString("regulation_mark");
            String cardNumber = res.getString("card_number");

            card = new Card(
                   PokemonStage.valueOf(stage),
                    name,
                    cardNumber,
                    hp,
                    EnergyType.valueOf(pokemonType),
                    evolvesFrom,
                    attackSkills,
                    EnergyType.valueOf(weaknessType),
                    EnergyType.valueOf(resistanceType),
                    retreatCost,
                    gameSet,
                    regulationMark.charAt(0),
                    owner
            );

            logger.log(Level.INFO, "Select card success: " + card);
        } catch (Exception e) {

            logger.log(Level.SEVERE, "Select card failed: " + e.getMessage());
        }

        return card;
    }

    @Override
    public Card getCardFromDatabase(UUID uuid) {
        Card card = null;

        try {

            PreparedStatement ps = connection.prepareStatement("SELECT * FROM card WHERE card.\"id\" = ?");

            ps.setObject(1, uuid);

            ResultSet res = ps.executeQuery();

            res.next();
            String name = res.getString("name");
            int hp = res.getInt("hp");
            String gameSet = res.getString("game_set");
            UUID ownerId = UUID.fromString(res.getString("pokemon_owner"));
            Student owner = getStudentFromDatabase(ownerId);
            String stage = res.getString("stage");
            String retreatCost = res.getString("retreat_cost");
            String weaknessType = res.getString("weakness_type");
            String resistanceType = res.getString("resistance_type");

            String json = res.getString("attack_skills");

            ObjectMapper mapper = new ObjectMapper();
            List<AttackSkill> attackSkills = mapper.readValue(json, new TypeReference<List<AttackSkill>>() {});

            for (AttackSkill attackSkill : attackSkills) {
                System.out.println(attackSkill);
            }

            String pokemonType = res.getString("pokemon_type");
            String regulationMark = res.getString("regulation_mark");
            String cardNumber = res.getString("card_number");

            card = new Card(
                    PokemonStage.valueOf(stage),
                    name,
                    cardNumber,
                    hp,
                    EnergyType.valueOf(pokemonType),
                    null,
                    attackSkills,
                    EnergyType.valueOf(weaknessType),
                    EnergyType.valueOf(resistanceType),
                    retreatCost,
                    gameSet,
                    regulationMark.charAt(0),
                    owner
            );

            logger.log(Level.INFO, "Select card success: " + card);
        } catch (Exception e) {

            logger.log(Level.SEVERE, "Select card failed: " + e.getMessage());
        }

        return card;
    }

    @Override
    public Student getStudentFromDatabase(String studentFullName) {
        // Реализовать получение данных о студенте из БД
        
        Student student = null;

        String firstMame = studentFullName.split(" ")[0];
        String familyName = studentFullName.split(" ")[1];
        String patronicName = studentFullName.split(" ")[2];

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM student " +
                    "WHERE student.\"firstName\" = ? " +
                    "AND student.\"familyName\" = ? " +
                    "AND student.\"patronicName\" = ?");

            ps.setString(1, firstMame);
            ps.setString(2, familyName);
            ps.setString(3, patronicName);

            ResultSet res = ps.executeQuery();

            res.next();
            String firstNameDerived = res.getString(3);
            String familyNameDerived = res.getString(2);
            String surnameDerived = res.getString(4);
            String group = res.getString(5);

            student = new Student(
                    firstNameDerived,
                    surnameDerived,
                    familyNameDerived,
                    group
            );

            logger.log(Level.INFO, "Select student success: " + student);

        } catch (Exception e) {

            logger.log(Level.SEVERE, "Select student failed: " + e.getMessage());
        }

        return student;
    }

    @Override
    public Student getStudentFromDatabase(UUID uuid) {
        Student student = null;

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM student " +
                    "WHERE student.\"id\" = ? ");

            ps.setObject(1, uuid);

            ResultSet res = ps.executeQuery();

            res.next();
            String firstNameDerived = res.getString(3);
            String familyNameDerived = res.getString(2);
            String surnameDerived = res.getString(4);
            String group = res.getString(5);

            student = new Student(
                    firstNameDerived,
                    surnameDerived,
                    familyNameDerived,
                    group
            );

            logger.log(Level.INFO, "Select student success: " + student);

        } catch (Exception e) {

            logger.log(Level.SEVERE, "Select student failed: " + e.getMessage());
        }

        return student;
    }

    @Override
    public void saveCardToDatabase(Card card) {

        try {

            if (card.getEvolvesFrom() != null) createPokemonOwner(card.getPokemonOwner());

            if (card.getEvolvesFrom() != null) saveCardToDatabase(card.getEvolvesFrom());

            PreparedStatement ps1 = connection.prepareStatement("INSERT INTO card VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            UUID cardUuid = UUID.nameUUIDFromBytes(card.toString().getBytes());

            logger.log(Level.INFO, "Card: " + card.getName() + " set uid to: " + cardUuid);

            UUID evolvesUuid = null;
            if (card.getEvolvesFrom() != null) {

                evolvesUuid = UUID.nameUUIDFromBytes(card.getEvolvesFrom().toString().getBytes());
                logger.log(Level.INFO, "Card evolves: " + card.getEvolvesFrom().getName() + " set uid to: " + evolvesUuid);
            }

            ps1.setObject(1, cardUuid);

            ps1.setString(2, card.getName());

            ps1.setInt(3, card.getHp());

            ps1.setObject(4, evolvesUuid);

            ps1.setString(5, card.getGameSet());

            UUID ownerUuid = null;
            if (card.getEvolvesFrom() != null) ownerUuid = UUID.nameUUIDFromBytes(card.getPokemonOwner().toString().getBytes());

            ps1.setObject(6, ownerUuid);

            ps1.setString(7, card.getPokemonStage().toString());

            ps1.setString(8, card.getRetreatCost());

            ps1.setString(9, card.getWeaknessType().toString());

            ps1.setString(10, card.getResistanceType().toString());

            var mapper = new ObjectMapper();
            var json = mapper.writeValueAsString(card.getSkills());

            PGobject jsonObject = new PGobject();
            jsonObject.setType("json");
            jsonObject.setValue(json);

            ps1.setObject(11, jsonObject);

            ps1.setString(12, card.getPokemonType().toString());

            ps1.setObject(13, card.getRegulationMark());

            ps1.setString(14, card.getNumber());

            ps1.executeUpdate();

            logger.log(Level.INFO, "Insert card request is successful");
        } catch (Exception e) {

            logger.log(Level.SEVERE, "Insert card request failed: " + e.getMessage());
        }
    }

    @Override
    public void createPokemonOwner(Student owner) {
        // Реализовать добавление студента - владельца карты в БД

        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO student VALUES(?, ?, ?, ?, ?)");

            UUID ownerUuid = UUID.nameUUIDFromBytes(owner.toString().getBytes());

            logger.log(Level.INFO, "Owner: " + owner.getFirstName() + " set uid to: " + ownerUuid);

            ps.setObject(1, ownerUuid);

            ps.setString(2, owner.getFamilyName());

            ps.setString(3, owner.getFirstName());

            ps.setString(4,  owner.getSurName());

            ps.setString(5, owner.getGroup());

            ps.executeUpdate();

        } catch (Exception e) {

            logger.log(Level.SEVERE, "Failed to insert student: " + e.getMessage());
        }
    }
}