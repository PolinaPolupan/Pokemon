package ru.mirea.pkmn;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Resources;
import ru.mirea.pkmn.polupanpolina.CardExport;
import ru.mirea.pkmn.polupanpolina.CardImport;
import ru.mirea.pkmn.polupanpolina.web.http.PkmnHttpClient;
import ru.mirea.pkmn.polupanpolina.web.jdbc.DatabaseService;
import ru.mirea.pkmn.polupanpolina.web.jdbc.DatabaseServiceImpl;

public class PkmnApplication {

    public static void main(String[] args) throws IOException, URISyntaxException, SQLException {

        testMethods();

        Logger logger = Logger.getLogger(PkmnApplication.class.getName());

        URL resource =  Resources.getResource("my_card.txt");

        Path path = Paths.get(resource.toURI());

        logger.setLevel(Level.FINE);

        Card cardFile = CardImport.parseCard(path.toString());


        PkmnHttpClient pkmnHttpClient = new PkmnHttpClient();

        JsonNode card = pkmnHttpClient.getPokemonCard("azumarill", "h4");
        System.out.println(card.toPrettyString());

        List<JsonNode> attacks = StreamSupport
                .stream(card.findValues("attacks").spliterator(), false)
                .collect(Collectors.toList());


        int ind = 0;
        for (final JsonNode objNode : attacks) {
            JsonNode text = objNode.findValue("text");
            System.out.println(text.toString());

            cardFile.setSkillDescription(ind, text.toString());
            ind++;
        }

        System.out.println(cardFile.toString());
        CardExport.serializeCard(cardFile, "export.crd");

        URL resource1 =  Resources.getResource("export.crd");

        Path path1 = Paths.get(resource1.toURI());

        CardImport.deserializeCard(path1.toString());

        DatabaseService db = new DatabaseServiceImpl();
    }

    public static void testMethods() {


    }
}