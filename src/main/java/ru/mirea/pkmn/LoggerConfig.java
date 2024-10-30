package ru.mirea.pkmn;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.logging.Logger;

@Configuration
public class LoggerConfig {

    @Bean
    public Logger logger() {

        return Logger.getLogger(PkmnApplication.class.getName());
    }
}
