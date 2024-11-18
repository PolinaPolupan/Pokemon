package ru.mirea.pkmn.polupanpolina.database;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import ru.mirea.pkmn.polupanpolina.entity.*;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseServiceImpl implements DatabaseService {

    private final Logger logger;

    private final EntityManager em;

    @Autowired
    DatabaseServiceImpl(EntityManager em, Logger logger) {

        this.logger = logger;
        this.em = em;
    }

    @Override
    public Card getCardFromDatabase(String cardName) {
        try {
            return em.createQuery("SELECT c FROM Card c WHERE c.name = :name", Card.class)
                    .setParameter("name", cardName)
                    .getSingleResult();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to find card by name: " + cardName, e);
        }
        return null;
    }

    @Override
    public Card getCardFromDatabase(UUID uuid) {
        return em.find(Card.class, uuid);
    }

    @Override
    public Student getStudentFromDatabase(String studentFullName) {
        String[] parts = studentFullName.split(" ");
        if (parts.length != 3) {
            logger.log(Level.WARNING, "Invalid student name format: " + studentFullName);
            return null;
        }
        try {
            return em.createQuery(
                            "SELECT s FROM Student s WHERE s.firstName = :firstName AND s.familyName = :familyName AND s.surName = :surName",
                            Student.class)
                    .setParameter("firstName", parts[0])
                    .setParameter("familyName", parts[1])
                    .setParameter("surName", parts[2])
                    .getSingleResult();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to find student: " + studentFullName, e);
        }
        return null;
    }

    @Override
    public Student getStudentFromDatabase(UUID uuid) {
        return em.find(Student.class, uuid);
    }

    @Override
    public void saveCardToDatabase(Card card) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(card);
            tx.commit();
            logger.log(Level.INFO, "Card saved: " + card.getName());
        } catch (Exception e) {
            tx.rollback();
            logger.log(Level.SEVERE, "Failed to save card: " + e.getMessage(), e);
        }
    }

    @Override
    public void createPokemonOwner(Student owner) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(owner);
            tx.commit();
            logger.log(Level.INFO, "Student saved: " + owner.getFirstName());
        } catch (Exception e) {
            tx.rollback();
            logger.log(Level.SEVERE, "Failed to save student: " + e.getMessage(), e);
        }
    }
}