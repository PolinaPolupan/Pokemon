package ru.mirea.pkmn.polupanpolina.database;

import ru.mirea.pkmn.polupanpolina.entity.CardEntity;
import ru.mirea.pkmn.polupanpolina.entity.StudentEntity;

import java.util.UUID;

public interface DatabaseService {

    CardEntity getCard(String name);

    CardEntity getCard(UUID uuid);

    StudentEntity getStudent(String fullName);

    StudentEntity getStudent(UUID uuid);

    void saveCard(CardEntity card);

    void saveStudent(StudentEntity student);
}
