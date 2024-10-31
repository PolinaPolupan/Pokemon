package ru.mirea.pkmn.polupanpolina.web.jdbc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.mirea.pkmn.utils.ResourceFileLoader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

@Configuration
public class DatabaseConfig {

    @Bean
    public Properties databaseProperties(Logger logger, ResourceFileLoader loader) {

        Properties databaseProperties = new Properties();
        databaseProperties.setProperty("stringtype", "unspecified");

        try {
            databaseProperties.load(loader.getResourceFile("database.properties"));

            logger.log(Level.INFO, "Database properties are set");

        } catch (Exception e) {

            logger.log(Level.SEVERE, "Database properties error: " + e.getMessage());
        }

        return databaseProperties;
    }

    @Bean
    public Connection connection(Properties databaseProperties, Logger logger) {

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(
                    databaseProperties.getProperty("database.url"),
                    databaseProperties.getProperty("database.user"),
                    databaseProperties.getProperty("database.password")
            );

            logger.log(Level.INFO, "Database connection is set");
        } catch (SQLException e) {

            logger.log(Level.SEVERE, "Database connection error: " + e.getMessage());
        }

        return connection;
    }

    @Bean
    public DatabaseServiceImpl databaseService(Connection connection, Logger logger) {
        return new DatabaseServiceImpl(connection, logger);
    }
}
