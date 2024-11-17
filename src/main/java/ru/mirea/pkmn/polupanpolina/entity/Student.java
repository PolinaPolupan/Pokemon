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

    @Column(name = "firstName")
    private String firstName;
    @Column(name = "patronicName")
    private String patronicName;
    @Column(name = "familyName")
    private String familyName;
    private String group;
}
