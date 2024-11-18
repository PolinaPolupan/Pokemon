package ru.mirea.pkmn.polupanpolina.database;

import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.logging.Logger;

@Configuration
public class DatabaseConfig {

    @Bean
    public DatabaseService databaseService(EntityManager em, Logger logger) {
        return new DatabaseServiceImpl(em, logger);
    }
}
