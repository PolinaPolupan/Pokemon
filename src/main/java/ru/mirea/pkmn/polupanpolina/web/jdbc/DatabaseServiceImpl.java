package ru.mirea.pkmn.polupanpolina.web.jdbc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.postgresql.util.PGobject;
import org.springframework.beans.factory.annotation.Autowired;
import ru.mirea.pkmn.*;
import java.io.IOException;
import java.sql.*;
import java.util.List;
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

        try {

            PreparedStatement ps = connection.prepareStatement("SELECT * FROM card WHERE card.\"name\" = ?");

            ps.setString(1, cardName);
            ResultSet res = ps.executeQuery();
            res.next();
            Card card = extractCardFromResultSet(res);

            logger.log(Level.INFO, "Select card success: " + card);

            return card;

        } catch (Exception e) {

            logger.log(Level.SEVERE, "Select card failed: " + e.getMessage());
        }

        return null;
    }

    @Override
    public Card getCardFromDatabase(UUID uuid) {

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM card WHERE card.\"id\" = ?");

            ps.setObject(1, uuid);
            ResultSet res = ps.executeQuery();
            res.next();
            Card card = extractCardFromResultSet(res);

            logger.log(Level.INFO, "Select card success: " + card);

            return card;

        } catch (Exception e) {

            logger.log(Level.SEVERE, "Select card failed: " + e.getMessage());
        }

        return null;
    }

    @Override
    public Student getStudentFromDatabase(String studentFullName) {
        String[] nameParts = studentFullName.split(" ");
        if (nameParts.length != 3) {
            logger.log(Level.WARNING, "Invalid student full name: " + studentFullName);
            return null;
        }

        String query = "SELECT * FROM student WHERE \"firstName\" = ? AND \"familyName\" = ? AND \"patronicName\" = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, nameParts[0]);
            ps.setString(2, nameParts[1]);
            ps.setString(3, nameParts[2]);

            try (ResultSet res = ps.executeQuery()) {
                if (res.next()) {
                    Student student = extractStudentFromResultSet(res);
                    logger.log(Level.INFO, "Select student success: " + student);
                    return student;
                } else {
                    logger.log(Level.WARNING, "No student found for: " + studentFullName);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Select student failed: " + e.getMessage(), e);
        }

        return null;
    }

    @Override
    public Student getStudentFromDatabase(UUID uuid) {
        String query = "SELECT * FROM student WHERE \"id\" = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setObject(1, uuid);

            try (ResultSet res = ps.executeQuery()) {
                if (res.next()) {
                    Student student = extractStudentFromResultSet(res);
                    logger.log(Level.INFO, "Select student success: " + student);
                    return student;
                } else {
                    logger.log(Level.WARNING, "No student found with UUID: " + uuid);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Select student failed: " + e.getMessage(), e);
        }

        return null;
    }

    @Override
    public void saveCardToDatabase(Card card) {

        try {
            if (card.getEvolvesFrom() != null) createPokemonOwner(card.getPokemonOwner());
            if (card.getEvolvesFrom() != null) saveCardToDatabase(card.getEvolvesFrom());

            // Generate the UUID for the card
            UUID cardUuid = UUID.nameUUIDFromBytes(card.toString().getBytes());
            logger.log(Level.INFO, "Card: " + card.getName() + " set uid to: " + cardUuid);

            // Check if the card already exists in the database
            PreparedStatement checkPs = connection.prepareStatement("SELECT COUNT(*) FROM card WHERE id = ?");
            checkPs.setObject(1, cardUuid);
            ResultSet rs = checkPs.executeQuery();
            rs.next();

            if (rs.getInt(1) > 0) {
                logger.log(Level.WARNING, "Card with UUID " + cardUuid + " already exists in the database.");
                return; // Exit without inserting the card
            }

            // Proceed with the insertion if the card does not exist
            PreparedStatement ps = connection.prepareStatement("INSERT INTO card VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            UUID evolvesUuid = null;
            if (card.getEvolvesFrom() != null) {
                evolvesUuid = UUID.nameUUIDFromBytes(card.getEvolvesFrom().toString().getBytes());
                logger.log(Level.INFO, "Card evolves: " + card.getEvolvesFrom().getName() + " set uid to: " + evolvesUuid);
            }

            ps.setObject(1, cardUuid);
            ps.setString(2, card.getName());
            ps.setInt(3, card.getHp());
            ps.setObject(4, evolvesUuid);
            ps.setString(5, card.getGameSet());

            UUID ownerUuid = null;
            if (card.getEvolvesFrom() != null) {
                ownerUuid = UUID.nameUUIDFromBytes(card.getPokemonOwner().toString().getBytes());
            }

            ps.setObject(6, ownerUuid);
            ps.setString(7, card.getPokemonStage().toString());
            ps.setString(8, card.getRetreatCost());
            ps.setString(9, card.getWeaknessType().toString());
            ps.setString(10, card.getResistanceType().toString());

            // Convert card skills to JSON and store it as a PGObject
            var mapper = new ObjectMapper();
            var json = mapper.writeValueAsString(card.getSkills());

            PGobject jsonObject = new PGobject();
            jsonObject.setType("json");
            jsonObject.setValue(json);

            ps.setObject(11, jsonObject);
            ps.setString(12, card.getPokemonType().toString());
            ps.setObject(13, card.getRegulationMark());
            ps.setString(14, card.getNumber());

            ps.executeUpdate();
            logger.log(Level.INFO, "Insert card request is successful");

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Insert card request failed: " + e.getMessage());
        }

    }

    @Override
    public void createPokemonOwner(Student owner) {
        try {
            UUID ownerUuid = UUID.nameUUIDFromBytes(owner.toString().getBytes());

            // Check if the student already exists
            PreparedStatement checkPs = connection.prepareStatement("SELECT COUNT(*) FROM student WHERE id = ?");
            checkPs.setObject(1, ownerUuid);
            ResultSet rs = checkPs.executeQuery();
            rs.next();

            if (rs.getInt(1) > 0) {
                logger.log(Level.WARNING, "Student with UUID " + ownerUuid + " already exists in the database.");
                return; // Exit without inserting
            }

            // If not exists, proceed with the insertion
            PreparedStatement ps = connection.prepareStatement("INSERT INTO student VALUES(?, ?, ?, ?, ?)");
            logger.log(Level.INFO, "Owner: " + owner.getFirstName() + " set uid to: " + ownerUuid);

            ps.setObject(1, ownerUuid);
            ps.setString(2, owner.getFamilyName());
            ps.setString(3, owner.getFirstName());
            ps.setString(4, owner.getSurName());
            ps.setString(5, owner.getGroup());

            ps.executeUpdate();

            logger.log(Level.INFO, "Student inserted successfully.");

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to insert student: " + e.getMessage());
        }
    }

    private Student extractStudentFromResultSet(ResultSet res) throws SQLException {
        String firstName = res.getString("firstName");
        String surname = res.getString("patronicName");
        String familyName = res.getString("familyName");
        String group = res.getString("group");

        return new Student(firstName, surname, familyName, group);
    }

    private Card extractCardFromResultSet(ResultSet res) throws SQLException, IOException {

        String name = res.getString("name");
        int hp = res.getInt("hp");
        String gameSet = res.getString("game_set");
        String strOwnerUuid = res.getString("pokemon_owner");

        Student owner = null;

        if (strOwnerUuid != null) {
            UUID ownerId = UUID.fromString(strOwnerUuid);
            owner = getStudentFromDatabase(ownerId);
        }

        String stage = res.getString("stage");
        String retreatCost = res.getString("retreat_cost");
        String weaknessType = res.getString("weakness_type");
        String resistanceType = res.getString("resistance_type");
        String strUuid = res.getString("evolves_from");

        Card evolvesFrom = null;

        if (strUuid !=  null) {
            UUID evolvesFromId = UUID.fromString(strUuid);
            evolvesFrom = getCardFromDatabase(evolvesFromId);
        }

        String json = res.getString("attack_skills");

        ObjectMapper mapper = new ObjectMapper();
        List<AttackSkill> attackSkills = mapper.readValue(json, new TypeReference<>() {});

        String pokemonType = res.getString("pokemon_type");
        String regulationMark = res.getString("regulation_mark");
        String cardNumber = res.getString("card_number");

        return new Card(
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
    }
}