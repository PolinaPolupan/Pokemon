package ru.mirea.pkmn;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

/**
 * Stores the student's data.
 * Classwork 3 (UML diagrams).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student implements Serializable {

    private String firstName;

    private String surName;

    private String familyName;

    private String group;

    public ru.mirea.pkmn.polupanpolina.entity.Student toEntity() {

        // Map fields to the entity
        return new ru.mirea.pkmn.polupanpolina.entity.Student(
                null,    // id
                this.firstName, // firstName
                this.surName,   // patronicName (mapped from surName)
                this.familyName,// familyName
                this.group      // group
        );
    }
}
