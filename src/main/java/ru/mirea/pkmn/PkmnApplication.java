package ru.mirea.pkmn;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.io.Resources;
import lombok.val;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.mirea.pkmn.polupanpolina.CardExport;
import ru.mirea.pkmn.polupanpolina.CardImport;
import ru.mirea.pkmn.polupanpolina.web.http.PkmnHttpClient;
import ru.mirea.pkmn.polupanpolina.web.jdbc.DatabaseService;
import ru.mirea.pkmn.polupanpolina.web.jdbc.DatabaseServiceImpl;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


@SpringBootApplication
public class PkmnApplication {

    static ApplicationContext context;

    public static void main(String[] args) throws URISyntaxException, SQLException, JsonProcessingException {

        context = SpringApplication.run(PkmnApplication.class, args);

        // Access the PkmnHttpClient bean
        PkmnHttpClient pkmnHttpClient = context.getBean(PkmnHttpClient.class);

        DatabaseServiceImpl dbService = context.getBean(DatabaseServiceImpl.class);

        testNetwork(pkmnHttpClient);

        testDatabase(dbService);
    }

    public static void testNetwork(PkmnHttpClient client) throws URISyntaxException {

        Logger logger = context.getBean(Logger.class); // Create logger

        URL resource =  Resources.getResource("my_card.txt"); // Get test resource

        Path path = Paths.get(resource.toURI());

        logger.setLevel(Level.FINE);

        Card cardFile = CardImport.parseCard(path.toString());

        val callback = new PkmnHttpClient.PokemonCardCallback() {
            @Override
            public void onSuccess(JsonNode cardData) {

                logger.log(Level.INFO,"Card data: " + cardData); // Log data

                val attacks = cardData.findValues("attacks"); // Find attacks section

                int ind = 0; // Index is needed to iterate over attack skills

                for (final JsonNode objNode : attacks) {
                    JsonNode text = objNode.findValue("text");

                    logger.log(Level.INFO, "Attack description: " + text.toString().replace('"', ' ').strip() );

                    cardFile.setSkillDescription(ind, text.toString());
                    ind++;
                }

                String EXPORT_PATH = "export.crd";

                CardExport.serializeCard(cardFile, EXPORT_PATH);

                try {
                    URL resource =  Resources.getResource(EXPORT_PATH);

                    Path path = Paths.get(resource.toURI());

                    CardImport.deserializeCard(path.toString());

                } catch (URISyntaxException ignored) {}
            }

            @Override
            public void onError(Throwable error) {
                logger.log(Level.SEVERE,"Failed to fetch card: " + error.getMessage());
            }
        };

        client.getPokemonCard("azumarill", "h4", callback);
    }

    public static void testDatabase(DatabaseService service) throws URISyntaxException, SQLException, JsonProcessingException {

        URL resource =  Resources.getResource("my_card.txt"); // Get test resource

        Path path = Paths.get(resource.toURI());

        Card card = CardImport.parseCard(path.toString());

        service.saveCardToDatabase(card);
        service.getStudentFromDatabase("Polina Polupan Mikhailovna");
        service.getCardFromDatabase("Azumarill");
    }
}