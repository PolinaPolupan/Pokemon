package ru.mirea.pkmn;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Resources;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.mirea.pkmn.polupanpolina.CardExport;
import ru.mirea.pkmn.polupanpolina.CardImport;
import ru.mirea.pkmn.polupanpolina.web.http.PkmnHttpClient;
import ru.mirea.pkmn.polupanpolina.web.jdbc.DatabaseService;
import ru.mirea.pkmn.polupanpolina.web.jdbc.DatabaseServiceImpl;


@SpringBootApplication
public class PkmnApplication {

    public static void main(String[] args) throws URISyntaxException {

        ApplicationContext context = SpringApplication.run(PkmnApplication.class, args);

        // Access the PkmnHttpClient bean
        PkmnHttpClient pkmnHttpClient = context.getBean(PkmnHttpClient.class);

        testNetwork(pkmnHttpClient);
    }

    public static void testNetwork(PkmnHttpClient client) throws URISyntaxException {

        Logger logger = Logger.getLogger(PkmnApplication.class.getName()); // Create logger

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
}