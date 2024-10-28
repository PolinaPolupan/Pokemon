package ru.mirea.pkmn.polupanpolina.web.jdbc;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.mirea.pkmn.Card;
import ru.mirea.pkmn.Student;

import java.sql.SQLException;

public interface DatabaseService {

    Card getCardFromDatabase(String cardName);

    Student getStudentFromDatabase(String studentFullName);

    void saveCardToDatabase(Card card) throws SQLException, JsonProcessingException;

    void createPokemonOwner(Student owner);
}
