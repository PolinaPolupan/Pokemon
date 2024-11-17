package ru.mirea.pkmn.polupanpolina.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "student")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    private UUID id;

    private String firstName;
    private String surName;
    private String familyName;
    private String group;
}
