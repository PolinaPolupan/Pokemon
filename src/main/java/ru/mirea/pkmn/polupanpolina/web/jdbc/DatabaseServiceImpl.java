package ru.mirea.pkmn.polupanpolina.web.jdbc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.postgresql.util.PGobject;
import ru.mirea.pkmn.Card;
import ru.mirea.pkmn.Student;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.UUID;

public class DatabaseServiceImpl implements DatabaseService {

    private final Connection connection;

    private final Properties databaseProperties;

    public DatabaseServiceImpl() throws SQLException, IOException {

        // Загружаем файл database.properties

        databaseProperties = new Properties();
        databaseProperties.load(new FileInputStream("C:\\Users\\student\\IdeaProjects\\Pkmn\\src\\main\\resources\\database.properties"));
        databaseProperties.setProperty("stringtype", "unspecified");
        // Подключаемся к базе данных

        connection = DriverManager.getConnection(
                databaseProperties.getProperty("database.url"),
                databaseProperties.getProperty("database.user"),
                databaseProperties.getProperty("database.password")
        );
        System.out.println("Connection is "+(connection.isValid(0) ? "up" : "down"));

        //createCardTable();
    }

    @Override
    public Card getCardFromDatabase(String cardName) {
        // Реализовать получение данных о карте из БД



        return null;
    }

    @Override
    public Student getStudentFromDatabase(String studentFullName) {
        // Реализовать получение данных о студенте из БД
        return null;
    }

    @Override
    public void saveCardToDatabase(Card card) throws SQLException, JsonProcessingException {

        PreparedStatement ps = connection.prepareStatement("INSERT INTO student VALUES(?, ?, ?, ?, ?)");

        if (card.getEvolvesFrom() != null) saveCardToDatabase(card.getEvolvesFrom());

        UUID uuid = UUID.randomUUID();

        ps.setObject(1, uuid);

        ps.setString(2, card.getPokemonOwner().getFamilyName());

        ps.setString(3, card.getPokemonOwner().getFirstName());

        ps.setString(4,  card.getPokemonOwner().getSurName());

        ps.setString(5, card.getPokemonOwner().getGroup());

        ps.executeUpdate();

        PreparedStatement ps1 = connection.prepareStatement("INSERT INTO card VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

        UUID uuid1 = UUID.randomUUID();

        UUID uuidOwner = UUID.randomUUID();

        UUID uuidEvolves = UUID.randomUUID();

        ps1.setObject(1, uuid1);

        ps1.setString(2, card.getName());

        ps1.setInt(3, card.getHp());

        ps1.setObject(4, uuid1);

        ps1.setString(5, card.getGameSet());

        PGobject toInsertUUID = new PGobject();
        toInsertUUID.setType("uuid");
        toInsertUUID.setValue(uuidOwner.toString());

        ps1.setObject(6, uuid);

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
    }

    @Override
    public void createPokemonOwner(Student owner) {
        // Реализовать добавление студента - владельца карты в БД
    }

    public int executeUpdate(String query) throws SQLException {
        Statement statement = connection.createStatement();
        // Для Insert, Update, Delete
        int result = statement.executeUpdate(query);
        return result;
    }
}