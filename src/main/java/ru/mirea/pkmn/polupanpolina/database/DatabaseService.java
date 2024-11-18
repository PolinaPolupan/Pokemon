package ru.mirea.pkmn.polupanpolina.database;

import ru.mirea.pkmn.polupanpolina.entity.Card;
import ru.mirea.pkmn.polupanpolina.entity.Student;

import java.util.UUID;

public interface DatabaseService {

    Card getCard(String name);

    Card getCard(UUID uuid);

    Student getStudent(String fullName);

    Student getStudent(UUID uuid);

    void saveCard(Card card);

    void saveStudent(Student student);
}
