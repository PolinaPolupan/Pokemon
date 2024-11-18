package ru.mirea.pkmn.polupanpolina.repository;

import ru.mirea.pkmn.polupanpolina.entity.CardEntity;
import ru.mirea.pkmn.polupanpolina.entity.StudentEntity;

import java.util.List;
import java.util.UUID;

public interface PkmnRepository {

    List<CardEntity> getCard(String name);

    CardEntity getCard(UUID uuid);

    StudentEntity getStudent(String fullName);

    StudentEntity getStudent(UUID uuid);

    void saveCard(CardEntity card);

    void saveStudent(StudentEntity student);
}
