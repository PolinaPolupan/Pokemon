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

    @Column(name = "firstName", nullable = false)
    private String firstName;

    @Column(name = "patronicName", nullable = false)
    private String surName;

    @Column(name = "familyName", nullable = false)
    private String familyName;

    @Column(nullable = false)
    private String group;
}
