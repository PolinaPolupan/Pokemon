package ru.mirea.polupanpolina;

import ru.mirea.polupanpolina.pkmn.Card;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility helper class exports Card instances to .crd files
 * Classwork 3 (Task 1).
 */
public class CardExport {

    private static final Logger logger =  Logger.getLogger(PkmnApplication.class.getName());

    public static void serializeCard(Card card, String fileName) {
        try {
            File outputFile = new File("output/" + fileName);
            FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(card);
        } catch (Exception e) {
            logger.log(Level.SEVERE, String.format("%s: Card serializing failed", e.getMessage()));
            return;
        }
        logger.log(Level.INFO, String.format("Object is successfully serialized into file %s", "output/" + fileName));
    }
}
