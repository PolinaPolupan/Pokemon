package ru.mirea.pkmn.polupanpolina.web.jdbc;

import com.google.common.io.Resources;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.mirea.pkmn.polupanpolina.CardImport;

import java.io.FileInputStream;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
public class DatabaseConfig {

    @Bean
    public Properties databaseProperties() {

        Properties databaseProperties = new Properties();
        databaseProperties.setProperty("stringtype", "unspecified");

        try {
            URL resource =  Resources.getResource("database.properties");

            Path path = Paths.get(resource.toURI());

            CardImport.deserializeCard(path.toString());

            databaseProperties.load(new FileInputStream(path.toString()));

        } catch (Exception ignored) {}


        return databaseProperties;
    }

    @Bean
    public Connection connection(Properties databaseProperties) {

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(
                    databaseProperties.getProperty("database.url"),
                    databaseProperties.getProperty("database.user"),
                    databaseProperties.getProperty("database.password")
            );
        } catch (SQLException ignored) {

        }

        return connection;
    }

    @Bean
    public DatabaseService databaseService(Connection connection) {
        return new DatabaseServiceImpl(connection);
    }
}
