package ru.mirea.polupanpolina;

import ru.mirea.polupanpolina.pkmn.Card;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PkmnApplication {
    public static void main(String[] args) throws URISyntaxException {

        URL resource = PkmnApplication.class.getClassLoader().getResource("my_card.txt");

        Path path = Paths.get(resource.toURI());

        Logger logger = Logger.getLogger(PkmnApplication.class.getName());

        logger.setLevel(Level.FINE);

        Card card = CardImport.parseCard(path.toString());
    }
}