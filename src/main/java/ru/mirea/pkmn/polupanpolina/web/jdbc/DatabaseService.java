package ru.mirea.pkmn.polupanpolina.web.jdbc;

import ru.mirea.pkmn.Card;
import ru.mirea.pkmn.Student;

import java.util.UUID;

public interface DatabaseService {

    Card getCardFromDatabase(String cardName);

    Card getCardFromDatabase(UUID uuid);

    Student getStudentFromDatabase(String studentFullName);

    Student getStudentFromDatabase(UUID uuid);

    void saveCardToDatabase(Card card);

    void createPokemonOwner(Student owner);
}
