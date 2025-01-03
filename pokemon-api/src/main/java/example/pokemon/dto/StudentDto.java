package example.pokemon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Stores the student's data.
 * Classwork 3 (UML diagrams).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {
    private String firstName;
    private String lastName;
    private String surName;
    private String group;
}