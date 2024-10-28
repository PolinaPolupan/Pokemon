package ru.mirea.pkmn.polupanpolina.web.jdbc;

import ru.mirea.pkmn.Card;
import ru.mirea.pkmn.Student;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DatabaseServiceImpl implements DatabaseService {

    private final Connection connection;

    private final Properties databaseProperties;

    public DatabaseServiceImpl() throws SQLException, IOException {

        // Загружаем файл database.properties

        databaseProperties = new Properties();
        databaseProperties.load(new FileInputStream("C:\\Users\\student\\IdeaProjects\\Pkmn\\src\\main\\resources\\database.properties"));

        // Подключаемся к базе данных

        connection = DriverManager.getConnection(
                databaseProperties.getProperty("database.url"),
                databaseProperties.getProperty("database.user"),
                databaseProperties.getProperty("database.password")
        );
        System.out.println("Connection is "+(connection.isValid(0) ? "up" : "down"));
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
    public void saveCardToDatabase(Card card) throws SQLException {

    }

    @Override
    public void createPokemonOwner(Student owner) {
        // Реализовать добавление студента - владельца карты в БД
    }

    private void createCardTable() throws SQLException {
        String customerTableQuery = "CREATE TABLE card " +
                "(" +
                "id uuid PRIMARY KEY, " +
                "name text, " +
                "hp int2, " +
                "evolves_from uuid, " +
                "game_set text, " +
                "card_number text, " +
                "pokemon_owner uuid, " +
                "stage text, " +
                "retreat_cost text, " +
                "weakness_type text, " +
                "resistance_type text, " +
                "attack_skills json, " +
                "pokemon_type text, " +
                "regulation_mark char(1)" +
                ")";
        executeUpdate(customerTableQuery);
    }

    private int executeUpdate(String query) throws SQLException {
        Statement statement = connection.createStatement();
        // Для Insert, Update, Delete
        int result = statement.executeUpdate(query);
        return result;
    }
}