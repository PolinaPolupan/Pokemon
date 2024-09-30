package ru.mirea.polupanpolina;

import ru.mirea.polupanpolina.pkmn.Card;
import ru.mirea.polupanpolina.pkmn.EnergyType;
import ru.mirea.polupanpolina.pkmn.PokemonStage;
import ru.mirea.polupanpolina.pkmn.Student;

import java.io.IOException;
import java.util.List;

public class PkmnApplication {
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        Student student = new Student("fist", "sur", "pol", "8");
        Card card = new Card(
                PokemonStage.STAGE1,
                "name",
                90,
                EnergyType.COLORLESS,
                null,
                List.of(),
                EnergyType.COLORLESS,
                EnergyType.DRAGON,
                "W",
                "set",
                'e',
                student
        );

        CardImport cardImport = new CardImport("C:\\Users\\student\\IdeaProjects\\Pkmn\\src\\main\\resources\\my_card.txt");

       // cardImport.serializeCard(card);
        String path = "C:\\Users\\student\\IdeaProjects\\Pkmn\\src\\main\\resources\\my_card.txt";

        Card card1 = cardImport.deserializeCard(path);

        System.out.println(card1.toString());
    }
}